/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.*;

import java.io.*;
import java.util.*;
import javax.xml.bind.*;
import org.apache.commons.logging.*;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author ochoaa
 */
public class FoundationXmlGeneratorTasklet implements Tasklet {
    
   @Value("#{jobParameters[outputDirectory]}")
    private String outputDirectory;
   
    @Value("#{jobParameters[cancerStudyId]}")
    private String cancerStudyIdentifier;
    
    private static final Log LOG = LogFactory.getLog(FoundationXmlGeneratorTasklet.class);
    
    @Override
    public RepeatStatus execute(StepContribution stepContext, ChunkContext chunkContext) throws Exception {
        // get fmi case type map from execution context
        Map<String, CaseType>  fmiCaseTypeMap = (Map<String, CaseType> ) chunkContext.getStepContext().getJobExecutionContext().get("fmiCaseTypeMap");
        
        // create new client case info type object to generate merged xml document
        ClientCaseInfoType clientCaseInfo = new ClientCaseInfoType();
        CasesType cases = new CasesType();
        cases.setCase(new ArrayList(fmiCaseTypeMap.values()));
        clientCaseInfo.setCases(cases);
        
        writeFoundationXmlDocument(clientCaseInfo);
        
        return RepeatStatus.FINISHED;
    }
 
    /**
     * Converts instance of ClientCaseInfoType to XML document.
     * 
     * @param clientCaseInfo
     */
    private void writeFoundationXmlDocument(ClientCaseInfoType clientCaseInfo) {
        String xmlData = null;
        StringWriter sw = new StringWriter();
        String outputFilename = outputDirectory + File.separator + cancerStudyIdentifier + "_merged.xml";
        try {
            JAXBContext context = JAXBContext.newInstance(clientCaseInfo.getClass());
            Marshaller jaxbMarshaller = context.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(clientCaseInfo, new File(outputFilename));
            xmlData = sw.toString();
            LOG.info("Merged Foundation XML document written to: " + outputFilename);
        }
        catch (JAXBException ex) {
            LOG.error("Error marshalling XML document from client case info object");
        }
    }
    
}
