package org.cbio.portal.pipelines.foundation.model;

import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SamplesType", propOrder = {
    "sample"
})
public class SamplesType {

    @XmlElement(required = true)
    protected SampleType sample;

    /**
     * @return the sample
     */
    public SampleType getSample() {
        return sample;
    }

    /**
     * @param sample the sample to set
     */
    public void setSample(SampleType sample) {
        this.sample = sample;
    }

}
