package org.cbio.portal.pipelines.foundation.model;

import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShortVariantsType", propOrder = {
    "shortVariant"
})
public class ShortVariantsType {
    
    @XmlElement(name = "short-variant")
    protected List<ShortVariantType> shortVariant;
    
    /**
     * @return the shortVariant
     */
    public List<ShortVariantType> getShortVariant() {
        if (shortVariant == null) {
            shortVariant = new ArrayList();
        }
        return shortVariant;
    }

    /**
     * @param shortVariant the shortVariant to set
     */
    public void setShortVariant(List<ShortVariantType> shortVariant) {
        this.shortVariant = shortVariant;
    }
   
}
