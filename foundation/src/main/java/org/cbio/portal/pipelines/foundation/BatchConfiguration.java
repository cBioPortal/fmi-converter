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
     * General Step for Clinical, Mutation, and Fusion data. 
     */
    @Bean
    public Step generalDataStep() {
        return stepBuilderFactory.get("generalDataStep")
                .<CaseType, CompositeResultBean> chunk(chunkInterval)
                .reader(foundationReader())
                .processor(foundationCompositeProcessor())
                .writer(compositeWriter())
                .listener(foundationStepListener())
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

    /**
     * Step for CNA data. 
     */            
    @Bean
    public Step cnaDataStep() {
        return stepBuilderFactory.get("cnaDataStep")
            .<String, String> chunk(chunkInterval)
            .reader(cnaDataReader())
            .writer(cnaDataWriter())
            .listener(cnaStepListener())
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
