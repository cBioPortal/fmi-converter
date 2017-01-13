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

import java.io.Serializable;
import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RearrangementType", propOrder = {
    "comment",
    "dnaEvidence",
    "rnaEvidence",
    "content"
})
public class RearrangementType {
     
    @XmlElement(name = "comment", required = false)
    protected String comment;
    
    @XmlElement(name = "dna-evidence", required = false)
    protected DnaEvidenceType dnaEvidence;

    @XmlElement(name = "rna-evidence", required = false)
    protected RnaEvidenceType rnaEvidence;
     
    @XmlMixed
    protected List<Serializable> content;

    @XmlAttribute(name = "description")
    protected String description;

    @XmlAttribute(name = "equivocal")
    protected boolean equivocal;
    
    @XmlAttribute(name = "in-frame")
    protected String inFrame;

    @XmlAttribute(name = "other-gene")
    protected String otherGene;

    @XmlAttribute(name = "pos1")
    protected String pos1;

    @XmlAttribute(name = "pos2")
    protected String pos2;

    @XmlAttribute(name = "status")
    protected String status;
 
    @XmlAttribute(name = "supporting-read-pairs")
    protected Short supportingReadPairs;
  
    @XmlAttribute(name = "targeted-gene")
    protected String targetedGene;

    @XmlAttribute(name = "type")
    protected String type;
    
    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return the inFrame
     */
    public String getInFrame() {
        return inFrame;
    }

    /**
     * @param inFrame the inFrame to set
     */
    public void setInFrame(String inFrame) {
        this.inFrame = inFrame;
    }

    /**
     * @return the otherGene
     */
    public String getOtherGene() {
        return otherGene;
    }

    /**
     * @param otherGene the otherGene to set
     */
    public void setOtherGene(String otherGene) {
        this.otherGene = otherGene;
    }

    /**
     * @return the pos1
     */
    public String getPos1() {
        return pos1;
    }

    /**
     * @param pos1 the pos1 to set
     */
    public void setPos1(String pos1) {
        this.pos1 = pos1;
    }

    /**
     * @return the pos2
     */
    public String getPos2() {
        return pos2;
    }

    /**
     * @param pos2 the pos2 to set
     */
    public void setPos2(String pos2) {
        this.pos2 = pos2;
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
     * @return the supportingReadPairs
     */
    public Short getSupportingReadPairs() {
        return supportingReadPairs;
    }

    /**
     * @param supportingReadPairs the supportingReadPairs to set
     */
    public void setSupportingReadPairs(Short supportingReadPairs) {
        this.supportingReadPairs = supportingReadPairs;
    }

    /**
     * @return the targetedGene
     */
    public String getTargetedGene() {
        return targetedGene;
    }

    /**
     * @param targetedGene the targetedGene to set
     */
    public void setTargetedGene(String targetedGene) {
        this.targetedGene = targetedGene;
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
