package org.cbio.portal.pipelines.foundation.model;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CasesType", propOrder = {
    "_case"
})
public class CasesType {
    
    @XmlElement(name = "Case")
    protected List<CaseType> _case;

    /**
     * @return the _case
     */
    public List<CaseType> getCase() {
        return _case;
    }

}
