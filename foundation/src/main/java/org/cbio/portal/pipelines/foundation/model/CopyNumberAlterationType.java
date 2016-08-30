package org.cbio.portal.pipelines.foundation.model;

import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CopyNumberAlterationType", propOrder = {
    "value"
})
public class CopyNumberAlterationType {
    @XmlValue
    protected String value;
   
    @XmlAttribute(name = "copy-number")
    protected Byte copyNumber;
   
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
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
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
