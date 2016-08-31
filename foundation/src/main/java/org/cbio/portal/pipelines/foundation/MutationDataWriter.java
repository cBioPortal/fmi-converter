package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.CaseType;
import org.cbio.portal.pipelines.foundation.model.staging.MutationData;

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
public class MutationDataWriter implements ItemStreamWriter <CompositeResultBean>{    
    @Value("#{jobParameters[outputDirectory]}")
    private String outputDirectory;
    
    private static final Log LOG = LogFactory.getLog(MutationDataWriter.class);

    private final List<String> writeList = new ArrayList<>();
    private final FlatFileItemWriter<String> flatFileItemWriter = new FlatFileItemWriter<>();
        
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {                
        // retrieve list of foundation cases from execution context
        final List<CaseType> fmiCaseList = (List<CaseType>) executionContext.get("fmiCaseList");
                
        String stagingFile = outputDirectory +  "data_mutations_extended.txt" ;
        PassThroughLineAggregator aggr = new PassThroughLineAggregator();
        flatFileItemWriter.setLineAggregator(aggr);
        flatFileItemWriter.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write(getHeader(fmiCaseList));
            }
        });
        flatFileItemWriter.setResource(new FileSystemResource(stagingFile));
        flatFileItemWriter.open(executionContext);
    }
    public String getHeader(List<CaseType> fmiCaseList){
        List<String> header = new ArrayList();        
        List <String> columns = new ArrayList();
        Map<String, String> map = new MutationData().getStagingMap();
        for (String key : map.keySet() ) {
            columns.add(key);
        }
        
        // format the MAF header
        header.add(getSequencedSamples(fmiCaseList));
        header.add(StringUtils.join(columns, "\t"));
        return StringUtils.join(header, "\n"); 
    }
    
    public String getSequencedSamples(List<CaseType> fmiCaseList) {
        List<String> sequencedSamples = new ArrayList();
        sequencedSamples.add("#sequenced_samples:");
        for (CaseType ct : fmiCaseList) {
            sequencedSamples.add(ct.getCase());
        }
        
        return StringUtils.join(sequencedSamples, " ");
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
        for (CompositeResultBean resultList : items) {
            for (String result : resultList.getMutationDataResult().split("\n")) {
                if (!Strings.isNullOrEmpty(result)) {
                    writeList.add(result);
                }
            }            
        }
        
        flatFileItemWriter.write(writeList);
    }
    
}
