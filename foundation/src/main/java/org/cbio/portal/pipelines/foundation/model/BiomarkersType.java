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

import java.io.Serializable;
import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BiomarkersType", propOrder = {
    "content",
    "microsatelliteInstability", 
    "tumorMutationBurden"
})
public class BiomarkersType {
    
    @XmlMixed
    protected List<Serializable> content;

    @XmlElement(name = "microsatellite-instability", required = false)
    protected MicrosatelliteInstabilityType microsatelliteInstability;

    @XmlElement(name = "tumor-mutation-burden", required = false)
    protected TumorMutationBurdenType tumorMutationBurden;

    /**
     * @return the content
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<>();
        }
        return this.content;
    }    
    
    /**
     * @param content the content to set
     */
    public void setContent(List<Serializable> content) {
        this.content = content;
    }
    
    /**
     * @return the microsatelliteInstability
     */
    public MicrosatelliteInstabilityType getMicrosatelliteInstability() {
        return microsatelliteInstability;
    }

    /**
     * @param microsatelliteInstability the microsatelliteInstability to set
     */
    public void setMicrosatelliteInstability(MicrosatelliteInstabilityType microsatelliteInstability) {
        this.microsatelliteInstability = microsatelliteInstability;
    }

    /**
     * @return the tumorMutationBurden
     */
    public TumorMutationBurdenType getTumorMutationBurden() {
        return tumorMutationBurden;
    }

    /**
     * @param tumorMutationBurden the tumorMutationBurden to set
     */
    public void setTumorMutationBurden(TumorMutationBurdenType tumorMutationBurden) {
        this.tumorMutationBurden = tumorMutationBurden;
    }
    
}
