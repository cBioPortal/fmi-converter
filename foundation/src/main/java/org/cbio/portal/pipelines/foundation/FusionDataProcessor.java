package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.*;
import org.cbio.portal.pipelines.foundation.model.staging.FusionData;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
public class FusionDataProcessor implements ItemProcessor<CaseType, String>{    
    
    @Override
    public String process(final CaseType caseType) throws Exception {
        List<String> fusionRecords = new ArrayList();
        for (RearrangementType re : caseType.getVariantReport().getRearrangements().getRearrangement()) {
            FusionData fd = new FusionData(caseType.getCase(), re);
            fusionRecords.add(transformRecord(fd));
        }
        
        return StringUtils.join(fusionRecords, "\n");
    }
        
    /**
     * transform fusion data record to string record for staging file
     * @param fusionData
     * @return
     * @throws Exception 
     */
    public String transformRecord(FusionData fusionData) throws Exception {
        List<String> record = new ArrayList();
        Map<String,String> map = fusionData.getStagingMap();
        for (String field : map.keySet()) {
            String value = fusionData.getClass().getMethod(map.get(field)).invoke(fusionData).toString();
            record.add(value);
        }
        
        return StringUtils.join(record, "\t");
    }    
    
}
