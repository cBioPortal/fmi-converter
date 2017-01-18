/*
 * Copyright (c) 2016 Memorial Sloan-Kettering Cancer Center.
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
import org.cbio.portal.pipelines.foundation.model.staging.MutationData;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
public class MutationDataProcessor implements ItemProcessor<CaseType, String> {
   
    @Override
    public String process(final CaseType caseType) throws Exception {
        List<String> mutationRecords = new ArrayList();
        for (ShortVariantType sv : caseType.getVariantReport().getShortVariants().getShortVariant()) {
            MutationData md = new MutationData(caseType.getCase(), sv);
            mutationRecords.add(transformRecord(md));                
        }
        
        return StringUtils.join(mutationRecords, "\n");
    }
        
    /**
     * transform mutation data record to string record for staging file
     * @param mutationData
     * @return
     * @throws Exception 
     */
    public String transformRecord(MutationData mutationData) throws Exception {
        List<String> record = new ArrayList();
        Map<String,String> map = mutationData.getStagingMap();
        for (String field : map.keySet()) {
            String value = mutationData.getClass().getMethod(map.get(field)).invoke(mutationData).toString();
            record.add(value);
        }
        
        return StringUtils.join(record, "\t");
    }    

}
