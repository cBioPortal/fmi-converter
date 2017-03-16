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
import org.cbio.portal.pipelines.foundation.model.staging.FusionData;
import org.cbio.portal.pipelines.foundation.util.*;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
public class FusionDataProcessor implements ItemProcessor<CaseType, String>{    
    
    private GeneDataUtils geneDataUtils;
    
    public void setProperties(GeneDataUtils geneDataUtils) {
        this.geneDataUtils = geneDataUtils;
    }
    
    private static final Log LOG = LogFactory.getLog(FusionDataProcessor.class);
    
    @Override
    public String process(final CaseType caseType) throws Exception {
        List<String> fusionRecords = new ArrayList();
        for (RearrangementType re : caseType.getVariantReport().getRearrangements().getRearrangement()) {
            // resolve gene symbols for targeted gene and other gene for rearrangement type record before converting record
            String targetedGene = geneDataUtils.resolveGeneSymbol(re.getTargetedGene().split("-")[0]);
            String otherGene = geneDataUtils.resolveGeneSymbol(
                    FoundationUtils.resolveOtherGene(re.getTargetedGene(), re.getOtherGene()));
            
            // skip fusion events containing ncRNAs
            if (geneDataUtils.isNcRNA(targetedGene) || geneDataUtils.isNcRNA(otherGene)) {
                LOG.info("Skipping fusion record with ncRNA: " + targetedGene + "-" + otherGene);
                continue;
            }
            re.setTargetedGene(targetedGene);
            re.setOtherGene(otherGene);
            
            // create fusion record and update entrez id before transforming
            FusionData fd = new FusionData(caseType.getCase(), re);
            fd.setEntrezGeneId(geneDataUtils.resolveEntrezId(targetedGene));
            fusionRecords.add(transformRecord(fd));
            
            // check if switched fusion event is valid and update entrez id before mapping
            FusionData fdSwitched = FoundationUtils.getOtherGeneFusionEvent(caseType.getCase(), re, fd.getFusion());
            if (fdSwitched != null) {
                fdSwitched.setEntrezGeneId(geneDataUtils.resolveEntrezId(otherGene));
                fusionRecords.add(transformRecord(fdSwitched));
            }
        }
        return StringUtils.join(fusionRecords, "\n");
    }
        
    /**
     * Transform fusion data record to string record for staging file
     * @param fusionData
     * @return
     * @throws Exception 
     */
    private String transformRecord(FusionData fusionData) throws Exception {        
        List<String> record = new ArrayList();
        Map<String,String> map = fusionData.getStagingMap();
        for (String field : map.keySet()) {
            String value = fusionData.getClass().getMethod(map.get(field)).invoke(fusionData).toString();
            record.add(value);
        }
        
        return StringUtils.join(record, "\t");
    }
    
}
