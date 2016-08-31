package org.cbio.portal.pipelines.foundation.model;

import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RearrangementsType", propOrder = {
    "rearrangement"
    })
public class RearrangementsType {

    @XmlElement(required = false)
    protected List<RearrangementType> rearrangement;
    
    /**
     * @return the rearrangement
     */
    public List<RearrangementType> getRearrangement() {
        if (rearrangement == null) {
            rearrangement = new ArrayList();
        }
        return rearrangement;
    }

    /**
     * @param rearrangement the rearrangement to set
     */
    public void setRearrangement(List<RearrangementType> rearrangement) {
        this.rearrangement = rearrangement;
    }

}
