package com.beluga.framework.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import static org.apache.logging.log4j.core.config.ConfigurationFactory.getInstance;


public class LoggingHelper {

    public static void main(String[] args) throws IOException, URISyntaxException {

        String logFile = "";
        try {
            File myObj = new File("/home/teo/Desktop/workspace/beluga/src/main/java/com/beluga/config/beluga-log.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                logFile = myReader.nextLine();
                System.out.println(logFile);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        File yourFile = new File(logFile + "test.log");
        yourFile.createNewFile();



        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        ConfigurationFactory factory = getInstance();

        LoggingHelper loggingHelper = new LoggingHelper();
        ConfigurationSource configurationSource = new ConfigurationSource(new FileInputStream(loggingHelper.getFileFromResource("log4j2.xml")));
        Configuration configuration = factory.getConfiguration(ctx, configurationSource);

        ConsoleAppender appender = ConsoleAppender.createDefaultAppenderForLayout(PatternLayout.createDefaultLayout());
        configuration.addAppender(appender);

        DefaultRolloverStrategy defaultRolloverStrategy = DefaultRolloverStrategy.newBuilder().withMax("1").withConfig(configuration).build();
        RollingFileAppender rollingFileAppender = RollingFileAppender.createAppender(logFile + "test.log", logFile + "test-%i.log",
                "true", "File", "true", "128", "true",
                SizeBasedTriggeringPolicy.createPolicy("1"),
                defaultRolloverStrategy, PatternLayout.createDefaultLayout(), null, "false", "false", null, configuration);
        configuration.addAppender(rollingFileAppender);

        LoggerConfig loggerConfig = new LoggerConfig("com", Level.FATAL, false);
        loggerConfig.addAppender(appender, null, null);
        configuration.addLogger("com", loggerConfig);

        LoggerContext context = new LoggerContext("test");
        context.start(configuration);
        Logger logger = context.getLogger("com");
    }

    


    private File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());
            return new File(resource.toURI());
        }
    }
}