package org.cbio.portal.pipelines.foundation.model;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CopyNumberAlterationsType", propOrder = {
    "copyNumberAlteration"
})
public class CopyNumberAlterationsType {

    @XmlElement(name = "copy-number-alteration", required = false)
    private List<CopyNumberAlterationType> copyNumberAlteration;

    /**
     * @return the copyNumberAlteration
     */
    public List<CopyNumberAlterationType> getCopyNumberAlteration() {
        return copyNumberAlteration;
    }

    /**
     * @param copyNumberAlteration the copyNumberAlteration to set
     */
    public void setCopyNumberAlteration(List<CopyNumberAlterationType> copyNumberAlteration) {
        this.copyNumberAlteration = copyNumberAlteration;
    }

}
