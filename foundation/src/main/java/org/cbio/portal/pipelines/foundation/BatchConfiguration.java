/*
 * Copyright (c) 2016-17 Memorial Sloan-Kettering Cancer Center.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY OR FITNESS
 * FOR A PARTICULAR PURPOSE. The software and documentation provided hereunder
 * is on an "as is" basis, and Memorial Sloan-Kettering Cancer Center has no
 * obligations to provide maintenance, support, updates, enhancements or
 * modifications. In no event shall Memorial Sloan-Kettering Cancer Center be
 * liable to any party for direct, indirect, special, incidental or
 * consequential damages, including lost profits, arising out of the use of this
 * software and its documentation, even if Memorial Sloan-Kettering Cancer
 * Center has been advised of the possibility of such damage.
 */

/*
 * This file is part of cBioPortal.
 *
 * cBioPortal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.CaseType;

import java.util.*;

import org.springframework.batch.core.*;
import org.springframework.batch.item.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    public static final String FOUNDATION_JOB = "foundationJob";
    public static final String FOUNDATION_XML_DOCUMENT_JOB = "foundationXmlDocumentJob";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("${chunk.interval}")
    private int chunkInterval;

    /**
     * Foundation Job.
     */
    @Bean
    public Job foundationJob() {
        return jobBuilderFactory.get(FOUNDATION_JOB)
            .start(extractFileDataStep())
                .next(cnaDataStep())
                .next(generalDataStep())
                .next(metaDataStep())
            .build();
    }

    /**
     * Foundation XML document generator job
     */
    @Bean
    public Job foundationXmlDocumentJob() {
        return jobBuilderFactory.get(FOUNDATION_XML_DOCUMENT_JOB)
                .start(extractFileDataStep())
                .next(foundationXmlGeneratorStep())
                .build();
    }

    /**
     * File processing Step.
     */
    @Bean
    public Step extractFileDataStep() {
        return stepBuilderFactory.get("extractFileDataStep")
                .tasklet(foundationFileTasklet())
                .build();
    }

    /**
     * File processing Tasklet.
     * Reads files from the source directory and stores list
     * of foundation cases in the execution context to pass
     * on to future steps
     */
    @Bean
    @StepScope
    public Tasklet foundationFileTasklet() {
        return new FoundationFileTasklet();
    }

    /**
     * XML document generator step.
     */
    @Bean
    public Step foundationXmlGeneratorStep() {
        return stepBuilderFactory.get("foundationXmlGeneratorStep")
                .listener(foundationXmlGeneratorListener())
                .tasklet(foundationXmlGeneratorTasklet())
                .build();
    }

    /**
     * Foundation XML document generator tasklet.
     * Merges Foundation cases into single ClientCaseInfoType instance and
     * writes output as a formatted XML document.
     */
    @Bean
    @StepScope
    public Tasklet foundationXmlGeneratorTasklet() {
        return new FoundationXmlGeneratorTasklet();
    }

    @Bean
    public StepExecutionListener foundationXmlGeneratorListener() {
        return new FoundationXmlGeneratorListener();
    }

    /**
     * General Step for Clinical, Mutation, and Fusion data.
     */
    @Bean
    public Step generalDataStep() {
        return stepBuilderFactory.get("generalDataStep")
                .listener(foundationStepListener())
                .<CaseType, CompositeResultBean> chunk(chunkInterval)
                .reader(foundationReader())
                .processor(foundationCompositeProcessor())
                .writer(compositeWriter())
                .build();
    }

    /**
     * Listener for Clinical, Mutation, and Fusion data.
     * Step listener adds list of foundation cases to step execution context .
     */
    @Bean
    public StepExecutionListener foundationStepListener() {
        return new FoundationStepListener();
    }

    /**
     * Reader, Composite Processor, and Composite Writer for Clinical, Mutation, and Fusion data.
     */
    @Bean
    @StepScope
    public ItemStreamReader<CaseType> foundationReader() {
        return new FoundationReader();
    }

    @Bean
    @StepScope
    public FoundationCompositeProcessor foundationCompositeProcessor(){
        return new FoundationCompositeProcessor();
    }

    @Bean
    @StepScope
    public CompositeItemWriter<CompositeResultBean> compositeWriter() {
        CompositeItemWriter writer = new CompositeItemWriter();
        List delegates = new ArrayList();
        delegates.add(clinicalWriter());
        delegates.add(mutationWriter());
        delegates.add(fusionWriter());
        delegates.add(genePanelWriter());

        writer.setDelegates(delegates);
        return writer;
    }

    @Bean
    @StepScope
    public ItemStreamWriter<CompositeResultBean> clinicalWriter() {
        return new ClinicalDataWriter();
    }

    @Bean
    @StepScope
    public ItemStreamWriter<CompositeResultBean> fusionWriter() {
        return new FusionDataWriter();
    }

    @Bean
    @StepScope
    public ItemStreamWriter<CompositeResultBean> mutationWriter() {
        return new MutationDataWriter();
    }

    @Bean
    @StepScope
    public ItemStreamWriter<CompositeResultBean> genePanelWriter() {
        return new GenePanelDataWriter();
    }

    /**
     * Step for CNA data.
     */
    @Bean
    public Step cnaDataStep() {
        return stepBuilderFactory.get("cnaDataStep")
            .listener(cnaStepListener())
            .<String, String> chunk(chunkInterval)
            .reader(cnaDataReader())
            .writer(cnaDataWriter())
            .build();
    }

    /**
     * Reader, Writer, and Listener for CNA data.
     */
    @Bean
    @JobScope
    public ItemStreamReader<String> cnaDataReader() {
        return new CnaDataReader();
    }

    @Bean
    @StepScope
    public ItemStreamWriter<String> cnaDataWriter() {
        return new CnaDataWriter();
    }

    @Bean
    public StepExecutionListener cnaStepListener() {
        return new CnaStepListener();
    }

    /**
     * Step and Tasklet for writing meta data files.
     */
    @Bean
    public Step metaDataStep() {
        return stepBuilderFactory.get("metaDataStep")
                .tasklet(metaDataTasklet())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet metaDataTasklet() {
        return new MetaDataTasklet();
    }

}
