package org.cbio.portal.pipelines.foundation.model;

import javax.xml.bind.annotation.*;

/**
 *
 * @author ochoaa
 */
@XmlRootElement(name="ResultsReport")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultsReportType", propOrder = {
    "variantReport"
})
public class ResultsReportType {
    
    @XmlElement(name = "variant-report", required = true)
    private VariantReportType variantReport;

    /**
     * @return the variantReport
     */
    public VariantReportType getVariantReport() {
        return variantReport;
    }

    /**
     * @param variantReport the variantReport to set
     */
    public void setVariantReport(VariantReportType variantReport) {
        this.variantReport = variantReport;
    }
    
}
