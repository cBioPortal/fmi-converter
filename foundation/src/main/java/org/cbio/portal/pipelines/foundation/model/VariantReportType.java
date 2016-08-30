package org.cbio.portal.pipelines.foundation.model;

import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VariantReportType", propOrder = {
    "samples",
    "qualityControl",
    "shortVariants",
    "copyNumberAlterations",
    "rearrangements"
})
public class VariantReportType {

   @XmlElement(required = true)
   protected SamplesType samples;
   
   @XmlElement(name = "quality-control", required = true)
   protected QualityControlType qualityControl;
   
   @XmlElement(name = "short-variants", required = true)
   protected ShortVariantsType shortVariants;
   
   @XmlElement(name = "copy-number-alterations", required = true)
    protected CopyNumberAlterationsType copyNumberAlterations;
   
   @XmlElement(name = "rearrangements", required = true)
    protected RearrangementsType rearrangements;

    @XmlAttribute(name = "disease-ontology")
    protected String diseaseOntology;
    
    @XmlAttribute(name = "gender")
    protected String gender;
    
    @XmlAttribute(name = "pipeline-version")
    protected String pipelineVersion;
   
    @XmlAttribute(name = "study")
    protected String study;
    
    @XmlAttribute(name = "test-request")
    protected String testRequest;
    
    @XmlAttribute(name = "purity-assessment")
    protected String purityAssessment;

    /**
     * @return the samples
     */
    public SamplesType getSamples() {
        return samples;
    }

    /**
     * @param samples the samples to set
     */
    public void setSamples(SamplesType samples) {
        this.samples = samples;
    }

    /**
     * @return the qualityControl
     */
    public QualityControlType getQualityControl() {
        return qualityControl;
    }

    /**
     * @param qualityControl the qualityControl to set
     */
    public void setQualityControl(QualityControlType qualityControl) {
        this.qualityControl = qualityControl;
    }

    /**
     * @return the shortVariants
     */
    public ShortVariantsType getShortVariants() {
        return shortVariants;
    }

    /**
     * @param shortVariants the shortVariants to set
     */
    public void setShortVariants(ShortVariantsType shortVariants) {
        this.shortVariants = shortVariants;
    }

    /**
     * @return the copyNumberAlterations
     */
    public CopyNumberAlterationsType getCopyNumberAlterations() {
        return copyNumberAlterations;
    }

    /**
     * @param copyNumberAlterations the copyNumberAlterations to set
     */
    public void setCopyNumberAlterations(CopyNumberAlterationsType copyNumberAlterations) {
        this.copyNumberAlterations = copyNumberAlterations;
    }

    /**
     * @return the rearrangements
     */
    public RearrangementsType getRearrangements() {
        return rearrangements;
    }

    /**
     * @param rearrangements the rearrangements to set
     */
    public void setRearrangements(RearrangementsType rearrangements) {
        this.rearrangements = rearrangements;
    }

    /**
     * @return the diseaseOntology
     */
    public String getDiseaseOntology() {
        return diseaseOntology;
    }

    /**
     * @param diseaseOntology the diseaseOntology to set
     */
    public void setDiseaseOntology(String diseaseOntology) {
        this.diseaseOntology = diseaseOntology;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the pipelineVersion
     */
    public String getPipelineVersion() {
        return pipelineVersion;
    }

    /**
     * @param pipelineVersion the pipelineVersion to set
     */
    public void setPipelineVersion(String pipelineVersion) {
        this.pipelineVersion = pipelineVersion;
    }

    /**
     * @return the study
     */
    public String getStudy() {
        return study;
    }

    /**
     * @param study the study to set
     */
    public void setStudy(String study) {
        this.study = study;
    }

    /**
     * @return the testRequest
     */
    public String getTestRequest() {
        return testRequest;
    }

    /**
     * @param testRequest the testRequest to set
     */
    public void setTestRequest(String testRequest) {
        this.testRequest = testRequest;
    }

    /**
     * @return the purityAssessment
     */
    public String getPurityAssessment() {
        return purityAssessment;
    }

    /**
     * @param purityAssessment the purityAssessment to set
     */
    public void setPurityAssessment(String purityAssessment) {
        this.purityAssessment = purityAssessment;
    }
    
}
