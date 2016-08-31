package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.CaseType;
import org.cbio.portal.pipelines.foundation.model.staging.ClinicalData;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
public class ClinicalDataProcessor implements ItemProcessor<CaseType, String> {

    @Override
    public String process(final CaseType caseType) throws Exception {
        ClinicalData clinicalData = new ClinicalData(caseType);
        Map<String,String> map = clinicalData.getStagingMap();
        
        List<String> record = new ArrayList();
        for (String field : map.keySet()) {
            String value = clinicalData.getClass().getMethod(map.get(field)).invoke(clinicalData).toString();
            record.add(value);
        }
        
        return StringUtils.join(record, "\t");        
    }
    
}
