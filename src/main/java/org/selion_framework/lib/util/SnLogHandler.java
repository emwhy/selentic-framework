package org.selion_framework.lib.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import org.apache.commons.io.FileUtils;
import org.selion_framework.lib.config.SelionConfig;
import org.selion_framework.lib.exception.SnLoggerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public final class SnLogHandler {
    private static File logDirectory;

    // By putting this in static, ensure that setting up log starts before anything else.
    static {
        SnInitializer.init();
    }

    public static Logger logger(Class<?> loggedClass) {
        return LoggerFactory.getLogger(loggedClass);
    }

    /**
     * Configure the logger which sets appender, log level, etc.
     */
    static synchronized void configureLogger() {
        final ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        final ch.qos.logback.classic.Logger apacheHttpLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.apache.hc");
        final ch.qos.logback.classic.Logger selionLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.selion_framework");
        final SelionConfig selionConfig = SelionConfig.config();

        // Set the log level at the root level.
        rootLogger.setLevel(Level.toLevel(selionConfig.rootLogLevel.toUpperCase()));
        // Set the log level for all Selion Framework packages.
        selionLogger.setLevel(Level.toLevel(selionConfig.selionLogLevel.toUpperCase()));
        // Turn down Apache HC logging because they are particularly annoying.
        apacheHttpLogger.setLevel(Level.WARN);

        rootLogger.addAppender(fileAppender());
    }


    /**
     * Start and return file appender for log.
     *
     * @return
     */
    private static FileAppender<ILoggingEvent> fileAppender() {
        final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        final FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
        final PatternLayoutEncoder encoder = new PatternLayoutEncoder();

        fileAppender.setContext(loggerContext);
        fileAppender.setName("logfile");

        // set the file name
        fileAppender.setFile(logDirectory() + "/selion.log");

        encoder.setContext(loggerContext);
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %level %C -- %msg%n");
        encoder.start();

        fileAppender.setEncoder(encoder);
        fileAppender.start();

        return fileAppender;
    }

    static void setLogDirectory(File logDirectory) {
        SnLogHandler.logDirectory = logDirectory;
    }

    public static File logDirectory() {
        return logDirectory;
    }

    public static File downloadDirectory() {
        return new File(logDirectory(), "download");
    }

    public static File userDirectory() {
        return new File(System.getProperty("user.dir"));
    }

    public static File artifactDirectory() {
        File artifactDirectory = new File(logDirectory().getAbsolutePath() + "/artifact");

        try {
            if (!artifactDirectory.exists()) {
                FileUtils.forceMkdir(artifactDirectory);
            }
            return artifactDirectory;
        } catch (IOException ex) {
            throw new SnLoggerException("Failed to create artifact directory: " + artifactDirectory.getAbsolutePath(), ex);
        }
    }

}
