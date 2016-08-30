package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.staging.FusionData;

import java.io.*;
import java.util.*;
import com.google.common.base.Strings;
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
public class FusionDataWriter implements ItemStreamWriter <CompositeResultBean> {
    
   @Value("#{jobParameters[outputDirectory]}")
    private String outputDirectory; 

    private static final Log LOG = LogFactory.getLog(FusionDataWriter.class);   

    private final List<String> writeList = new ArrayList<>();
    private final FlatFileItemWriter<String> flatFileItemWriter = new FlatFileItemWriter<>();
        
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        String stagingFile = outputDirectory +  "data_fusions.txt" ;
                
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
        Map<String, String> map = new FusionData().getStagingMap();
        for (String key : map.keySet()) {
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
        List<String> writeList = new ArrayList<String>();
        for (CompositeResultBean resultList : items) {
            for (String result : resultList.getFusionDataResult().split("\n")) {
                if (!Strings.isNullOrEmpty(result)) {
                    writeList.add(result);
                }
            }
            
        }
        flatFileItemWriter.write(writeList);
    }

}
