package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.CaseType;

import java.util.*;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ExecutionContext;

/**
 * Listener to add case data map to execution context
 * @author ochoaa
 */
public class CnaStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // add map of case id to case data to execution context
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        Map<String, CaseType> fmiCaseTypeMap = (Map<String, CaseType>) executionContext.get("fmiCaseTypeMap");
        stepExecution.getExecutionContext().put("fmiCaseTypeMap", fmiCaseTypeMap);
    }       

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
    
}
