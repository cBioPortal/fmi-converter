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
import org.cbio.portal.pipelines.foundation.util.GeneDataUtils;

import java.util.List;
import org.apache.commons.logging.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author ochoaa
 */
public class FoundationCompositeProcessor implements ItemProcessor<CaseType, CompositeResultBean>{

    @Value("#{stepExecutionContext['addNonHumanContentData']}")
    private boolean addData;
    
    @Value("#{stepExecutionContext['nonHumanContentColumns']}")
    private List<String> columns;
    
    @Autowired
    private GeneDataUtils geneDataUtils;
    
    private final ClinicalDataProcessor clinicalDataProcessor = new ClinicalDataProcessor();
    private final MutationDataProcessor mutationDataProcessor = new MutationDataProcessor();
    private final FusionDataProcessor fusionDataProcessor = new FusionDataProcessor();
    
    private static final Log LOG = LogFactory.getLog(FoundationCompositeProcessor.class);
    
    @Override
    public CompositeResultBean process(CaseType ct) throws Exception {
        // set properties for clinical, mutation, and fusion data processors
        clinicalDataProcessor.setProperties(addData, columns);
        mutationDataProcessor.setProperties(geneDataUtils);
        fusionDataProcessor.setProperties(geneDataUtils);
        
        final CompositeResultBean compositeResultBean = new CompositeResultBean();
        try {
            compositeResultBean.setClinicalDataResult(clinicalDataProcessor.process(ct));
            compositeResultBean.setMutationDataResult(mutationDataProcessor.process(ct));
            compositeResultBean.setFusionDataResult(fusionDataProcessor.process(ct));
        }
        catch (NullPointerException ex ) {
            LOG.error("Error processing clinical, fusion, or mutation data for case: " + ct.getCase());
            ex.printStackTrace();            
        }
          
        return compositeResultBean;
    }

}
