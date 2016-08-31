package org.cbio.portal.pipelines.foundation.model;

import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetricType", propOrder = {
    "value"
})
public class MetricType {
    
    @XmlValue
    protected String value;
    
    @XmlAttribute(name = "criterion")
    protected String criterion;
   
    @XmlAttribute(name = "name")
    protected String name;
   
    @XmlAttribute(name = "status")
    protected String status;
   
    @XmlAttribute(name = "value")
    protected String metricValue;
    
    public String getValue() {
        return value;
    }   
    
    public void setValue(String value) {
        this.value = value;
    }    

    /**
     * @return the criterion
     */
    public String getCriterion() {
        return criterion;
    }

    /**
     * @param criterion the criterion to set
     */
    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * @return the metricValue
     */
    public String getMetricValue() {
        return metricValue;
    }

    /**
     * @param metricValue
     */
    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }
    
}
