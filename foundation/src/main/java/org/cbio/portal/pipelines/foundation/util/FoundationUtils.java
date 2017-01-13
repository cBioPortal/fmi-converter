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

package org.cbio.portal.pipelines.foundation.util;

import org.cbio.portal.pipelines.foundation.model.*;
import org.cbio.portal.pipelines.foundation.model.staging.*;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.google.common.base.Strings;

/**
 * Util class for resolving data
 * @author ochoaa
 */
@Repository
public class FoundationUtils {        
    public static final String FOUNDATION_CENTER = "Foundation";
    public static final String FOUNDATION_BUILD = "37";
    public static final String FOUNDATION_UNKNOWN = "unknown";    
    public static final String FOUNDATION_NA = "NA";
    public static final String FOUNDATION_EMPTY = "";    
    public static final String FOUNDATION_DNA_SUPPORT = "yes";
    public static final String FOUNDATION_MUTATION_STATUS = "SOMATIC";
    public static final String FOUNDATION_SEQUENCE_SOURCE = "Exome";
    public static final Set<String> NULL_EMPTY_VALUES = new HashSet(Arrays.asList(new String[]{"NA", "N/A"}));
    
    private static final Map<Character,String> complementMap = new HashMap<>();
    private static final Map<Object, String> variantTypeMap = new HashMap<>();  
    private static final Map<String, String> variantClassificationMap = new HashMap<>();  

    @Autowired
    public void setReverseComplementMap() {        
        complementMap.put('A',"T");
        complementMap.put('G', "C");
        complementMap.put('C', "G");
        complementMap.put('T',"A");
    }
    
    @Autowired
    public void setVariantTypeMap() {
        variantTypeMap.put(1, "SNP");
        variantTypeMap.put(2, "DNP");
        variantTypeMap.put(3, "TNP");
        variantTypeMap.put(">3", "ONP");
    }
    
    @Autowired
    public void setVariantClassificationMap() {
        variantClassificationMap.put("missense", "Missense_Mutation");
        variantClassificationMap.put("nonsense", "Nonsense_Mutation");
        variantClassificationMap.put("splice", "Splice_Site");
        variantClassificationMap.put("nonframeshift", "In_Frame_");
        variantClassificationMap.put("frameshift", "Frame_Shift_");
    }
    
    
    /**
     * Resolve the reference allele.
     * @param bases
     * @param alleleList
     * @param cdsEffect
     * @param strand
     * @return 
     */    
    public static String resolveReferenceAllele(String bases, String[] alleleList, String cdsEffect, String strand) {
        String referenceAllele = "" ; 

        if (cdsEffect.contains("ins") || (cdsEffect.contains(">") && alleleList.length == 1)){
            referenceAllele = "-";
        }
        else if (!Strings.isNullOrEmpty(bases)) {
            referenceAllele = alleleList[0].toUpperCase();
        }
        
        return strand.equals("-")?getReverseComplement(referenceAllele):referenceAllele;
    }
    
    /**
     * Resolve the tumor allele.
     * @param alleleList
     * @param cdsEffect
     * @param strand
     * @return 
     */
    public static String resolveTumorAllele(String bases, String[] alleleList, String cdsEffect, String strand) {
        String tumorAllele = "";
        if (alleleList.length > 1) {
            tumorAllele = alleleList[alleleList.length-1].toUpperCase();
        }
        else if (cdsEffect.contains("ins")) {
            if (Strings.isNullOrEmpty(alleleList[0])) {
                StringBuilder stringBuilder = new StringBuilder();
                String[] effects = cdsEffect.split("ins");
                for (int i = 0; i < Integer.parseInt(effects[effects.length - 1]); i++) {
                    stringBuilder.append("N");
                }
                tumorAllele = stringBuilder.toString();
            }
            else {
                tumorAllele = alleleList[0].toUpperCase();
            }
        }
        else if (cdsEffect.contains("del")) {
            tumorAllele = "-";
        }
        else if (cdsEffect.contains(">") && alleleList.length == 1) {
            tumorAllele = alleleList[0].toUpperCase();
        }
                
        return strand.equals("-")?getReverseComplement(tumorAllele):tumorAllele;
    }
    
    /**
     * Return the reverse complement.
     * @param input
     * @return 
     */
    public static String getReverseComplement(String input) {
        if (Strings.isNullOrEmpty(input) || input.equals("-")) {
            return input;
        }
        
        String reverseComplement = "";
        for (char nuc : input.toCharArray()) {
            String complement = "N";
            if (complementMap.containsKey(nuc)) {
                complement = complementMap.get(nuc);
            }
            reverseComplement = complement + reverseComplement;
        }
        
        return reverseComplement;
    }
    
    /**
     * Resolve the variant type.
     * @param referenceAllele
     * @param tumorAllele
     * @param cdsEffect
     * @return 
     */
    public static String resolveVariantType(String referenceAllele, String tumorAllele, String cdsEffect) {
        String variantType = "UNK";
        
        if (!Strings.isNullOrEmpty(referenceAllele) && !Strings.isNullOrEmpty(tumorAllele)) {
            if (referenceAllele.equals("-") || referenceAllele.length() < tumorAllele.length() || cdsEffect.contains("ins")) {
                variantType = "INS";
            }
            else if (tumorAllele.equals("-") || referenceAllele.length() > tumorAllele.length() || cdsEffect.contains("del")) {
                variantType = "DEL";
            }
            else {
                if (referenceAllele.length() == tumorAllele.length()) {
                    if (referenceAllele.length() > 3) {
                        variantType = variantTypeMap.get(">3");
                    }
                    else {
                        variantType = variantTypeMap.get(referenceAllele.length());
                    }                    
                }
            }            
        }
        else {
            // catch any variants with nucleotides missing in the cds effects but still indicate an INDEL
            if (cdsEffect.contains("ins")) {
                variantType = "INS";                
            }
            else if (cdsEffect.contains("del")) {
                variantType = "DEL";
            }
        }

        return variantType;
    }
    
    public static String resolveVariantClassification(String functionalEffect, String variantType) {
        String variantClassification = "";        
        if (variantClassificationMap.containsKey(functionalEffect)) {
            if (!functionalEffect.contains("frameshift")) {
                variantClassification = variantClassificationMap.get(functionalEffect);
            }
            else if (!variantType.equals("UNK")) {
                variantClassification = variantClassificationMap.get(functionalEffect) + variantType;
            }            
        }
        else {
            variantClassification = Strings.isNullOrEmpty(functionalEffect)?"unknown":functionalEffect;
        }        
        
        return variantClassification;
    }
    
    /**
     * Resolve the start position.
     * @param position
     * @param variantType
     * @return 
     */
    public static String resolveStartPosition(String position, String variantType) {
        int startPosition = Integer.valueOf(position.split(":")[1]);
        
        switch (variantType) {
            case "INS":
            case "DEL":
                return String.valueOf(startPosition + 1);
            case "DNP":
                return String.valueOf(startPosition - 1);
            default:
                return String.valueOf(startPosition);
        }
    }
    
    /**
     * Calculate the end position.
     * @param cdsEffect
     * @param startPosition
     * @return 
     */
    public static String calculateEndPosition(String cdsEffect, int startPosition) {
        int endPosition = -1;
        
        String[] changes = cdsEffect.replaceAll("[^atcgATCG]", " ").trim().split(" ");
        if (cdsEffect.contains("del")) {
            if (changes.length > 1) {
                endPosition = startPosition + changes[1].length() - changes[0].length() - 1;
            }
            else {
                endPosition = startPosition +changes[0].length() - 1;
            }
        }
        else if (cdsEffect.contains("ins")) {
            endPosition = startPosition + 1;
        }
        else if (cdsEffect.contains(">") && changes.length > 0) {
            endPosition = startPosition + changes[0].length() - 1;
        }
        else {
            endPosition = startPosition;
        }
        
        return String.valueOf(endPosition);
    }
    
    /**
     * Resolve the fusion event.
     * @param targetedGene
     * @param otherGene
     * @param description
     * @param comment
     * @return 
     */
    public static String resolveFusionEvent(String targetedGene, String otherGene, String description, String comment) {
        String[] fusionEventParts = targetedGene.split("-");
        
        String fusionEvent = fusionEventParts[0];
        if (!Strings.isNullOrEmpty(otherGene) && !NULL_EMPTY_VALUES.contains(otherGene)
                && !otherGene.equals(targetedGene) && !otherGene.contains("intergenic")) {
            fusionEvent += "-" + otherGene + " " +description;
        }
        else {
            if (targetedGene.contains("intergenic") || otherGene.contains("intergenic")) {
                fusionEvent += "-intragenic";
            }
            else {
                fusionEvent += " " + description;
            }
        }

        if (!Strings.isNullOrEmpty(comment)) {
            fusionEvent += ": " + comment;
        }
        return fusionEvent;
    }
    
    
    /**
     * Get the fusion event with the other gene set as the targeted gene.
     * @param sampleId
     * @param rearrangement
     * @return FusionData
     */
    public static FusionData getOtherGeneFusionEvent(String sampleId, RearrangementType rearrangement, String fusionEvent) {
        // return null if other gene is empty, intergenic, or contains '/'
        if (Strings.isNullOrEmpty(rearrangement.getOtherGene()) || 
                NULL_EMPTY_VALUES.contains(rearrangement.getOtherGene()) || 
                rearrangement.getOtherGene().equals(rearrangement.getTargetedGene()) || 
                rearrangement.getOtherGene().contains("intergenic") || 
                rearrangement.getOtherGene().contains("/")) {
            return null;
        }
        
        // switch the targeted gene and other gene values
        String[] targetedGeneParts = rearrangement.getTargetedGene().split("-");
        String newOtherGene = targetedGeneParts[0];
        String newTargetedGene = rearrangement.getOtherGene();
        if (targetedGeneParts.length > 1) {
            newTargetedGene += "-" + targetedGeneParts[1];
        }
        rearrangement.setTargetedGene(newTargetedGene);
        rearrangement.setOtherGene(newOtherGene);
        
        // create FusionData instance with switched targeted/other gene and given fusion event 
        FusionData fdSwitched = new FusionData(sampleId, rearrangement);
        fdSwitched.setFusion(fusionEvent);
        
        return fdSwitched;
    }

    /**
     * Resolve the rearrangement frame.
     * @param rearrangementFrame
     * @return 
     */
    public static String resolveRearrangementFrame(String rearrangementFrame) {
        String frame;
        if (rearrangementFrame.equals("in-frame")) {
            frame = "in-frame";
        }
        else if (rearrangementFrame.equals("out-of-frame")) {
            frame = "out of frame";
        }
        else {
            frame = "unknown";
        }
        
        return frame;
    }
    
    /**
     * Resolve the copy number alteration type.
     * @param cnaType
     * @return 
     */
    public static String resolveCnaType(CopyNumberAlterationType cnaType) {
        String alteration = cnaType.getType();
        switch (alteration) {
            case "amplification":
                return "2";
            case "loss":
                return "-2";
            default:
                return "0";
        }
    }
    
    /**
     * Resolves gene symbol for other gene in rearrangement type record.
     * @param targetedGene
     * @param otherGene
     * @return 
     */
    public static String resolveOtherGene(String targetedGene, String otherGene) {        
        if (Strings.isNullOrEmpty(otherGene) || NULL_EMPTY_VALUES.contains(otherGene)) {
            return otherGene;
        }
        
        // resolve value of other gene in '/'- and ';'-delimited gene symbol lists
        if (otherGene.contains("/") && !otherGene.contains("intergenic")) {
            // '/'-delimited lists sometimes contain the targeted gene symbol
            // return the first gene symbol in this list not matching the targeted gene
            String[] genes = otherGene.split("/");
            for (String gene : genes) {
                // set gene symbol only if doesn't match targeted gene
                if (!gene.equals(targetedGene)){
                    return gene;
                }
            }
        }
        else if (otherGene.contains(";")) {
            // ';'-delimited lists contain genes that are overlapping on the genome
            // return the first gene symbol in this list
            String[] genes = otherGene.split(";");
            return genes[0];
        }
        
        return otherGene;
    }
    
    /**
     * Resolves the tumor nuclei percent given an instance of CaseType.
     * @param caseType
     * @return 
     */
    public static String resolveTumorNucleiPercent(CaseType caseType) {
        String tumorNucleiPercent = "";
        
        // resolve the value for tumor nuclei percent 
        if (!Strings.isNullOrEmpty(caseType.getVariantReport().getPercentTumorNuclei()) && 
                !NULL_EMPTY_VALUES.contains(caseType.getVariantReport().getPercentTumorNuclei())) {
            tumorNucleiPercent = caseType.getVariantReport().getPercentTumorNuclei();
        }
        else if (caseType.getVariantReport().getSamples().getSample().getPercentTumorNuclei() != null) {
            tumorNucleiPercent = String.valueOf(caseType.getVariantReport().getSamples().getSample().getPercentTumorNuclei());
        }
        else if (caseType.getVariantReport().getQualityControl().getMetrics() != null) {
            List<MetricType> metricData = caseType.getVariantReport().getQualityControl().getMetrics().getMetric();
            for (MetricType m : metricData) {
                if (m.getName().equals("Tumor Nuclei Percent")) {
                    tumorNucleiPercent = m.getValue();
                    break;
                }
            }
        }
        
        return tumorNucleiPercent;
    }
    
}
