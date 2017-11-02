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

import org.cbio.portal.pipelines.foundation.model.*;

import java.io.*;
import java.util.*;
import javax.xml.bind.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.apache.commons.logging.*;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.io.comparator.NameFileComparator;

/**
 *
 * @author ochoaa
 */
public class FoundationFileTasklet implements Tasklet {
    
    @Value("#{jobParameters[sourceDirectory]}")
    private String sourceDir;
        
    private static final Log LOG = LogFactory.getLog(FoundationFileTasklet.class);
    
    @Override
    public RepeatStatus execute(StepContribution stepContext, ChunkContext chunkContext) throws Exception {        
        Map<String, CaseType> fmiCaseTypeMap = new LinkedHashMap<>();
        Set<String> nonHumanContentColumns = new LinkedHashSet();
        
        // get list of xml files sorted by date and put data into map
        File[] sourceXmlFiles = getDateSortedFiles(new File(sourceDir));
        for (File xmlFile : sourceXmlFiles) {
            List<CaseType> newCases = new ArrayList();
            try {
                LOG.info("Extracting case data from: " + xmlFile.getName());                
                newCases = extractFileCaseData(xmlFile);
            } 
            catch (JAXBException | IOException ex){
                LOG.error("Error processing file: " + xmlFile.getName());
                ex.printStackTrace();
            }
            
            // since files are sorted by filename/date, cases in more than one file 
            // will automatically only have their most recent data converted
            for (CaseType ct : newCases) {
                if (fmiCaseTypeMap.containsKey(ct.getCase())) {
                    LOG.warn("Sample found in more than one file. Using current file to update data for sample: " + ct.getCase());
                }
                // if non-human content list is not empty then add to map of normalized
                // column names to external column name
                if (ct.getVariantReport().getNonHumanContent() != null && !ct.getVariantReport().getNonHumanContent().getNonHuman().isEmpty()) {
                    for (NonHumanType nht : ct.getVariantReport().getNonHumanContent().getNonHuman()) {
                        nonHumanContentColumns.add(nht.getOrganism());
                    }
                }                
                fmiCaseTypeMap.put(ct.getCase(), ct);
            }
        }        
        // add list of cases to the execution context
        LOG.info("Adding " + fmiCaseTypeMap.size() + " cases to execution context.");
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("fmiCaseTypeMap", fmiCaseTypeMap);
        
        // log if any non-human content data was found and add flag to execution context
        if (!nonHumanContentColumns.isEmpty()) {
            LOG.info("Non-Human Content data found in source XMLS: " + nonHumanContentColumns.size());            
        }
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("addNonHumanContentData", !nonHumanContentColumns.isEmpty());
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("nonHumanContentColumns", new ArrayList(nonHumanContentColumns));
        
        return RepeatStatus.FINISHED;
    }
    
    /**
     * Extract the case data from source xml file by root tag
     * @param xmlFile
     * @return
     * @throws JAXBException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException 
     */
    private List<CaseType> extractFileCaseData(File xmlFile) throws JAXBException, IOException, ParserConfigurationException, SAXException {
        List<CaseType> newCases = new ArrayList();

        // get the document root and determine how to unmarshal document  
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
        Element root = document.getDocumentElement();        
        if (root.getNodeName().equals("ClientCaseInfo")) {
            // unmarshal document with root tag = ClientCaseInfo
            JAXBContext context = JAXBContext.newInstance(ClientCaseInfoType.class);                
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
            ClientCaseInfoType cci = (ClientCaseInfoType) jaxbUnmarshaller.unmarshal(root);
            newCases.addAll(cci.getCases().getCase());
        }
        else {
            // unmarshal document with root tag = ResultsReport
            JAXBContext context = JAXBContext.newInstance(ResultsReportType.class);
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
            ResultsReportType rrt = (ResultsReportType) jaxbUnmarshaller.unmarshal(root);

            // Foundation schema 2.0 nests variant-report within ResultsPayload
            // root is still the same
            try {
                newCases.add(new CaseType(rrt.getVariantReport()));
            }
            catch (NullPointerException ex) {
                newCases.add(new CaseType(rrt.getResultsPayload().getVariantReport()));
            }            
        }

        return newCases;
    }

    /**
     * Return a list of xml files sorted by date (oldest-newest).
     * Source XML filenames are expected to be date-controlled by <filename>_<date of transfer>.xml
     * if more than one date-controlled version of the source XML file exists. 
     * @param sourceDirectory
     * @return 
     */
    private File[] getDateSortedFiles(File sourceDirectory) {
        File[] sourceFiles = sourceDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        });
        
        // sort files alphabetically by case-insensitive filename
        Arrays.sort(sourceFiles, NameFileComparator.NAME_INSENSITIVE_COMPARATOR);        
        return sourceFiles;
    }
    
}
