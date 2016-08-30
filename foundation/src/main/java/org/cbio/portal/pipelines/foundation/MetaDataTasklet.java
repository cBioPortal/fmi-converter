package org.cbio.portal.pipelines.foundation;

import java.util.*;
import javax.annotation.Resource;

import org.apache.commons.logging.*;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;

/**
 * Tasklet for loading meta data and writing meta staging files. 
 * @author ochoaa
 */
public class MetaDataTasklet implements Tasklet {

    @Value("#{jobParameters[outputDirectory]}")
    private String outputDirectory;
    
    @Value("#{jobParameters[cancerStudyId]}")
    private String cancerStudyIdentifier;

    @Resource(name="mutationsMetaData")    
    public Map<String, String> mutationsMetaData;

    @Resource(name="cnaMetaData")    
    public Map<String, String> cnaMetaData;

    @Resource(name="fusionsMetaData")    
    public Map<String, String> fusionsMetaData;    
    
    private static final Log LOG = LogFactory.getLog(MetaDataTasklet.class);
    
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
        writeMutationsMetaFile(executionContext);
        writeCnaMetaFile(executionContext);
        writeFusionsMetaFile(executionContext) ;
        
        return RepeatStatus.FINISHED;
    }
    
    /**
     * Load/Modify mutations meta data and write to meta file.
     * @param executionContext
     * @throws Exception 
     */
    private void writeMutationsMetaFile(ExecutionContext executionContext) throws Exception {
        String metaFilename = outputDirectory + "meta_mutations_extended.txt";
        
        List<String> mutationsData = new ArrayList();
        for (String key : mutationsMetaData.keySet()) {
            String value = mutationsMetaData.get(key).replace("<study_id>", cancerStudyIdentifier);
            mutationsData.add(key+":"+value);
        }

        LOG.info("Writing mutations meta file: " + metaFilename);
        writeMetaStagingFile(executionContext, metaFilename, mutationsData);
    }
    
    /**
     * Load/Modify CNA meta data and write to meta file.
     * @param executionContext
     * @throws Exception 
     */    
    private void writeCnaMetaFile(ExecutionContext executionContext) throws Exception {
        String metaFilename = outputDirectory + "meta_CNA.txt";
        
        List<String> cnaData = new ArrayList();
        for (String key : cnaMetaData.keySet()) {
            String value = cnaMetaData.get(key).replace("<study_id>", cancerStudyIdentifier);
            cnaData.add(key+":"+value);
        }

        LOG.info("Writing CNA meta file: " + metaFilename);
        writeMetaStagingFile(executionContext, metaFilename, cnaData);
    }  
    
    /**
     * Load/Modify fusions meta data and write to meta file.
     * @param executionContext
     * @throws Exception 
     */    
    private void writeFusionsMetaFile(ExecutionContext executionContext) throws Exception {
        String metaFilename = outputDirectory + "meta_fusions.txt";
        
        List<String> fusionsData = new ArrayList();
        for (String key : fusionsMetaData.keySet()) {
            String value = fusionsMetaData.get(key).replace("<study_id>", cancerStudyIdentifier);
            fusionsData.add(key+":"+value);
        }

        LOG.info("Writing fusions meta file: " + metaFilename);
        writeMetaStagingFile(executionContext, metaFilename, fusionsData);
    }      
    
    /**
     * Create and Write meta data file.
     * @param executionContext
     * @throws Exception 
     */    
    private void writeMetaStagingFile(ExecutionContext executionContext, String metaFilename, List<String> metaData) throws Exception {
        FlatFileItemWriter<String> flatFileItemWriter = new FlatFileItemWriter<>();
        PassThroughLineAggregator aggr = new PassThroughLineAggregator();

        try {       
            flatFileItemWriter.setLineAggregator(aggr);
            flatFileItemWriter.setResource(new FileSystemResource(metaFilename));
            flatFileItemWriter.open(executionContext);
            flatFileItemWriter.write(metaData);
            flatFileItemWriter.close();            
        }
        catch (Exception ex) {
            LOG.error("Error writing meta data file: " + metaFilename);
            ex.printStackTrace();
        }        

    }
    
}
