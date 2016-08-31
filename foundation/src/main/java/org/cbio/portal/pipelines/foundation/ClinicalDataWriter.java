package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.staging.ClinicalData;

import java.io.*;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.*;

import org.springframework.batch.item.*;
import org.springframework.batch.item.file.*;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;

/**
 *
 * @author Prithi Chakrapani, ochoaa
 */
public class ClinicalDataWriter  implements ItemStreamWriter<CompositeResultBean> {
    
    @Value("#{jobParameters[outputDirectory]}")
    private String outputDirectory;    

    private final List<String> writeList = new ArrayList<>();
    private final FlatFileItemWriter<String> flatFileItemWriter = new FlatFileItemWriter<>();
        
    private static final Log LOG = LogFactory.getLog(ClinicalDataWriter.class);
    
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        String stagingFile = outputDirectory +  "data_clinical.txt" ;
        
        PassThroughLineAggregator aggr = new PassThroughLineAggregator();
        flatFileItemWriter.setLineAggregator(aggr);
        flatFileItemWriter.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write(getHeader());
            }
        });
        flatFileItemWriter.setResource(new FileSystemResource(stagingFile));
        flatFileItemWriter.open(executionContext);
    }
    
    public String getHeader(){
        List<String> header = new ArrayList();
        
        Map<String, String> map = new ClinicalData().getStagingMap();
        for (String key : map.keySet() ) {
            header.add(key);
        }
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
    public void write(List<? extends CompositeResultBean> items) throws Exception
    {
        writeList.clear();
        List<String> writeList = new ArrayList<>();
        for (CompositeResultBean result : items) {
            writeList.add(result.getClinicalDataResult());
        }
        flatFileItemWriter.write(writeList);
    }
    
}
