/*
 * Copyright (c) 2016-17 Memorial Sloan-Kettering Cancer Center.
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
 * @author Prithi Chakrapani, ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CopyNumberAlterationType", propOrder = {
    "dnaEvidence",
    "rnaEvidence"
})
public class CopyNumberAlterationType {
    
    @XmlElement(name = "dna-evidence", required = false)
    protected DnaEvidenceType dnaEvidence;

    @XmlElement(name = "rna-evidence", required = false)
    protected RnaEvidenceType rnaEvidence;
   
    @XmlAttribute(name = "copy-number")
    protected Byte copyNumber;
   
    @XmlAttribute(name = "equivocal")
    protected boolean equivocal;
    
    @XmlAttribute(name = "gene")
    protected String gene;
   
    @XmlAttribute(name = "number-of-exons")
    protected String numberOfExons;

    @XmlAttribute(name = "position")
    protected String position;

    @XmlAttribute(name = "ratio")
    protected Float ratio;
  
    @XmlAttribute(name = "status")
    protected String status;
   
    @XmlAttribute(name = "type")
    protected String type;

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
     * @return the copyNumber
     */
    public Byte getCopyNumber() {
        return copyNumber;
    }

    /**
     * @param copyNumber the copyNumber to set
     */
    public void setCopyNumber(Byte copyNumber) {
        this.copyNumber = copyNumber;
    }

    /**
     * @return the equivocal
     */
    public boolean isEquivocal() {
        return equivocal;
    }

    /**
     * @param equivocal the equivocal to set
     */
    public void setEquivocal(boolean equivocal) {
        this.equivocal = equivocal;
    }
    
    /**
     * @return the gene
     */
    public String getGene() {
        return gene;
    }

    /**
     * @param gene the gene to set
     */
    public void setGene(String gene) {
        this.gene = gene;
    }

    /**
     * @return the numberOfExons
     */
    public String getNumberOfExons() {
        return numberOfExons;
    }

    /**
     * @param numberOfExons the numberOfExons to set
     */
    public void setNumberOfExons(String numberOfExons) {
        this.numberOfExons = numberOfExons;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the ratio
     */
    public Float getRatio() {
        return ratio;
    }

    /**
     * @param ratio the ratio to set
     */
    public void setRatio(Float ratio) {
        this.ratio = ratio;
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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
