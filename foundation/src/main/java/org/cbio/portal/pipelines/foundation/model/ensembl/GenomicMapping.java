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

package org.cbio.portal.pipelines.foundation.model.ensembl;

import java.util.*;
import com.fasterxml.jackson.annotation.*;
import javax.annotation.Generated;

/**
 *
 * @author ochoaa
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
"assembly_name",
"end",
"seq_region_name",
"gap",
"strand",
"coord_system",
"rank",
"start"
})
public class GenomicMapping {

    @JsonProperty("assembly_name")
    private String assemblyName;

    @JsonProperty("end")
    private Integer end;

    @JsonProperty("seq_region_name")
    private String seqRegionName;

    @JsonProperty("gap")
    private Integer gap;

    @JsonProperty("strand")
    private Integer strand;

    @JsonProperty("coord_system")
    private String coordSystem;

    @JsonProperty("rank")
    private Integer rank;

    @JsonProperty("start")
    private Integer start;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
    * No args constructor for use in serialization
    * 
    */
    public GenomicMapping() {}
    
    /**
     * 
     * @param assemblyName
     * @param end
     * @param seqRegionName
     * @param gap
     * @param strand
     * @param coordSystem
     * @param rank
     * @param start 
     */
    public GenomicMapping(String assemblyName, Integer end, String seqRegionName, Integer gap, Integer strand, String coordSystem, Integer rank, Integer start) {
        this.assemblyName = assemblyName;
        this.end = end;
        this.seqRegionName = seqRegionName;
        this.gap = gap;
        this.strand = strand;
        this.coordSystem = coordSystem;
        this.rank = rank;
        this.start = start;
    }
    
    @JsonProperty("assembly_name")
    public String getAssemblyName() {
        return assemblyName;
    }

    @JsonProperty("assembly_name")
    public void setAssemblyName(String assemblyName) {
        this.assemblyName = assemblyName;
    }

    @JsonProperty("end")
    public Integer getEnd() {
        return end;
    }

    @JsonProperty("end")
    public void setEnd(Integer end) {
        this.end = end;
    }

    @JsonProperty("seq_region_name")
    public String getSeqRegionName() {
        return seqRegionName;
    }

    @JsonProperty("seq_region_name")
    public void setSeqRegionName(String seqRegionName) {
        this.seqRegionName = seqRegionName;
    }

    @JsonProperty("gap")
    public Integer getGap() {
        return gap;
    }

    @JsonProperty("gap")
    public void setGap(Integer gap) {
        this.gap = gap;
    }

    @JsonProperty("strand")
    public Integer getStrand() {
        return strand;
    }

    @JsonProperty("strand")
    public void setStrand(Integer strand) {
        this.strand = strand;
    }

    @JsonProperty("coord_system")
    public String getCoordSystem() {
        return coordSystem;
    }

    @JsonProperty("coord_system")
    public void setCoordSystem(String coordSystem) {
        this.coordSystem = coordSystem;
    }

    @JsonProperty("rank")
    public Integer getRank() {
        return rank;
    }

    @JsonProperty("rank")
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @JsonProperty("start")
    public Integer getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(Integer start) {
        this.start = start;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
