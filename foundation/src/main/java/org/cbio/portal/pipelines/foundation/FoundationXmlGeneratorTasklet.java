/*
 * Copyright (c) 2017 Memorial Sloan-Kettering Cancer Center.
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
