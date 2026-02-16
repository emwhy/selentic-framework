package org.selion_framework.lib.util;

import org.apache.commons.io.FileUtils;
import org.selion_framework.lib.config.SelionConfig;
import org.selion_framework.lib.exception.SnInitializationException;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

final class SnInitializer {
    private static final Logger LOG = SnLogHandler.logger(SnInitializer.class);

    static void init() {
        final SelionConfig config = SelionConfig.config();
        final File logsDir = config.logRootDir();
        final File currentLogDir = new File(logsDir.getAbsolutePath() + "/log-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm_ss_SSS")));
        final long keepLogDuration = 60 * 1000 * config.keepLogDurationMinutes();

        if (logsDir.exists()) {
            File[] directories = logsDir.listFiles((dir, name) -> name.startsWith("log-"));

            if (directories != null) {
                for (File directory : directories) {
                    // Clean up old log directories.
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
            throw new SnInitializationException("Failed to create log directory " + logsDir.getAbsolutePath());
        }

        if (!currentLogDir.mkdirs()) {
            throw new SnInitializationException("Failed to create log directory " + currentLogDir.getAbsolutePath());
        }
        SnLogHandler.setLogDirectory(currentLogDir);

        LOG.info("Created log directory: {}", currentLogDir.getAbsolutePath());
        SnLogHandler.configureLogger();

        if (!SnLogHandler.downloadDirectory().mkdirs()) {
            throw new SnInitializationException("Failed to create download directory " + SnLogHandler.downloadDirectory().getAbsolutePath());
        }
        if (!SnLogHandler.screenshotDirectory().mkdirs()) {
            throw new SnInitializationException("Failed to create screenshot directory " + SnLogHandler.screenshotDirectory().getAbsolutePath());
        }

        LOG.info("Timezone: \"{}\"", ZoneId.systemDefault().getId());
    }
}
