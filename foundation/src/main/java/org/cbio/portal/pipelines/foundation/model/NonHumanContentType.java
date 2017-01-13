/*
 * Copyright (c) 2017 Memorial Sloan-Kettering Cancer Center.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY OR FITNESS
 * FOR A PARTICULAR PURPOSE. The software and documentation provided hereunder
 * is on an "as is" basis, and Memorial Sloan-Kettering Cancer Center has no
 * obligations to provide maintenance, support, updates, enhancements or
 * modifications. In no event shall Memorial Sloan-Kettering Cancer Center be
 * liable to any party for direct, indirect, special, incidental or
 * consequential damages, including lost profits, arising out of the use of this
 * software and its documentation, even if Memorial Sloan-Kettering Cancer
 * Center has been advised of the possibility of such damage.
 */

/*
 * This file is part of cBioPortal.
 *
 * cBioPortal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.cbio.portal.pipelines.foundation.model;

import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NonHumanContentType", propOrder = {
    "nonHuman"
})
public class NonHumanContentType {
    
    @XmlElement(name = "non-human", required = false)
    protected List<NonHumanType> nonHuman;

    /**
     * @return the nonHuman
     */
    public List<NonHumanType> getNonHuman() {
        if (nonHuman == null) {
            nonHuman = new ArrayList();
        }
        return nonHuman;
    }

    /**
     * @param nonHuman the nonHuman to set
     */
    public void setNonHuman(List<NonHumanType> nonHuman) {
        this.nonHuman = nonHuman;
    }
    
    /**
     * Returns HashMap of non-human content data.
     * @return 
     */
    public Map<String, String> getNonHumanContentData() {
        Map<String, String> nonHumanContentData = new HashMap<>();
        for (NonHumanType nht : nonHuman) {
            nonHumanContentData.put(nht.getOrganism(), nht.getStatus());            
        }
        
        return nonHumanContentData;
    }

}
