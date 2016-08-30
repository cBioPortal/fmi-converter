package org.cbio.portal.pipelines;

import org.cbio.portal.pipelines.foundation.BatchConfiguration;

import java.io.*;
import org.apache.commons.cli.*;
import org.apache.commons.logging.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;

/**
 * Pipeline for transforming Foundation XML data to staging files.
 * 
 * @author Prithi Chakrapani, ochoaa
 */
@SpringBootApplication
public class FoundationPipeline {
    
    private static final Log LOG = LogFactory.getLog(FoundationPipeline.class);
    
    private static Options getOptions(String[] args) {
        Options gnuOptions = new Options();
        gnuOptions.addOption("h", "help", false, "shows this help document and quits.")
            .addOption("s", "source", true, "Foundation .XML source directory" )
            .addOption ("o", "output", true, "Output directory for writing staging files")
            .addOption ("c", "cancer_study_id", true, "Cancer study identifier for meta data");
         
        return gnuOptions;
    }   
    
    private static void help(Options gnuOptions, int exitStatus) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("FoundationPipeline", gnuOptions);        
        System.exit(exitStatus);
    }  
            
    private static void launchJob(String[] args, String sourceDirectory, String outputDirectory, String cancerStudyId) throws Exception {
        // set up application context and job launcher
        SpringApplication app = new SpringApplication(FoundationPipeline.class);
        ConfigurableApplicationContext ctx= app.run(args);
        JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);

        // configure job parameters and launch job
        Job foundationJob = ctx.getBean(BatchConfiguration.FOUNDATION_JOB, Job.class);
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("sourceDirectory", sourceDirectory)
                .addString("outputDirectory", outputDirectory)
                .addString ("cancerStudyId", cancerStudyId)
                .toJobParameters();  
        JobExecution jobExecution = jobLauncher.run(foundationJob, jobParameters);
        
        // close job after completion 
        LOG.info("Closing FoundationPipeline with status: " + jobExecution.getStatus());
        ctx.close();
    }      
    
    public static void main(String[] args) throws Exception {        
        Options gnuOptions = FoundationPipeline.getOptions(args);
        CommandLineParser parser = new GnuParser();
        CommandLine commandLine = parser.parse(gnuOptions, args);
        if (commandLine.hasOption("h") ||
            !commandLine.hasOption("source") ||
            !commandLine.hasOption("output") ||
            !commandLine.hasOption("cancer_study_id"))  {
            for (String a : args) {
                System.out.println(a);
            }
            help(gnuOptions, 0);        
        }
        
        // light pre-processing for source directory and output directory paths
        String sourceDirectory = commandLine.getOptionValue("source");
        String outputDirectory = commandLine.getOptionValue("output");
        if (!sourceDirectory.endsWith(File.separator)) sourceDirectory += File.separator;
        if (!outputDirectory.endsWith(File.separator)) outputDirectory += File.separator;

        launchJob(args, sourceDirectory, outputDirectory, commandLine.getOptionValue("cancer_study_id"));
    }
    
}
    