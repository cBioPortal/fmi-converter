package org.cbio.portal.pipelines.foundation.model;

import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
 @XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetricsType", propOrder = {
    "metric"
})
public class MetricsType {
   
    protected List<MetricType> metric;

    /**
     * @return the metric
     */
    public List<MetricType> getMetric() {
        if (metric == null) {
            metric = new ArrayList();
        }
        return this.metric;
    }    

}
