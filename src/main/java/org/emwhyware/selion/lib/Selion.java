package org.emwhyware.selion.lib;

import org.emwhyware.selion.lib.config.SelionConfig;
import org.emwhyware.selion.lib.util.SnLogHandler;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * {@code Selion} is the central gateway class for the Selion Framework, providing access to
 * WebDriver instances and browser automation functionality.
 *
 * <p>
 * <strong>Etymology:</strong> "Selion" is a medieval term for a strip of land between two furrows.
 * Why it works: It evokes a clear, defined path or "strip." In a modular web component architecture,
 * you are testing individual "strips" (components) that make up the whole field (the page).
 * 
 *
 * <h2>Core Responsibilities</h2>
 * <ul>
 *   <li><strong>WebDriver Management:</strong> Manages thread-safe access to WebDriver instances</li>
 *   <li><strong>Browser Initialization:</strong> Creates and configures WebDriver instances for different browsers</li>
 *   <li><strong>Browser Options Configuration:</strong> Provides fluent API for configuring browser-specific options</li>
 *   <li><strong>JavaScript Execution:</strong> Executes JavaScript code in the browser context</li>
 *   <li><strong>Screenshot Capture:</strong> Captures and saves screenshots during test execution</li>
 *   <li><strong>WebDriver Listeners:</strong> Supports custom WebDriver event listeners for enhanced logging and monitoring</li>
 * </ul>
 *
 * <h2>Supported Browsers</h2>
 * <ul>
 *   <li>Chrome (via ChromeDriver)</li>
 *   <li>Firefox (via GeckoDriver)</li>
 *   <li>Safari (via SafariDriver)</li>
 *   <li>Edge (via EdgeDriver)</li>
 * </ul>
 *
 * <h2>Thread Safety</h2>
 * <p>
 * All driver management methods are synchronized to ensure thread-safe access. Each thread maintains its own
 * WebDriver instance, allowing for parallel test execution without interference.
 * 
 *
 * @see SnBrowser
 * @see SnWebDriverOptions
 * @see WebDriver
 * @see WebDriverListener
 */
public final class Selion {
    private static final Logger LOG = SnLogHandler.logger(Selion.class);
    private static final HashMap<Long, WebDriver> DRIVERS = new HashMap<>();
    private static final SnWebDriverOptions WEBDRIVER_OPTIONS = new SnWebDriverOptions();
    private static Optional<WebDriverListener> webDriverListener = Optional.empty();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Selion() {
        // Utility class - no instances
    }

    /**
     * Returns the WebDriver instance for the current thread using the default browser from configuration.
     *
     * <p>
     * This method retrieves or creates a WebDriver instance for the calling thread. If no driver exists
     * for the current thread, a new one is created using the browser configured in {@link SelionConfig}.
     * The WebDriver instance is reused for subsequent calls from the same thread.
     * 
     *
     * <p>
     * <strong>Thread Safety:</strong> This method is synchronized to ensure thread-safe access. Each thread
     * maintains its own WebDriver instance, enabling parallel test execution.
     * 
     *
     * <p>
     * <strong>Browser Selection:</strong> The browser type is determined by the configuration setting
     * {@code browser} in the {@code selion.conf} file. The default is Chrome.
     * 
     *     *
     * @return the {@link WebDriver} instance for the current thread
     * @see #driver(SnBrowser)
     * @see SelionConfig#browser()
     */
    public static synchronized WebDriver driver() {
        return driver(SelionConfig.config().browser());
    }

    /**
     * Returns the WebDriver instance for the current thread using the specified browser.
     *
     * <p>
     * This method retrieves or creates a WebDriver instance for the calling thread with the specified
     * browser type. If a driver already exists for the current thread and browser combination, it is
     * reused. Otherwise, a new WebDriver instance is created and initialized with the appropriate
     * browser options.
     * 
     *
     * <p>
     * <strong>Thread Safety:</strong> This method is synchronized to ensure thread-safe access.
     * Multiple threads can safely call this method concurrently, each receiving their own driver instance.
     * 
     *
     * <p>
     * <strong>WebDriver Listener Support:</strong> If a custom {@link WebDriverListener} has been set via
     * {@link #setWebDriverListener(WebDriverListener)}, the WebDriver will be wrapped with an
     * {@link EventFiringDecorator} to capture WebDriver events.
     * 
     *
     * <p>
     * <strong>Browser Options:</strong> Each browser is initialized with default options. These can be
     * customized before calling this method using the {@code withXxxOptions()} methods:
     * <ul>
     *   <li>{@link #withChromeOptions(SnWebDriverOptions.ChromeOptionSetup)}</li>
     *   <li>{@link #withFirefoxOptions(SnWebDriverOptions.FirefoxOptionSetup)}</li>
     *   <li>{@link #withEdgeOptions(SnWebDriverOptions.EdgeOptionSetup)}</li>
     *   <li>{@link #withSafariOptions(SnWebDriverOptions.SafariOptionSetup)}</li>
     * </ul>
     *
     *
     * @param browser the {@link SnBrowser} type to use for creating the WebDriver
     * @return the {@link WebDriver} instance for the current thread with the specified browser
     * @see #driver()
     * @see SnBrowser
     */
    public static synchronized WebDriver driver(SnBrowser browser) {
        final long threadId = Thread.currentThread().threadId();

        if (!DRIVERS.containsKey(threadId)) {
            WebDriver driver = null;

            switch (browser) {
                case SnBrowser.Chrome -> driver = new ChromeDriver(WEBDRIVER_OPTIONS.chromeOptions());
                case SnBrowser.Firefox -> driver = new FirefoxDriver(WEBDRIVER_OPTIONS.firefoxOptions());
                case SnBrowser.Safari -> driver = new SafariDriver();
                case SnBrowser.Edge -> driver = new EdgeDriver(WEBDRIVER_OPTIONS.edgeOptions());
            }

            // Add listener class, if available.
            if (webDriverListener.isPresent()) {
                driver = new EventFiringDecorator<>(webDriverListener.get()).decorate(driver);
            }
            DRIVERS.put(threadId, driver);
        }

        return DRIVERS.get(threadId);
    }

    /**
     * Configures Chrome-specific WebDriver options.
     *
     * <p>
     * This method provides a fluent API for customizing Chrome browser options before the WebDriver
     * instance is created. The provided lambda receives the Chrome options object and preferences map,
     * allowing you to add arguments, set preferences, and configure other Chrome-specific settings.
     * 
     *
     * <p>
     * <strong>Important:</strong> This method must be called BEFORE calling {@link #driver()} or
     * {@link #driver(SnBrowser)} to ensure the options are applied to the new ChromeDriver instance.
     * 
     *
     * <p>
     * <strong>Common Chrome Options:</strong>
     * <pre>{@code
     * Selion.withChromeOptions((options, prefs) -> {
     *     options.addArguments("--headless");                           // Run in headless mode
     *     options.addArguments("--disable-gpu");                        // Disable GPU acceleration
     *     options.addArguments("--no-sandbox");                         // Bypass OS security model
     *     options.addArguments("--disable-dev-shm-usage");              // Overcome limited resource problems
     *     options.addArguments("--disable-blink-features=AutomationControlled");
     *     prefs.put("download.prompt_for_download", false);            // Auto-download files
     *     prefs.put("profile.default_content_settings.popups", 0);     // Block popups
     * });
     * }</pre>
     *
     *
     * @param optionSetup a {@link SnWebDriverOptions.ChromeOptionSetup} functional interface that receives
     *                   the Chrome options and preferences for configuration
     * @see SnWebDriverOptions.ChromeOptionSetup
     * @see #withFirefoxOptions(SnWebDriverOptions.FirefoxOptionSetup)
     * @see #withEdgeOptions(SnWebDriverOptions.EdgeOptionSetup)
     */
    public static synchronized void withChromeOptions(SnWebDriverOptions.ChromeOptionSetup optionSetup) {
        optionSetup.options(WEBDRIVER_OPTIONS.chromeOptions(), WEBDRIVER_OPTIONS.chromePrefs());

        WEBDRIVER_OPTIONS.chromeOptions().setExperimentalOption("pref", WEBDRIVER_OPTIONS.chromePrefs());
    }

    /**
     * Configures Firefox-specific WebDriver options.
     *
     * <p>
     * This method provides a fluent API for customizing Firefox browser options before the WebDriver
     * instance is created. The provided lambda receives the Firefox options object, allowing you to
     * add arguments, set preferences, and configure other Firefox-specific settings.
     * 
     *
     * <p>
     * <strong>Important:</strong> This method must be called BEFORE calling {@link #driver()} or
     * {@link #driver(SnBrowser)} to ensure the options are applied to the new FirefoxDriver instance.
     * 
     *
     * <p>
     * <strong>Common Firefox Options:</strong>
     * <pre>{@code
     * Selion.withFirefoxOptions(options -> {
     *     options.addArguments("--headless");                           // Run in headless mode
     *     options.addPreference("browser.download.folderList", 2);     // Use custom download folder
     *     options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
     * });
     * }</pre>
     *
     *
     * @param optionSetup a {@link SnWebDriverOptions.FirefoxOptionSetup} functional interface that receives
     *                   the Firefox options for configuration
     * @see SnWebDriverOptions.FirefoxOptionSetup
     * @see #withChromeOptions(SnWebDriverOptions.ChromeOptionSetup)
     * @see #withEdgeOptions(SnWebDriverOptions.EdgeOptionSetup)
     */
    public static synchronized void withFirefoxOptions(SnWebDriverOptions.FirefoxOptionSetup optionSetup) {
        optionSetup.options(WEBDRIVER_OPTIONS.firefoxOptions());
    }

    /**
     * Configures Edge-specific WebDriver options.
     *
     * <p>
     * This method provides a fluent API for customizing Edge browser options before the WebDriver
     * instance is created. The provided lambda receives the Edge options object and preferences map,
     * allowing you to add arguments, set preferences, and configure other Edge-specific settings.
     * 
     *
     * <p>
     * <strong>Important:</strong> This method must be called BEFORE calling {@link #driver()} or
     * {@link #driver(SnBrowser)} to ensure the options are applied to the new EdgeDriver instance.
     * 
     *
     * <p>
     * <strong>Common Edge Options:</strong>
     * <pre>{@code
     * Selion.withEdgeOptions((options, prefs) -> {
     *     options.addArguments("--headless");                           // Run in headless mode
     *     options.addArguments("--disable-gpu");                        // Disable GPU acceleration
     *     options.addArguments("--no-sandbox");                         // Bypass OS security model
     *     prefs.put("download.prompt_for_download", false);            // Auto-download files
     * });
     * }</pre>
     *
     *
     * @param optionSetup a {@link SnWebDriverOptions.EdgeOptionSetup} functional interface that receives
     *                   the Edge options and preferences for configuration
     * @see SnWebDriverOptions.EdgeOptionSetup
     * @see #withChromeOptions(SnWebDriverOptions.ChromeOptionSetup)
     * @see #withFirefoxOptions(SnWebDriverOptions.FirefoxOptionSetup)
     */
    public static synchronized void withEdgeOptions(SnWebDriverOptions.EdgeOptionSetup optionSetup) {
        optionSetup.options(WEBDRIVER_OPTIONS.edgeOptions(), WEBDRIVER_OPTIONS.edgePrefs());

        WEBDRIVER_OPTIONS.edgeOptions().setExperimentalOption("pref", WEBDRIVER_OPTIONS.edgeOptions());
    }

    /**
     * Configures Safari-specific WebDriver options.
     *
     * <p>
     * This method provides a fluent API for customizing Safari browser options before the WebDriver
     * instance is created. The provided lambda receives the Safari options object, allowing you to
     * configure Safari-specific settings.
     * 
     *
     * <p>
     * <strong>Important:</strong> This method must be called BEFORE calling {@link #driver()} or
     * {@link #driver(SnBrowser)} to ensure the options are applied to the new SafariDriver instance.
     * 
     *
     * <p>
     * <strong>Note:</strong> Safari driver support is more limited compared to Chrome and Firefox.
     * Some options available in other browsers may not be supported in Safari.
     * 
     *
     * @param optionSetup a {@link SnWebDriverOptions.SafariOptionSetup} functional interface that receives
     *                   the Safari options for configuration
     * @see SnWebDriverOptions.SafariOptionSetup
     * @see #withChromeOptions(SnWebDriverOptions.ChromeOptionSetup)
     * @see #withFirefoxOptions(SnWebDriverOptions.FirefoxOptionSetup)
     */
    public static synchronized void withSafariOptions(SnWebDriverOptions.SafariOptionSetup optionSetup) {
        optionSetup.options(WEBDRIVER_OPTIONS.safariOptions());
    }

    /**
     * Sets a custom WebDriver listener for event handling and monitoring.
     *
     * <p>
     * This method allows you to register a custom {@link WebDriverListener} that will receive callbacks
     * for WebDriver events such as navigation, element location, and JavaScript execution. This is useful
     * for logging, custom assertions, or performance monitoring.
     * 
     *
     * <p>
     * <strong>Important:</strong> This method must be called BEFORE calling {@link #driver()} to ensure
     * the listener is attached to the WebDriver instance.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * Selion.setWebDriverListener(new WebDriverListener() {
     *     @Override
     *     public void beforeNavigateTo(String url, WebDriver driver) {
     *         System.out.println("Navigating to: " + url);
     *     }
     *
     *     @Override
     *     public void afterFindElement(WebDriver driver, By locator, WebElement element) {
     *         System.out.println("Found element: " + locator);
     *     }
     * });
     * }</pre>
     *
     *
     * @param listener the {@link WebDriverListener} to set for event notifications
     * @see WebDriverListener
     * @see #driver()
     */
    public static void setWebDriverListener(WebDriverListener listener) {
        webDriverListener = Optional.of(listener);
    }

    /**
     * Navigates to the specified URL.
     *
     * <p>
     * This method is a convenience wrapper around {@link WebDriver#get(String)} that navigates the
     * current browser to the specified URL.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * Selion.open("https://example.com");
     * }</pre>
     *
     *
     * @param url the URL to navigate to
     * @see #open()
     * @see #driver()
     */
    public static void open(String url) {
        driver().get(url);
    }

    /**
     * Navigates to a blank page.
     *
     * <p>
     * This method navigates the current browser to about:blank, which is useful for clearing the browser
     * state or initializing a clean slate before test execution.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * Selion.open();  // Navigate to blank page
     * }</pre>
     *
     *
     * @see #open(String)
     * @see #driver()
     */
    public static void open() {
        driver().get("about:blank");
    }

    /**
     * Quits the WebDriver instance for the current thread and cleans up resources.
     *
     * <p>
     * This method terminates the WebDriver session for the calling thread, closes all associated browser
     * windows and tabs, and removes the driver from the thread-local storage. After calling this method,
     * a new WebDriver instance will be created the next time {@link #driver()} is called.
     * 
     *
     * <p>
     * <strong>Thread Safety:</strong> This method is synchronized to ensure thread-safe access to the
     * driver map.
     * 
     *
     * @see #driver()
     */
    public static synchronized void quit() {
        final long threadId = Thread.currentThread().threadId();

        if (DRIVERS.containsKey(threadId)) {
            DRIVERS.get(threadId).quit();
            DRIVERS.remove(threadId);
        }
    }

    /**
     * Executes JavaScript code in the browser context.
     *
     * <p>
     * This method executes the provided JavaScript string in the context of the current page loaded in the
     * WebDriver. It automatically converts {@link SnComponent} parameters to their underlying {@link WebElement}
     * objects, allowing for seamless integration with the framework's component model.
     * 
     *
     * <p>
     * <strong>Parameter Conversion:</strong> If any parameter is an instance of {@link SnComponent}, it is
     * automatically converted to its wrapped {@link WebElement}. All other parameters are passed as-is.
     * 
     *
     * <p>
     * <strong>Return Value:</strong> The return value depends on the JavaScript code executed. Common return
     * types include:
     * <ul>
     *   <li>Primitives: Boolean, Long, Double, String</li>
     *   <li>Collections: List, Map</li>
     *   <li>WebElements: org.openqa.selenium.WebElement</li>
     * </ul>
     *
     **
     * @param script the JavaScript code to execute
     * @param params variable number of parameters to pass to the script;
     *              {@link SnComponent} instances are converted to {@link WebElement}
     * @return the result of JavaScript execution, or null if no value is returned
     * @see #executeAsyncScript(String, Object...)
     * @see JavascriptExecutor
     */
    public static Object executeScript(String script, Object... params) {
        final JavascriptExecutor executor = (JavascriptExecutor) driver();
        final Object[] objects = Arrays.stream(params).map(o -> o instanceof SnComponent ? ((SnComponent) o).existingElement() : o).toArray();

        return executor.executeScript(script, objects);
    }

    /**
     * Executes asynchronous JavaScript code in the browser context.
     *
     * <p>
     * This method executes the provided JavaScript string asynchronously in the context of the current page.
     * The script is expected to handle its own callback mechanism, typically by calling the provided callback
     * function when the asynchronous operation completes.
     * 
     *
     * <p>
     * <strong>Parameter Conversion:</strong> If any parameter is an instance of {@link SnComponent}, it is
     * automatically converted to its underlying {@link WebElement}. All other parameters are passed as-is.
     * 
     *
     * <p>
     * <strong>Timeout:</strong> The script execution times out after the WebDriver's configured timeout period
     * if the callback is not invoked.
     * 
     *
     * @param script the asynchronous JavaScript code to execute
     * @param params variable number of parameters to pass to the script;
     *              {@link SnComponent} instances are converted to {@link WebElement}
     * @return the result of asynchronous JavaScript execution as a String, or null if no value is returned
     * @see #executeScript(String, Object...)
     * @see JavascriptExecutor
     */
    public static String executeAsyncScript(String script, Object... params) {
        final JavascriptExecutor executor = (JavascriptExecutor) driver();
        final List<Object> objects = Arrays.stream(params).map(o -> o instanceof SnComponent ? ((SnComponent) o).existingElement() : o).toList();

        return String.valueOf(executor.executeAsyncScript(script, objects));
    }

    /**
     * Takes a screenshot of the current page without a name suffix.
     *
     * <p>
     * This method captures the current state of the browser and saves it as a PNG file in the screenshot
     * directory. The filename includes a timestamp but no additional descriptive name.
     * 
     *
     * <p>
     * <strong>File Naming:</strong> Screenshots are named using the current system timestamp in milliseconds.
     * Example: {@code 1697234567890.png}
     * 
     *
     * <p>
     * <strong>Storage Location:</strong> Screenshots are stored in the directory returned by
     * {@link SnLogHandler#screenshotDirectory()}.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * Selion.screenshot();  // Saves as: 1697234567890.png
     * }</pre>
     *
     *
     * @see #screenshot(String)
     * @see SnLogHandler#screenshotDirectory()
     */
    public static void screenshot() {
        screenshot("");
    }

    /**
     * Takes a screenshot of the current page with an optional descriptive name.
     *
     * <p>
     * This method captures the current state of the browser and saves it as a PNG file in the screenshot
     * directory. The filename includes a timestamp and an optional descriptive suffix to help identify
     * what the screenshot depicts.
     * 
     *
     * <p>
     * <strong>File Naming:</strong> Screenshots are named using the format: {@code {timestamp}-{screenshotName}.png}
     * Example with name: {@code 1697234567890-login-page.png}
     * Example without name: {@code 1697234567890.png}
     * 
     *
     * <p>
     * <strong>Storage Location:</strong> Screenshots are stored in the directory returned by
     * {@link SnLogHandler#screenshotDirectory()}.
     * 
     *
     * <p>
     * <strong>Error Handling:</strong> If an {@link IOException} occurs during screenshot capture or file writing,
     * the error is logged at ERROR level but does not throw an exception, allowing tests to continue.
     * 
     *
     * <p>
     * <strong>Debugging:</strong> After successful capture, the screenshot file path is logged at DEBUG level
     * in a file:// URL format for easy access.
     * 
     *
     * <p>
     * <strong>Usage Examples:</strong>
     * <pre>{@code
     * // With descriptive name
     * Selion.screenshot("login-success");        // Saves as: 1697234567890-login-success.png
     * Selion.screenshot("checkout-page");        // Saves as: 1697234567890-checkout-page.png
     *
     * // Without name
     * Selion.screenshot();                       // Saves as: 1697234567890.png
     * }</pre>
     *
     *
     * @param screenshotName an optional descriptive name for the screenshot; if empty, only timestamp is used
     * @see #screenshot()
     * @see SnLogHandler#screenshotDirectory()
     * @see TakesScreenshot
     */
    public static void screenshot(String screenshotName) {
        if (!screenshotName.isEmpty()) {
            screenshotName = "-" + screenshotName;
        }

        try {
            final byte[] screenshotData = ((TakesScreenshot) driver()).getScreenshotAs(OutputType.BYTES);
            final Path screenshotPath = Path.of(SnLogHandler.screenshotDirectory().getAbsolutePath(), System.currentTimeMillis() + screenshotName + ".png");

            // Write the byte array to the file
            Files.write(screenshotPath, screenshotData);
            LOG.debug("Screenshot: file:///{}", screenshotPath.toFile().getAbsolutePath().replace("\\", "/"));
        } catch (IOException ex) {
            LOG.error("Error while taking screenshot." , ex);
        }
    }
}