package org.selion_framework.lib.util;

import org.apache.commons.io.FileUtils;
import org.selion_framework.lib.config.SelionConfig;
import org.selion_framework.lib.exception.SnLoggerException;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

final class SnInitializer {
    private static final Logger LOG = SnLogHandler.logger(SnInitializer.class);

    static void init() {
        final File rootProjectDir = SnLogHandler.userDirectory();
        final File logsDir = new File(rootProjectDir, "/logs");
        final File currentLogDir = new File(logsDir.getAbsolutePath() + "/log-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm_ss_SSS")));
        final long keepLogDuration = 60 * 1000 * SelionConfig.config().keepLogInMinutes;

        if (logsDir.exists()) {
            File[] directories = logsDir.listFiles((dir, name) -> name.startsWith("log-"));

            if (directories != null) {
                for (File directory : directories) {
                    if (directory.lastModified() + keepLogDuration < System.currentTimeMillis()) {
                        try {
                            FileUtils.forceDelete(directory);
                        } catch (IOException ex) {
                            LOG.error(ex.getMessage(), ex);
                        }
                    }
                }
            }
        } else if (!logsDir.mkdirs()) {
            throw new SnLoggerException("Failed to create log directory " + logsDir.getAbsolutePath());
        }
        if (!currentLogDir.mkdirs()) {
            throw new SnLoggerException("Failed to create log directory " + currentLogDir.getAbsolutePath());
        }
        SnLogHandler.setLogDirectory(currentLogDir);
        LOG.info("Created log directory: {}", currentLogDir.getAbsolutePath());
        SnLogHandler.configureLogger();
        LOG.info("Timezone: \"{}\"", ZoneId.systemDefault().getId());
    }
}
