package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.CaseType;

import java.util.*;
import org.apache.commons.logging.*;
import org.springframework.batch.item.*;

/**
 *
 * @author ochoaa
 */
public class FoundationReader implements ItemStreamReader<CaseType> {
    
    private List<CaseType> foundationCaseList = new ArrayList();
    
    private static final Log LOG = LogFactory.getLog(FoundationReader.class);
        
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {           
       
        // retrieve list of foundation cases from execution context
        List<CaseType> foundationCaseListExecution = (List<CaseType>) executionContext.get("fmiCaseList");
        if (foundationCaseListExecution.isEmpty() || foundationCaseListExecution == null) {
            LOG.error("Error retrieving Foundation case list from execution context.");
        }
        else {
            this.foundationCaseList = new ArrayList(foundationCaseListExecution);
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
