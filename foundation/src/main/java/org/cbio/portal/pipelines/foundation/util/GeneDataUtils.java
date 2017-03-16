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

package org.cbio.portal.pipelines.foundation.util;

import java.io.*;
import java.util.*;
import org.apache.commons.logging.*;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.*;
import org.springframework.batch.item.file.mapping.*;
import org.springframework.batch.item.file.transform.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindException;

/**
 *
 * @author ochoaa
 */
@Repository
@JobScope
public class GeneDataUtils {
    
    // general constants
    private String DELIMITER = "\t";
    private String METADATA_PREFIX = "#";
    private String GENE_ID_COLUMN = "GeneID";
    private String SYMBOL_COLUMN = "Symbol";
    private String SYNONYMS_COLUMN = "Synonyms";
    private String TYPE_OF_GENE_COLUMN = "type_of_gene";
    
    // autowired maps for getting entrez from a hugo symbol and vice versa
    private final Map<String, String> hugoGeneSymbolMap = new HashMap<>();
    private final Map<String, String> entrezGeneIdMap = new HashMap<>();
    private final Map<String, String> geneAliasMap = new HashMap<>();  
    private final Set<String> ncRNABlackList = new HashSet();
    
    @Value("#{jobParameters[geneDataFilename]}")
    private String geneDataFilename;
    
    private boolean normalizedGeneSymbols;

    private static final Log LOG = LogFactory.getLog(GeneDataUtils.class);
    
    @Autowired
    private void loadHumanGeneInfo() throws Exception {
        File geneInfoFile = new File(geneDataFilename);
        if (geneInfoFile.exists()) {
            final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB);
            DefaultLineMapper<Properties> lineMapper = new DefaultLineMapper();
            lineMapper.setLineTokenizer(tokenizer);
            lineMapper.setFieldSetMapper(new FieldSetMapper() {
                @Override
                public Properties mapFieldSet(FieldSet fs) throws BindException {
                    return fs.getProperties();
                }
            });
            FlatFileItemReader<Properties> reader = new FlatFileItemReader();
            reader.setResource(new FileSystemResource(geneDataFilename));
            reader.setLineMapper(lineMapper);
            reader.setLinesToSkip(1);
            reader.setSkippedLinesCallback(new LineCallbackHandler() {
                @Override
                public void handleLine(String line) {
                    tokenizer.setNames(splitDataFields(line));
                }
            });
            reader.open(new ExecutionContext());

            try {
                Properties record = reader.read();
                while (record != null) {
                    String entrezId = record.getProperty(GENE_ID_COLUMN);
                    String hugoSymbol = record.getProperty(SYMBOL_COLUMN);
                    String typeOfGene = record.getProperty(TYPE_OF_GENE_COLUMN);
                    String synonyms = record.getProperty(SYNONYMS_COLUMN);

                    // add ncRNAs to blacklist
                    if (typeOfGene.equals("ncRNA")) {
                        ncRNABlackList.add(hugoSymbol);
                    }

                    // add values to appropriate data map
                    hugoGeneSymbolMap.put(hugoSymbol, entrezId);
                    entrezGeneIdMap.put(entrezId, hugoSymbol);
                    if (!synonyms.equals("-")) {
                        for (String alias : synonyms.split("\\|")) {
                            geneAliasMap.put(alias, hugoSymbol);
                        }
                    }
                    record = reader.read();
                }
                reader.close();
                this.normalizedGeneSymbols = true;
            }
            catch (Exception ex) {
                LOG.error("Error loading data from: " + geneDataFilename + " - gene symbol normalization will be skipped!");
                ex.printStackTrace();
            }
        }
        else {
            LOG.warn("Skipping gene symbol normalization! " + 
                    "\n\t--> To download the data and enable this feature, please refer to the fmi-convert Wiki page at: https://github.com/cBioPortal/fmi-converter/wiki/Downloading-Homo_sapiens.gene_info-data\n");
            this.normalizedGeneSymbols = false;
        }
    }
    
    /**
     * Light string processing/cleanup for tab delimited files.
     * 
     * @param line
     * @return String[]
     */
    private String[] splitDataFields(String line) {
        line = line.replaceAll("^" + METADATA_PREFIX + "+", "");
        String[] fields = line.split(DELIMITER, -1);

        return fields;                   
    }
    
    /**
     * Returns the header of a given datafile.
     * 
     * @param dataFile
     * @return String[]
     * @throws IOException 
     */
    private String[] getFileHeader(File dataFile) throws IOException {
        String[] columnNames;
        
        try (FileReader reader = new FileReader(dataFile)) {
            BufferedReader buff = new BufferedReader(reader);            
            String line = buff.readLine();
            
            // keep reading until line does not start with meta data prefix
            while (line.startsWith(METADATA_PREFIX)) {
                line = buff.readLine();
            }
            // extract the maf file header
            columnNames = splitDataFields(line);
            reader.close();
        }
        
        return columnNames;
    }
    
    /**
     * Determines whether given symbol is a ncRNA.
     * @param hugoSymbol
     * @return 
     */
    public boolean isNcRNA(String hugoSymbol) {
        if (!this.normalizedGeneSymbols) return false;
        return (ncRNABlackList.contains(hugoSymbol) || FoundationUtils.NULL_EMPTY_VALUES.contains(hugoSymbol));
    }
    
    /**
     * Resolves list of gene symbols. 
     * @param geneList
     * @return 
     */
    public List<String> resolveGeneSymbolList(String[] geneList) {
        List<String> resolvedGeneList = new ArrayList();        
        if (geneList.length == 1) {
            // if only one gene in list then resolve single gene symbol
            resolvedGeneList.add(resolveGeneSymbol(geneList[0]));
        }
        else {
            // if more than one gene in list then it is assumed that the first
            // gene in list contains the gene name prefix as in the case of 
            // CDKN2A/B, which has a gene name prefix of 'CDKN2'
            String genePrefix = geneList[0].substring(0, geneList[0].length()-geneList[1].length());
            for (int i=0;i<geneList.length;i ++) {
                String resolvedGene;
                if (i==0) {
                    resolvedGene = resolveGeneSymbol(geneList[i]);
                }
                else {
                    resolvedGene = resolveGeneSymbol(genePrefix + geneList[i]);                    
                }
                resolvedGeneList.add(resolvedGene);
            }
        }
        return resolvedGeneList;
    }
    
    /**
     * Resolves the given gene symbol.
     * Returns the input gene symbol if could not be resolved.
     * @param hugoSymbol
     * @return 
     */
    public String resolveGeneSymbol(String hugoSymbol) {
        // return if normalized gene data not loaded
        if (!this.normalizedGeneSymbols) return hugoSymbol;
        if (FoundationUtils.NULL_EMPTY_VALUES.contains(hugoSymbol)) {
            return hugoSymbol;
        }
        else if (hugoGeneSymbolMap.containsKey(hugoSymbol)) {
            return hugoSymbol;
        }
        else if (geneAliasMap.containsKey(hugoSymbol)) {
            LOG.info("Gene " + hugoSymbol+ " given as alias - normalizing symbol to: " + geneAliasMap.get(hugoSymbol));
            return geneAliasMap.get(hugoSymbol);                
        }
        else if (hugoSymbol.startsWith("LOC")) {
            String entrezId = hugoSymbol.replace("LOC", "");
            if (entrezGeneIdMap.containsKey(entrezId)) {
                LOG.info("Gene symbol " + hugoSymbol + " contains entrez ID - normalizing symbol to: " + entrezGeneIdMap.get(entrezId));
                geneAliasMap.put(hugoSymbol, entrezGeneIdMap.get(entrezId));
                return entrezGeneIdMap.get(entrezId);
            }
        }
        
        return hugoSymbol;
    }
    
    /**
     * Returns the entrez gene id for a given hugo symbol.
     * Returns '0' if hugo gene symbol map does not contain given hugo symbol.
     * @param hugoSymbol
     * @return 
     */
    public String resolveEntrezId(String hugoSymbol) {
        if (!this.normalizedGeneSymbols) return "0";
        return hugoGeneSymbolMap.getOrDefault(hugoSymbol, "0");
    }
    
}
