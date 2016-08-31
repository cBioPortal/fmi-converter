package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.CaseType;

import org.apache.commons.logging.*;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author ochoaa
 */
public class FoundationCompositeProcessor implements ItemProcessor<CaseType, CompositeResultBean>{
     private static final Log LOG = LogFactory.getLog(FoundationCompositeProcessor.class);
    
    private final ClinicalDataProcessor clinicalDataProcessor= new ClinicalDataProcessor();
    private final MutationDataProcessor mutationDataProcessor = new MutationDataProcessor();
    private final FusionDataProcessor fusionDataProcessor = new FusionDataProcessor();
        
    @Override
    public CompositeResultBean process(CaseType ct) throws Exception {
        
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
