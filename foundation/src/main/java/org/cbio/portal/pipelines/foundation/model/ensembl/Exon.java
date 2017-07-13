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
"object_type",
"version",
"species",
"assembly_name",
"end",
"seq_region_name",
"db_type",
"strand",
"id",
"start"
})
public class Exon {

    @JsonProperty("object_type")
    private String objectType;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("species")
    private String species;

    @JsonProperty("assembly_name")
    private String assemblyName;

    @JsonProperty("end")
    private Integer end;

    @JsonProperty("seq_region_name")
    private String seqRegionName;

    @JsonProperty("db_type")
    private String dbType;

    @JsonProperty("strand")
    private Integer strand;

    @JsonProperty("id")
    private String id;

    @JsonProperty("start")
    private Integer start;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
    * No args constructor for use in serialization
    * 
    */
    public Exon() {}

    /**
    * 
    * @param id
    * @param species
    * @param start
    * @param strand
    * @param seqRegionName
    * @param dbType
    * @param objectType
    * @param end
    * @param assemblyName
    * @param version
    */
    public Exon(String objectType, Integer version, String species, String assemblyName, Integer end, String seqRegionName, String dbType, Integer strand, String id, Integer start) {
        this.objectType = objectType;
        this.version = version;
        this.species = species;
        this.assemblyName = assemblyName;
        this.end = end;
        this.seqRegionName = seqRegionName;
        this.dbType = dbType;
        this.strand = strand;
        this.id = id;
        this.start = start;
    }

    @JsonProperty("object_type")
    public String getObjectType() {
        return objectType;
    }

    @JsonProperty("object_type")
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    @JsonProperty("version")
    public Integer getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Integer version) {
        this.version = version;
    }

    @JsonProperty("species")
    public String getSpecies() {
        return species;
    }

    @JsonProperty("species")
    public void setSpecies(String species) {
        this.species = species;
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

    @JsonProperty("db_type")
    public String getDbType() {
        return dbType;
    }

    @JsonProperty("db_type")
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @JsonProperty("strand")
    public Integer getStrand() {
        return strand;
    }

    @JsonProperty("strand")
    public void setStrand(Integer strand) {
        this.strand = strand;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
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