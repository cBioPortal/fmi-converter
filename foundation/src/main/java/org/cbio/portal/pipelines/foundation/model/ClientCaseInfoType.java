package org.cbio.portal.pipelines.foundation.model;

import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
@XmlRootElement(name="ClientCaseInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientCaseInfoType", propOrder = {
    "cases"
})
public class ClientCaseInfoType {
   
    @XmlElement(name = "Cases", required = true)
    protected CasesType cases;
  
    /**
     * @return the cases
     */
    public CasesType getCases() {
        return cases;
    }

    /**
     * @param cases the cases to set
     */
    public void setCases(CasesType cases) {
        this.cases = cases;
    }
    
}
