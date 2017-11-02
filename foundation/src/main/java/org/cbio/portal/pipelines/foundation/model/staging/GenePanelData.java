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

package org.cbio.portal.pipelines.foundation.model.staging;

import com.google.common.base.Strings;
import org.cbio.portal.pipelines.foundation.model.CaseType;

import java.util.*;

/**
 *
 * @author ochoaa
 */
public class GenePanelData {

    private String sampleId;
    private String mutationsPanel;

    public GenePanelData(){}

    public GenePanelData(CaseType caseType) {
        this.sampleId = caseType.getCase();
        this.mutationsPanel = Strings.isNullOrEmpty(caseType.getVariantReport().getSamples().getSample().getBaitSet()) ?
                "" : "FMI-" + caseType.getVariantReport().getSamples().getSample().getBaitSet();
    }

    /**
     * @return the sampleId
     */
    public String getSampleId() {
        return sampleId;
    }

    /**
     * @param sampleId the sampleId to set
     */
    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    /**
     * @return the mutations
     */
    public String getMutationsPanel() {
        return mutationsPanel;
    }

    /**
     * @param mutationsPanel the mutations to set
     */
    public void setMutationsPanel(String mutationsPanel) {
        this.mutationsPanel = mutationsPanel;
    }

    /**
     * Returns a map linking the staging file column name to the appropriate getter method.
     * @return
     */
    public Map<String, String> getStagingMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("SAMPLE_ID", "getSampleId");
        map.put("mutations", "getMutationsPanel");

        return map;
    }
}
