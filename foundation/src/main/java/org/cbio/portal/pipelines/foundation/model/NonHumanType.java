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

import javax.xml.bind.annotation.*;

/**
 *
 * @author ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NonHumanType", propOrder = {
    "dnaEvidence",
    "rnaEvidence"
})
public class NonHumanType {

    @XmlElement(name = "dna-evidence", required = false)
    protected DnaEvidenceType dnaEvidence;

    @XmlElement(name = "rna-evidence", required = false)
    protected RnaEvidenceType rnaEvidence;
    
    @XmlAttribute(name = "organism")
    protected String organism;
    
    @XmlAttribute(name = "status")
    protected String status;
    
    @XmlAttribute(name = "reads-per-million")
    protected Integer readsPerMillion;
    
    /**
     * @return the dnaEvidence
     */
    public DnaEvidenceType getDnaEvidence() {
        return dnaEvidence;
    }

    /**
     * @param dnaEvidence the dnaEvidence to set
     */
    public void setDnaEvidence(DnaEvidenceType dnaEvidence) {
        this.dnaEvidence = dnaEvidence;
    }

    /**
     * @return the rnaEvidence
     */
    public RnaEvidenceType getRnaEvidence() {
        return rnaEvidence;
    }

    /**
     * @param rnaEvidence the rnaEvidence to set
     */
    public void setRnaEvidence(RnaEvidenceType rnaEvidence) {
        this.rnaEvidence = rnaEvidence;
    }

    /**
     * @return the organism
     */
    public String getOrganism() {
        return organism;
    }

    /**
     * @param organism the organism to set
     */
    public void setOrganism(String organism) {
        this.organism = organism;
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
     * @return the readsPerMillion
     */
    public Integer getReadsPerMillion() {
        return readsPerMillion;
    }

    /**
     * @param readsPerMillion the readsPerMillion to set
     */
    public void setReadsPerMillion(Integer readsPerMillion) {
        this.readsPerMillion = readsPerMillion;
    }
        
}
