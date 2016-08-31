package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.CaseType;

import java.util.*;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ExecutionContext;
 
/**
 * Listener to add case list to execution context
 * @author Prithi Chakrapani, ochoaa
 */
public class FoundationStepListener implements StepExecutionListener {

  @Override
   public void beforeStep(StepExecution stepExecution) {
       // add Foundation case list to step execution context
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        Map<String, CaseType> fmiCaseTypeMap = (Map<String, CaseType>) executionContext.get("fmiCaseTypeMap");
        stepExecution.getExecutionContext().put("fmiCaseList", new ArrayList(fmiCaseTypeMap.values()));
    }     

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }    
 
}
