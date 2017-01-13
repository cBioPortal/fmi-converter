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

package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.CaseType;

import java.util.*;
import org.apache.commons.logging.*;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author ochoaa
 */
public class FoundationReader implements ItemStreamReader<CaseType> {
    
    @Value("#{stepExecutionContext['fmiCaseList']}")
    private List<CaseType> fmiCaseList;
    
    private List<CaseType> foundationCaseList = new ArrayList();
    
    private static final Log LOG = LogFactory.getLog(FoundationReader.class);
        
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {           
       
        // check list of foundation cases from execution context
        if (fmiCaseList.isEmpty() || fmiCaseList == null) {
            LOG.error("Error retrieving Foundation case list from execution context.");
        }
        else {
            this.foundationCaseList = new ArrayList(fmiCaseList);
        }
    }
        
    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException{}
    
    @Override
    public void close() throws ItemStreamException {}
    
    @Override
    public CaseType read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {        
        if (!foundationCaseList.isEmpty()) {
            return foundationCaseList.remove(0);
        }
        return null;
    }    
    
}
