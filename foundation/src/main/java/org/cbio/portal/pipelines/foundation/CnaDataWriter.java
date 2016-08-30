package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.CaseType;

import java.io.*;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.*;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;

/**
 *
 * @author ochoaa
 */
public class CnaDataWriter implements ItemStreamWriter<String> {

    @Value("#{jobParameters[outputDirectory]}")
    private String outputDirectory;

    private final List<String> writeList = new ArrayList<>();
    private final FlatFileItemWriter<String> flatFileItemWriter = new FlatFileItemWriter<>();            
    
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {                
        // retrieve list of foundation cases from execution context
        final Map<String, CaseType> fmiCaseTypeMap = (Map<String, CaseType>) executionContext.get("fmiCaseTypeMap");
                
        String stagingFile = outputDirectory +  "data_CNA.txt" ;
        PassThroughLineAggregator aggr = new PassThroughLineAggregator();
        flatFileItemWriter.setLineAggregator(aggr);
        flatFileItemWriter.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write(getHeader(fmiCaseTypeMap.keySet()));
            }
        });
        flatFileItemWriter.setResource(new FileSystemResource(stagingFile));
        flatFileItemWriter.open(executionContext);
    }

    private String getHeader(Set<String> fmiCaseList) {
        List<String> header = new ArrayList();
        header.add("Hugo_Symbol");
        header.addAll(fmiCaseList);
        
        return StringUtils.join(header, "\t");
    }
    
    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {}

    @Override
    public void close() throws ItemStreamException
    {
        flatFileItemWriter.close();
    }

    @Override
    public void write(List<? extends String> items) throws Exception
    {
        writeList.clear();
        List<String> writeList = new ArrayList<String>();
        for (String result : items) {
            writeList.add(result);            
        }
        flatFileItemWriter.write(writeList);
    }    

}
