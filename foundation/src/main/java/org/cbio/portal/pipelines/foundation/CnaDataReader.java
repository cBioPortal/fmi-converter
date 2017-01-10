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
import org.cbio.portal.pipelines.foundation.util.*;

import java.util.*;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.*;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ochoaa
 */
public class CnaDataReader implements ItemStreamReader<String> {
    
    @Autowired
    GeneDataUtils geneDataUtils;
    
    private List<String> foundationCnaRowData;
    private static final Log LOG = LogFactory.getLog(CnaDataReader.class);

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        Map<String, CaseType>  fmiCaseTypeMap = (Map<String, CaseType> ) executionContext.get("fmiCaseTypeMap");
        this.foundationCnaRowData = generateCnaRowData(fmiCaseTypeMap);
    }

    /**
     * Generate map of gene to CNA data 
     * @param fmiCaseMap
     * @return 
     */
    private List<String> generateCnaRowData(Map<String, CaseType> fmiCaseTypeMap) {
        Set<String> geneList = new HashSet<>();
        MultiKeyMap cnaMap = new MultiKeyMap(); 
        
        int noCnaCount = 0; // keep track of how many cases don't have copy number data
        for (CaseType ct : fmiCaseTypeMap.values()) {
            List<CopyNumberAlterationType> cnaTypeList = ct.getVariantReport().getCopyNumberAlterations().getCopyNumberAlteration();
            if (cnaTypeList != null) {
                for (CopyNumberAlterationType cnaType : cnaTypeList) {
                    // create cna record for each gene in cna event
                    List<String> cnaGenes = geneDataUtils.resolveGeneSymbolList(cnaType.getGene().split("/"));
                    for (String gene : cnaGenes) {
                        if (geneDataUtils.isNcRNA(gene)) {
                            LOG.info("Skipping CNA record with ncRNA: " + gene);
                            continue;
                        }                        
                        cnaMap.put(gene, ct.getCase(), FoundationUtils.resolveCnaType(cnaType));
                        geneList.add(gene);
                    }
                }
            }
            else {
                noCnaCount++;
            }            
        }
        if (noCnaCount > 0) {
            LOG.info("Number of cases without CNA data: " + noCnaCount);
        }
        
        // format row data for CNA file                
        List<String> cnaRowData = new ArrayList();
        for (String gene : geneList) {            
            List<String> geneCnaData = new ArrayList();            
            geneCnaData.add(gene);
            geneCnaData.add(geneDataUtils.resolveEntrezId(gene));
            for (String caseId : fmiCaseTypeMap.keySet()) {
                if (cnaMap.containsKey(gene, caseId)) {
                    geneCnaData.add((String) cnaMap.get(gene, caseId));
                }
                else {
                    geneCnaData.add("0");
                }
                
            }
            cnaRowData.add(StringUtils.join(geneCnaData, "\t"));            
        }
        
        return cnaRowData;
    }
        
    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {}

    @Override
    public void close() throws ItemStreamException {}

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!foundationCnaRowData.isEmpty()) {
            return foundationCnaRowData.remove(0);
        }
        return null;
    }
    
}
