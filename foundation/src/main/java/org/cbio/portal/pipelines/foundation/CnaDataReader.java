package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.*;
import org.cbio.portal.pipelines.foundation.util.FoundationUtils;

import java.util.*;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.*;
import org.springframework.batch.item.*;

/**
 *
 * @author ochoaa
 */
public class CnaDataReader implements ItemStreamReader<String> {
    
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
                    cnaMap.put(cnaType.getGene(), ct.getCase(), FoundationUtils.resolveCnaType(cnaType));
                    geneList.add(cnaType.getGene());
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
