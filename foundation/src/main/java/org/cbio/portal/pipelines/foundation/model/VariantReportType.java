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
    "rearrangements",
    "biomarkers",
    "nonHumanContent"
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
    
    // to continue supporting previous schema format, 
    // biomarkers and non-human-content XML elements are not required 
    @XmlElement(name = "biomarkers", required = true)
    protected BiomarkersType biomarkers;
    
    @XmlElement(name = "non-human-content", required = true)
    protected NonHumanContentType nonHumanContent;

    @XmlAttribute(name = "disease")
    protected String disease;    
    
    @XmlAttribute(name = "disease-ontology")
    protected String diseaseOntology;
    
    @XmlAttribute(name = "flowcell-analysis")
    protected Integer flowcellAnalysis;

    @XmlAttribute(name = "gender")
    protected String gender;

    @XmlAttribute(name = "pathology-diagnosis")
    protected String pathologyDiagnosis;
    
    @XmlAttribute(name = "percent-tumor-nuclei")
    protected String percentTumorNuclei;
    
    @XmlAttribute(name = "pipeline-version")
    protected String pipelineVersion;

    @XmlAttribute(name = "purity-assessment")
    protected String purityAssessment;
    
    @XmlAttribute(name = "specimen")
    protected String specimen;
    
    @XmlAttribute(name = "study")
    protected String study;

    @XmlAttribute(name = "test-request")
    protected String testRequest;
    
    @XmlAttribute(name = "test-type")
    protected String testType;

    @XmlAttribute(name = "tissue-of-origin")
    protected String tissueOfOrigin;

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
     * @return the biomarkers
     */
    public BiomarkersType getBiomarkers() {
        return biomarkers;
    }

    /**
     * @param biomarkers the biomarkers to set
     */
    public void setBiomarkers(BiomarkersType biomarkers) {
        this.biomarkers = biomarkers;
    }

    /**
     * @return the nonHumanContent
     */
    public NonHumanContentType getNonHumanContent() {
        return nonHumanContent;
    }

    /**
     * @param nonHumanContent the nonHumanContent to set
     */
    public void setNonHumanContent(NonHumanContentType nonHumanContent) {
        this.nonHumanContent = nonHumanContent;
    }

    /**
     * @return the disease
     */
    public String getDisease() {
        return disease;
    }

    /**
     * @param disease the disease to set
     */
    public void setDisease(String disease) {
        this.disease = disease;
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
     * @return the flowcellAnalysis
     */
    public Integer getFlowcellAnalysis() {
        return flowcellAnalysis;
    }

    /**
     * @param flowcellAnalysis the flowcellAnalysis to set
     */
    public void setFlowcellAnalysis(Integer flowcellAnalysis) {
        this.flowcellAnalysis = flowcellAnalysis;
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
     * @return the pathologyDiagnosis
     */
    public String getPathologyDiagnosis() {
        return pathologyDiagnosis;
    }

    /**
     * @param pathologyDiagnosis the pathologyDiagnosis to set
     */
    public void setPathologyDiagnosis(String pathologyDiagnosis) {
        this.pathologyDiagnosis = pathologyDiagnosis;
    }

    /**
     * @return the percentTumorNuclei
     */
    public String getPercentTumorNuclei() {
        return percentTumorNuclei;
    }

    /**
     * @param percentTumorNuclei the percentTumorNuclei to set
     */
    public void setPercentTumorNuclei(String percentTumorNuclei) {
        this.percentTumorNuclei = percentTumorNuclei;
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

    /**
     * @return the specimen
     */
    public String getSpecimen() {
        return specimen;
    }

    /**
     * @param specimen the specimen to set
     */
    public void setSpecimen(String specimen) {
        this.specimen = specimen;
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
     * @return the testType
     */
    public String getTestType() {
        return testType;
    }

    /**
     * @param testType the testType to set
     */
    public void setTestType(String testType) {
        this.testType = testType;
    }

    /**
     * @return the tissueOfOrigin
     */
    public String getTissueOfOrigin() {
        return tissueOfOrigin;
    }

    /**
     * @param tissueOfOrigin the tissueOfOrigin to set
     */
    public void setTissueOfOrigin(String tissueOfOrigin) {
        this.tissueOfOrigin = tissueOfOrigin;
    }

}
