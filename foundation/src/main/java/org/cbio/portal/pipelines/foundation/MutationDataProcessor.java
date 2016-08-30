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
