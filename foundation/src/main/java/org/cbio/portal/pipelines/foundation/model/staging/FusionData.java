package org.cbio.portal.pipelines.foundation.model.staging;

import org.cbio.portal.pipelines.foundation.model.RearrangementType;
import org.cbio.portal.pipelines.foundation.util.FoundationUtils;

import java.util.*;

/**
 * Class for converting RearrangementType to FusionData
 * @author Prithi Chakrapani, ochoaa
 */
public class FusionData {

    private String gene;
    private String entrezGeneId;
    private String center;
    private String tumorSampleBarcode;
    private String fusion;
    private String dNASupport;
    private String rNASupport;
    private String method;
    private String frame;
    private String annotation;

    public FusionData(){}
    
    public FusionData(String sampleId, RearrangementType rearrangement) {
        this.tumorSampleBarcode = sampleId;
        this.gene = rearrangement.getTargetedGene();
        this.entrezGeneId = "0"; 
        this.fusion = rearrangement.getTargetedGene()+"-"+rearrangement.getOtherGene();

        // resolve the rearrangement frame
        this.frame = FoundationUtils.resolveRearrangementFrame(rearrangement.getInFrame());
        
        /**
         * set defaults for MutationData.
         */
        this.center = FoundationUtils.FOUNDATION_CENTER;
        this.dNASupport = FoundationUtils.FOUNDATION_DNA_SUPPORT;
        this.rNASupport = FoundationUtils.FOUNDATION_UNKNOWN;
        this.method = FoundationUtils.FOUNDATION_NA;
        this.annotation = FoundationUtils.FOUNDATION_EMPTY;
    }
                        
    /**
     * @return the gene
     */
    public String getGene() {
        return gene;
    }
    
    /**
     * @param gene the gene to set
     */
    public void setGene(String gene) {
        this.gene = gene;
    }

    /**
     * @return the entrezGeneId
     */
    public String getEntrezGeneId() {
        return entrezGeneId;
    }

    /**
     * @param entrezGeneId the entrezGeneId to set
     */
    public void setEntrezGeneId(String entrezGeneId) {
        this.entrezGeneId = entrezGeneId;
    }

    /**
     * @return the center
     */
    public String getCenter() {
        return center;
    }

    /**
     * @param center the center to set
     */
    public void setCenter(String center) {
        this.center = center;
    }

    /**
     * @return the tumorSampleBarcode
     */
    public String getTumorSampleBarcode() {
        return tumorSampleBarcode;
    }

    /**
     * @param tumorSampleBarcode the tumorSampleBarcode to set
     */
    public void setTumorSampleBarcode(String tumorSampleBarcode) {
        this.tumorSampleBarcode = tumorSampleBarcode;
    }

    /**
     * @return the fusion
     */
    public String getFusion() {
        return fusion;
    }

    /**
     * @param fusion the fusion to set
     */
    public void setFusion(String fusion) {
        this.fusion = fusion;
    }

    /**
     * @return the dNASupport
     */
    public String getdNASupport() {
        return dNASupport;
    }

    /**
     * @param dNASupport the dNASupport to set
     */
    public void setdNASupport(String dNASupport) {
        this.dNASupport = dNASupport;
    }

    /**
     * @return the rNASupport
     */
    public String getrNASupport() {
        return rNASupport;
    }

    /**
     * @param rNASupport the rNASupport to set
     */
    public void setrNASupport(String rNASupport) {
        this.rNASupport = rNASupport;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the frame
     */
    public String getFrame() {
        return frame;
    }

    /**
     * @param frame the frame to set
     */
    public void setFrame(String frame) {
        this.frame = frame;
    }

    /**
     * @return the annotation
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * @param annotation the annotation to set
     */
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }
    
    /**
     * Returns a map linking the staging file column name to the appropriate getter method.
     * @return 
     */    
    public Map<String, String> getStagingMap(){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Hugo_Symbol","getGene");
        map.put("Entrez_Gene_Id","getEntrezGeneId");
        map.put("Center","getCenter");
        map.put("Tumor_Sample_Barcode", "getTumorSampleBarcode");
        map.put("Fusion","getFusion");
        map.put("DNA_support","getdNASupport");
        map.put("RNA_support","getrNASupport");
        map.put("Method","getMethod");
        map.put("Frame","getFrame");
        map.put("Annotation","getAnnotation");

        return map;
    }

}
