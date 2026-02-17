package org.emwhyware.selentic.lib.util;

import org.apache.commons.io.FileUtils;
import org.emwhyware.selentic.lib.config.SelelenticConfig;
import org.emwhyware.selentic.lib.exception.ScInitializationException;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

final class ScInitializer {
    private static final Logger LOG = ScLogHandler.logger(ScInitializer.class);

    static void init() {
        final SelelenticConfig config = SelelenticConfig.config();
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
            throw new ScInitializationException("Failed to create log directory " + logsDir.getAbsolutePath());
        }

        if (!currentLogDir.mkdirs()) {
            throw new ScInitializationException("Failed to create log directory " + currentLogDir.getAbsolutePath());
        }
        ScLogHandler.setLogDirectory(currentLogDir);

        LOG.info("Created log directory: {}", currentLogDir.getAbsolutePath());
        ScLogHandler.configureLogger();

        if (!ScLogHandler.downloadDirectory().mkdirs()) {
            throw new ScInitializationException("Failed to create download directory " + ScLogHandler.downloadDirectory().getAbsolutePath());
        }
        if (!ScLogHandler.screenshotDirectory().mkdirs()) {
            throw new ScInitializationException("Failed to create screenshot directory " + ScLogHandler.screenshotDirectory().getAbsolutePath());
        }

        LOG.info("Timezone: \"{}\"", ZoneId.systemDefault().getId());
    }
}
