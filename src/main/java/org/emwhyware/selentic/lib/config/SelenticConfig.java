package org.emwhyware.selentic.lib.config;

import ch.qos.logback.classic.Level;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.selentic.lib.ScBrowser;
import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.slf4j.Logger;

import java.io.File;

/**
 * {@code SelenticConfig} is a singleton configuration class that manages all global configuration settings.
 *
 * <p>
 * This class provides centralized access to framework configuration including browser selection, timeout settings,
 * and logging configuration. Configuration values are loaded from the {@code selentic.conf} file located on the
 * classpath, with sensible defaults provided for any missing settings.
 * 
 *
 * <h2>Configuration File</h2>
 * <p>
 * The framework loads configuration from a {@code selentic.conf} file (HOCON format). If the file is not found or
 * contains errors, default values are used.
 * 
 *
 * <p>
 * <strong>Configuration File Location:</strong> The file should be placed in any location on the classpath.
 * 
 *
 * <h2>Configuration File Format (selentic.conf)</h2>
 * <pre>{@code
 * // Default browser
 * browser = "chrome"  // Options: chrome, firefox, safari, edge
 *
 * // Set headless execution
 * headless = false
 *
 * // Wait timeout in milliseconds
 * wait-timeout-millisec = 5000
 *
 * // Logging configuration
 * log {
 *     root-dir = ""              // Default is $user.dir/log. Directory where logs are stored.
 *     root-log-level = "INFO"         // Log level for root logger (TRACE, DEBUG, INFO, WARN, ERROR)
 *     selentic-log-level = "DEBUG"      // Log level for Selentic Framework logger (TRACE, DEBUG, INFO, WARN, ERROR)
 *     keep-duration-min = 0           // How long to keep logs in minutes (0 = will not keep)
 * }
 * }</pre>
 *
 * <h2>Default Configuration Values</h2>
 * <ul>
 *   <li><strong>browser:</strong> Chrome</li>
 *   <li><strong>headless:</strong> false</li>
 *   <li><strong>wait-timeout-millisec:</strong> 5000 (5 seconds)</li>
 *   <li><strong>log.root-dir:</strong> {@code {user.dir}/log}</li>
 *   <li><strong>log.root-log-level:</strong> INFO</li>
 *   <li><strong>log.selentic-log-level:</strong> DEBUG</li>
 *   <li><strong>log.keep-duration-min:</strong> 0 (keep forever)</li>
 * </ul>
 *
 * <h2>Access configuration values</h2>
 * <pre>{@code
 * ScBrowser browser = config.browser();
 * long timeout = config.waitTimeoutMilliseconds();
 * File logDir = config.logRootDir();
 * Level rootLogLevel = config.rootLogLevel();
 * Level selenticLogLevel = config.selenticLogLevel();
 * long logRetention = config.keepLogDurationMinutes();
 * }</pre>
 *
 * @see ScBrowser
 * @see ch.qos.logback.classic.Level
 */
public class SelenticConfig {
    private static final Logger LOG = ScLogHandler.logger(SelenticConfig.class);
    private static final SelenticConfig GLOBAL_CONFIG = new SelenticConfig();

    private ScBrowser browser = ScBrowser.Chrome;
    private boolean headless = false;
    private long waitTimeoutMilliseconds = 5000;
    private Level rootLogLevel = Level.INFO;
    private Level selenticLogLevel = Level.DEBUG;
    private long keepLogDurationMinutes = 0;
    private File logRootDir = new File(System.getProperty("user.dir") + "/log");

    /**
     * Returns the singleton instance of {@code SelenticConfig}.
     *
     * <p>
     * This is the primary entry point for accessing framework configuration. The configuration is loaded
     * once when the class is first initialized and remains constant throughout the test execution.
     * 
     *
     * @return the global {@code SelenticConfig} singleton instance
     */
    public static SelenticConfig config() {
        return GLOBAL_CONFIG;
    }

    /**
     * Private constructor for the {@code SelenticConfig} singleton.
     *
     * <p>
     * Instances are created only once, and the constructor:
     * <ul>
     *   <li>Attempts to load the {@code selentic.conf} file from the classpath</li>
     *   <li>Falls back to default values if the file is not found or contains errors</li>
     *   <li>Logs configuration details for debugging purposes</li>
     * </ul>
     * 
     *
     * <p>
     * This constructor should not be called directly. Use {@link #config()} to access the singleton instance.
     * 
     */
    @SuppressWarnings("nullness")
    private SelenticConfig() {
        try {
            final Config config = ConfigFactory.load("selentic.conf");

            loadFromFile(config);
        } catch (Throwable th) {
            LOG.warn("Error while attempting to load 'selentic.conf' file. There are errors while parsing it.", th);
            LOG.warn("Using default configuration values for this run:");
            LOG.warn("""
                    
                    
                    browser = '{}'
                    headless = {}
                    wait-timeout-millisec = {}
                    log {
                        root-dir = '{}'
                        root-log-level = '{}'
                        selentic-log-level = '{}'
                        keep-duration-min = {}
                    }

                    """, this.browser.toString().toLowerCase(), this.headless, this.waitTimeoutMilliseconds, parseDir(this.logRootDir.getAbsolutePath()), this.rootLogLevel.toString().toUpperCase(), this.selenticLogLevel.toString().toUpperCase(), this.keepLogDurationMinutes);
        }
    }

    /**
     * Loads configuration values from 'selentic.conf' file.
     *
     * @param config the {@link Config} object containing configuration properties
     */
    private void loadFromFile(@Nullable Config config) {
        int defaultConfigCount = 0;

        if (config == null) {
            return;
        }

        try {
            this.browser = ScBrowser.toEnum(config.getString("browser"));

            LOG.info("browser = '{}'", this.browser.toString().toLowerCase());
        } catch (ConfigException ex) {
            LOG.info("browser = '{}' (default)", this.browser.toString().toLowerCase());
            defaultConfigCount++;
        }

        try {
            this.headless = config.getBoolean("headless");

            LOG.info("headless = '{}'", this.headless);
        } catch (ConfigException ex) {
            LOG.info("headless = '{}' (default)", this.headless);
            defaultConfigCount++;
        }

        try {
            final long timeoutMilliseconds = config.getLong("wait-timeout-millisec");

            this.waitTimeoutMilliseconds = timeoutMilliseconds < 0 ? 0 : timeoutMilliseconds;
            LOG.info("wait-timeout-millisec = {}", this.waitTimeoutMilliseconds);
        } catch (ConfigException ex) {
            LOG.info("wait-timeout-millisec = {} (default)", this.waitTimeoutMilliseconds);
            defaultConfigCount++;
        }

        try {
            final File logRootDir = new File(config.getString("log.root-dir").trim());

            if (!logRootDir.exists()) {
                LOG.warn("'log.root-dir' was invalid in selentic.conf file. Using the default value: {}", parseDir(this.logRootDir.getAbsolutePath()));
            } else {
                this.logRootDir = logRootDir;
            }
            LOG.info("log.root-dir = '{}'", parseDir(this.logRootDir.getAbsolutePath()));
        } catch (ConfigException ex) {
            LOG.info("log.root-dir = '{}' (default)", parseDir(this.logRootDir.getAbsolutePath()));
            defaultConfigCount++;
        }

        try {
            this.rootLogLevel = Level.toLevel(config.getString("log.root-log-level").toUpperCase(), Level.INFO);
            LOG.info("log.root-log-level = {}", this.rootLogLevel.toString().toUpperCase());
        } catch (ConfigException ex) {
            LOG.info("log.root-log-level = {} (default)", this.rootLogLevel.toString().toUpperCase());
            defaultConfigCount++;
        }

        try {
            this.selenticLogLevel = Level.toLevel(config.getString("log.selentic-log-level").toUpperCase(), Level.DEBUG);
            LOG.info("log.selentic-log-level = {}", this.selenticLogLevel.toString().toUpperCase());
        } catch (ConfigException ex) {
            LOG.info("log.selentic-log-level = {} (default)", this.selenticLogLevel.toString().toUpperCase());
            defaultConfigCount++;
        }

        try {
            final long durationMinutes = config.getLong("log.keep-duration-min");

            this.keepLogDurationMinutes = durationMinutes < 0 ? 0 : durationMinutes;
            LOG.info("log.keep-duration-min = {}", this.keepLogDurationMinutes);
        } catch (ConfigException ex) {
            LOG.info("log.keep-duration-min = {} (default)", this.keepLogDurationMinutes);
            defaultConfigCount++;
        }

        if (defaultConfigCount >= 7) {
            LOG.info("You can control the configuration values by adding 'selentic.conf' file to one of classpath locations.");
            LOG.info("""
                    
                    
                    // 'selentic.conf' file content. You can copy and paste.
                    browser = '{}'
                    headless = {}
                    wait-timeout-millisec = {}
                    log {
                        root-dir = '{}'
                        root-log-level = '{}'
                        selentic-log-level = '{}'
                        keep-duration-min = {}
                    }

                    """, this.browser.toString().toLowerCase(), this.headless, this.waitTimeoutMilliseconds, parseDir(this.logRootDir.getAbsolutePath()), this.rootLogLevel.toString().toUpperCase(), this.selenticLogLevel.toString().toUpperCase(), this.keepLogDurationMinutes);
        }
    }

    /**
     * Returns if the test is set to run in headless mode from configuration file.
     * @return true if the configuration file sets headless value to true
     */
    public boolean isHeadless() {
        return this.headless;
    }

    /**
     * Returns the wait timeout duration in milliseconds.
     *
     * <p>
     * This timeout is used by the framework when waiting for elements to exist, become visible, or other
     * time-based operations. The default value is 5000 milliseconds (5 seconds).
     * 
     *
     * <p>
     * <strong>Configuration Key:</strong> {@code wait-timeout-millisec}
     * 
     *
     * @return the wait timeout in milliseconds
     */
    public long waitTimeoutMilliseconds() {
        return waitTimeoutMilliseconds;
    }

    /**
     * Returns the log level for the root logger.
     *
     * <p>
     * This setting controls the logging level for all loggers except the Selentic Framework logger,
     * which uses its own separate log level setting. The default value is {@link Level#INFO}.
     * 
     *
     * <p>
     * <strong>Configuration Key:</strong> {@code log.root-log-level}
     * 
     *
     * <p>
     * <strong>Valid Values:</strong> TRACE, DEBUG, INFO, WARN, ERROR, OFF
     * 
     *
     * @return the {@link Level} for the root logger
     * @see #selenticLogLevel()
     */
    public Level rootLogLevel() {
        return rootLogLevel;
    }

    /**
     * Returns the log level for the Selentic Framework logger.
     *
     * <p>
     * This setting controls the logging level specifically for the Selentic Framework's internal logging,
     * allowing you to see framework-specific debug information independently of your application logs.
     * The default value is {@link Level#DEBUG}.
     * 
     *
     * <p>
     * <strong>Configuration Key:</strong> {@code log.selentic-log-level}
     * 
     *
     * <p>
     * <strong>Valid Values:</strong> TRACE, DEBUG, INFO, WARN, ERROR, OFF
     * 
     *
     * @return the {@link Level} for the Selentic Framework logger
     * @see #rootLogLevel()
     */
    public Level selenticLogLevel() {
        return selenticLogLevel;
    }

    /**
     * Returns the log retention duration in minutes.
     *
     * <p>
     * This setting determines how long log files are retained before being automatically deleted.
     * A value of 0 indicates that logs are not kept.
     * 
     *
     * <p>
     * <strong>Configuration Key:</strong> {@code log.keep-duration-min}
     * 
     *
     * <p>
     * <strong>Examples:</strong>
     * <ul>
     *   <li>0 = Keep none (default)</li>
     *   <li>60 = Keep logs for 1 hour</li>
     *   <li>1440 = Keep logs for 1 day</li>
     *   <li>10080 = Keep logs for 1 week</li>
     * </ul>
     *
     *
     * @return the log retention duration in minutes
     */
    public long keepLogDurationMinutes() {
        return keepLogDurationMinutes;
    }

    /**
     * Returns the configured browser type for test execution.
     *
     * <p>
     * This setting determines which web browser the framework will use when initializing WebDriver instances.
     * The default value is {@link ScBrowser#Chrome}.
     * 
     *
     * <p>
     * <strong>Configuration Key:</strong> {@code browser}
     * 
     *
     * <p>
     * <strong>Valid Values:</strong> chrome, firefox, safari, edge (case-insensitive)
     * 
     *
     * @return the {@link ScBrowser} to use for test execution
     * @see ScBrowser
     */
    public ScBrowser browser() {
        return this.browser;
    }

    /**
     * Returns the root directory where log files are stored.
     *
     * <p>
     * This setting specifies the base directory where all framework log files will be written.
     * The default value is {@code {user.dir}/log} (a "log" directory in the current working directory).
     * 
     *
     * <p>
     * <strong>Configuration Key:</strong> {@code log.root-dir}
     * 
     *
     * @return the {@link File} representing the log root directory
     */
    public File logRootDir() {
        return logRootDir;
    }

    /**
     * Parses and normalizes a directory path string.
     *
     * <p>
     * This utility method converts absolute directory paths to a normalized format, removing platform-specific
     * path separators and keeping only the relevant directory portion. This is used internally for logging
     * configuration values in a consistent format.
     * 
     *
     * @param originalDir the original directory path string to parse
     * @return the normalized directory path with platform-specific separators converted to forward slashes
     */
    private static String parseDir(@NonNull String originalDir) {
        if (originalDir.contains(":")) {
            return originalDir.split(":")[1].replace("\\", "/");
        } else {
            return originalDir.replace("\\", "/");
        }
    }
}