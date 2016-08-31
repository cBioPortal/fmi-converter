package org.cbio.portal.pipelines.foundation;

/**
 *Composite result bean for Clinical, Mutation, and Fusion data.
 * @author ochoaa
 */
public class CompositeResultBean {
   
    private String clinicalDataResult;
    private String mutationDataResult;
    private String fusionDataResult;

    /**
     * @return the clinicalDataResult
     */
    public String getClinicalDataResult() {
        return clinicalDataResult;
    }

    /**
     * @param clinicalDataResult the clinicalDataResult to set
     */
    public void setClinicalDataResult(String clinicalDataResult) {
        this.clinicalDataResult = clinicalDataResult;
    }

    /**
     * @return the mutationDataResult
     */
    public String getMutationDataResult() {
        return mutationDataResult;
    }

    /**
     * @param mutationDataResult the mutationDataResult to set
     */
    public void setMutationDataResult(String mutationDataResult) {
        this.mutationDataResult = mutationDataResult;
    }

    /**
     * @return the fusionDataResult
     */
    public String getFusionDataResult() {
        return fusionDataResult;
    }

    /**
     * @param fusionDataResult the fusionDataResult to set
     */
    public void setFusionDataResult(String fusionDataResult) {
        this.fusionDataResult = fusionDataResult;
    }
    
}
