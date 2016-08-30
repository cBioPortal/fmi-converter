package org.cbio.portal.pipelines.foundation.model;

import java.io.Serializable;
import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
 @XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SampleType", propOrder = {
    "comment", 
    "content",
    "baitSet",
    "meanExonDepth",
    "percentTumorNuclei"        
})
public class SampleType {
    
   @XmlElement(name = "comment", required = false)
   protected String comment;
   
   @XmlMixed
   protected List<Serializable> content;
   
   @XmlAttribute(name = "bait-set")
   protected String baitSet;
   
   @XmlAttribute(name = "mean-exon-depth")
   protected Float meanExonDepth;
   
   @XmlAttribute(name = "name" )
   protected String name;
   
   @XmlAttribute(name = "percent-tumor-nuclei")
   protected Integer percentTumorNuclei;
   
    /**
     * @return the content
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }   

    /**
     * @return the baitSet
     */
    public String getBaitSet() {
        return baitSet;
    }

    /**
     * @param baitSet the baitSet to set
     */
    public void setBaitSet(String baitSet) {
        this.baitSet = baitSet;
    }

    /**
     * @return the meanExonDepth
     */
    public Float getMeanExonDepth() {
        return meanExonDepth;
    }

    /**
     * @param meanExonDepth the meanExonDepth to set
     */
    public void setMeanExonDepth(Float meanExonDepth) {
        this.meanExonDepth = meanExonDepth;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the percentTumorNuclei
     */
    public Integer getPercentTumorNuclei() {
        return percentTumorNuclei;
    }

    /**
     * @param percentTumorNuclei the percentTumorNuclei to set
     */
    public void setPercentTumorNuclei(Integer percentTumorNuclei) {
        this.percentTumorNuclei = percentTumorNuclei;
    }
    
}
