package org.cbio.portal.pipelines.foundation.model;

import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualityControlType", propOrder = { 
    "metrics" 
})
public class QualityControlType {
    
    @XmlElement(required = true)
    protected MetricsType metrics;
    
    @XmlAttribute(name = "status")
    protected String status;

    /**
     * @return the metrics
     */
    public MetricsType getMetrics() {
        return metrics;
    }

    /**
     * @param metrics the metrics to set
     */
    public void setMetrics(MetricsType metrics) {
        this.metrics = metrics;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
}
