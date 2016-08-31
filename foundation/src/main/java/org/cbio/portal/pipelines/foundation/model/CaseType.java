package org.cbio.portal.pipelines.foundation.model;

import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseType", propOrder = {
    "variantReport"
})
public class CaseType {
    
    @XmlElement(name = "variant-report", required = true)
    protected VariantReportType variantReport;
   
    @XmlAttribute(name = "case")
    protected String _case;
   
    @XmlAttribute(name = "fmiCase")
    protected String fmiCase;
  
    @XmlAttribute(name = "hasVariant")
    protected Byte hasVariant;

    public CaseType(){}
    
    public CaseType(VariantReportType vrt) {
        this._case = vrt.getTestRequest();
        this.fmiCase = vrt.getTestRequest();
        this.hasVariant = 1;
        this.variantReport = vrt;
    }
    
    public VariantReportType getVariantReport() {
        return variantReport;
    }
    public void setVariantReport(VariantReportType value) {
        this.variantReport = value;
    }

    /**
     * @return the _case
     */
    public String getCase() {
        return variantReport.getTestRequest();
    }

    /**
     * @param _case the _case to set
     */
    public void setCase(String _case) {
        this._case = _case;
    }

    /**
     * @return the fmiCase
     */
    public String getFmiCase() {
        return fmiCase;
    }

    /**
     * @param fmiCase the fmiCase to set
     */
    public void setFmiCase(String fmiCase) {
        this.fmiCase = fmiCase;
    }

    /**
     * @return the hasVariant
     */
    public Byte getHasVariant() {
        return hasVariant;
    }

    /**
     * @param hasVariant the hasVariant to set
     */
    public void setHasVariant(Byte hasVariant) {
        this.hasVariant = hasVariant;
    }

}
