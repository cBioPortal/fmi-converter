/*
 * Copyright (c) 2016 Memorial Sloan-Kettering Cancer Center.
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

import com.google.common.base.Strings;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseType", propOrder = {
    "variantReport"
})
public class CaseType {
    
    @XmlElement(name = "variant-report", required = true)
    protected VariantReportType variantReport;
   
    @XmlAttribute(name = "case")
    protected String _case;
   
    @XmlAttribute(name = "fmiCase")
    protected String fmiCase;
  
    @XmlAttribute(name = "hasVariant")
    protected Byte hasVariant;

    public CaseType(){}
    
    public CaseType(VariantReportType vrt) {
        this._case = vrt.getTestRequest();
        this.fmiCase = vrt.getTestRequest();
        this.hasVariant = 1;
        this.variantReport = vrt;
    }
    
    public VariantReportType getVariantReport() {
        return variantReport;
    }
    public void setVariantReport(VariantReportType value) {
        this.variantReport = value;
    }

    /**
     * @return the _case
     */
    public String getCase() {
        if (Strings.isNullOrEmpty(variantReport.getTestRequest())) {
            return _case;
        }
        return variantReport.getTestRequest();
    }

    /**
     * @param _case the _case to set
     */
    public void setCase(String _case) {
        this._case = _case;
    }

    /**
     * @return the fmiCase
     */
    public String getFmiCase() {
        return fmiCase;
    }

    /**
     * @param fmiCase the fmiCase to set
     */
    public void setFmiCase(String fmiCase) {
        this.fmiCase = fmiCase;
    }

    /**
     * @return the hasVariant
     */
    public Byte getHasVariant() {
        return hasVariant;
    }

    /**
     * @param hasVariant the hasVariant to set
     */
    public void setHasVariant(Byte hasVariant) {
        this.hasVariant = hasVariant;
    }

}
