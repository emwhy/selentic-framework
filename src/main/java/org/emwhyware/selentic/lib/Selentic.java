package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.selentic.lib.config.SelelenticConfig;
import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.emwhyware.selentic.lib.util.ScNullCheck;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * {@code Selentic} is the central gateway class for the Selentic Framework, providing access to context that has
 * WebDriver instances and web driver options.
 * <p>
 * The context ({@link SelenticWebDriverContext}) exists separately for each thread if running in multi-thread run, making
 * it possible to run different configuration for each thread (like running different browsers in each thread).
 * <p>
 * Any changes to options must be done before calling the web driver for the first time in a thread. Changes after the
 * web driver is created will be ignored.
 * <p>
 * Many of typically recommended options for each browser type is already set up. It's possible to add more options.
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
 * @see ScBrowser
 * @see ScWebDriverOptions
 * @see WebDriver
 * @see WebDriverListener
 * @see SelenticWebDriverContext
 */
public final class Selentic {
    private static final Logger LOG = ScLogHandler.logger(Selentic.class);
    private static final ThreadLocal<@Nullable SelenticWebDriverContext> CONTEXT = ThreadLocal.withInitial(SelenticWebDriverContext::new);

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Selentic() {
        // Utility class - no instances
    }

    /**
     * Allows setting a browser and override config value during run time.
     * @param browser browser type
     */
    public synchronized static void setBrowser(ScBrowser browser) {
        context().setBrowser(browser);
    }

    /**
     * Enable headless mode for all browser types.
     */
    public synchronized static void enableHeadless() {
        withChromeOptions((options, prefs) -> {
            options.addArguments("--headless=new");
        });
        withEdgeOptions(((options, prefs) -> {
            options.addArguments("--headless=new");
        }));
        withFirefoxOptions(options -> {
            options.addArguments("--headless");
        });
        withSafariOptions(options -> {
            options.setCapability("webkit:headless", true);
        });
    }

    /**
     * Returns the WebDriver instance for the current thread using the default browser from configuration.
     *
     * <p>
     * This method retrieves or creates a WebDriver instance for the calling thread. If no driver exists
     * for the current thread, a new one is created using the browser configured in {@link SelelenticConfig}.
     * The WebDriver instance is reused for subsequent calls from the same thread.
     *
     * <p>
     * Generally, the accessing the driver directly should be limited when implementing tests, pages, and components.
     * The Selentic Frame implements many cases where the WebDriver class is often directly accessed, such as
     * window switching ({@link ScWindow}, frame switching ({@link ScFrame}, {@link ScFrameContent}),
     * JavaScript execution ({@link #executeScript(String, Object...)}, {@link #executeAsyncScript(String, Object...)}), accessing {@link Actions}
     * object ({@link ScComponent#actions()}), handling {@link Alert} ({@link ScPage#inAlert(ScPage.ScAlertAction)}), etc.
     *
     * <p>
     * <strong>Thread Safety:</strong> This method is synchronized to ensure thread-safe access. Each thread
     * maintains its own WebDriver instance, enabling parallel test execution.
     * 
     *
     * <p>
     * <strong>Browser Selection:</strong> The browser type is determined by the configuration setting
     * {@code browser} in the {@code selentic.conf} file. The default is Chrome. It can also be set in code using
     * {@link #setBrowser(ScBrowser)} before the web driver starts.
     * 
     *
     * @return the {@link WebDriver} instance for the current thread
     * @see #driver()
     * @see SelelenticConfig#browser()
     * @see #setBrowser(ScBrowser)
     * @see ScWindow
     * @see ScFrame
     * @see ScFrameContent
     * @see #executeScript(String, Object...)
     * @see #executeAsyncScript(String, Object...)
     * @see ScComponent#actions()
     * @see ScPage#inAlert(ScPage.ScAlertAction)
     */
    public static synchronized WebDriver driver() {
        return context().driver();
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
     * {@link #driver()} to ensure the options are applied to the new ChromeDriver instance.
     * 
     *
     * <p>
     * <strong>Common Chrome Options:</strong>
     * <pre>{@code
     * Selentic.withChromeOptions((options, prefs) -> {
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
     * @param optionSetup a {@link ScWebDriverOptions.ChromeOptionSetup} functional interface that receives
     *                   the Chrome options and preferences for configuration
     * @see #withFirefoxOptions(ScWebDriverOptions.FirefoxOptionSetup)
     * @see #withEdgeOptions(ScWebDriverOptions.EdgeOptionSetup)
     * @see #enableHeadless()
     */
    public static synchronized void withChromeOptions(ScWebDriverOptions.@NonNull ChromeOptionSetup optionSetup) {
        context().withChromeOptions(optionSetup);
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
     * {@link #driver()} to ensure the options are applied to the new FirefoxDriver instance.
     * 
     *
     * <p>
     * <strong>Common Firefox Options:</strong>
     * <pre>{@code
     * Selentic.withFirefoxOptions(options -> {
     *     options.addArguments("--headless");                           // Run in headless mode
     *     options.addPreference("browser.download.folderList", 2);     // Use custom download folder
     *     options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
     * });
     * }</pre>
     *
     *
     * @param optionSetup a {@link ScWebDriverOptions.FirefoxOptionSetup} functional interface that receives
     *                   the Firefox options for configuration
     * @see #withChromeOptions(ScWebDriverOptions.ChromeOptionSetup)
     * @see #withEdgeOptions(ScWebDriverOptions.EdgeOptionSetup)
     * @see #enableHeadless()
     */
    public static synchronized void withFirefoxOptions(ScWebDriverOptions.@NonNull FirefoxOptionSetup optionSetup) {
        context().withFirefoxOptions(optionSetup);
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
     * {@link #driver()} to ensure the options are applied to the new EdgeDriver instance.
     * 
     *
     * <p>
     * <strong>Common Edge Options:</strong>
     * <pre>{@code
     * Selentic.withEdgeOptions((options, prefs) -> {
     *     options.addArguments("--headless");                           // Run in headless mode
     *     options.addArguments("--disable-gpu");                        // Disable GPU acceleration
     *     options.addArguments("--no-sandbox");                         // Bypass OS security model
     *     prefs.put("download.prompt_for_download", false);            // Auto-download files
     * });
     * }</pre>
     *
     *
     * @param optionSetup a {@link ScWebDriverOptions.EdgeOptionSetup} functional interface that receives
     *                   the Edge options and preferences for configuration
     * @see #withChromeOptions(ScWebDriverOptions.ChromeOptionSetup)
     * @see #withFirefoxOptions(ScWebDriverOptions.FirefoxOptionSetup)
     * @see #enableHeadless()
     */
    public static synchronized void withEdgeOptions(ScWebDriverOptions.@NonNull EdgeOptionSetup optionSetup) {
        context().withEdgeOptions(optionSetup);
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
     * {@link #driver()} to ensure the options are applied to the new SafariDriver instance.
     * 
     *
     * <p>
     * <strong>Note:</strong> Safari driver support is more limited compared to Chrome and Firefox.
     * Some options available in other browsers may not be supported in Safari.
     * 
     *
     * @param optionSetup a {@link ScWebDriverOptions.SafariOptionSetup} functional interface that receives
     *                   the Safari options for configuration
     * @see #withChromeOptions(ScWebDriverOptions.ChromeOptionSetup)
     * @see #withFirefoxOptions(ScWebDriverOptions.FirefoxOptionSetup)
     * @see #enableHeadless()
     */
    public static synchronized void withSafariOptions(ScWebDriverOptions.@NonNull SafariOptionSetup optionSetup) {
        context().withSafariOptions(optionSetup);
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
     * Selentic.setWebDriverListener(new WebDriverListener() {
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
    public static void setWebDriverListener(@NonNull WebDriverListener listener) {
        context().setWebDriverListener(listener);
    }

    /**
     * Navigates to the specified URL.
     *
     * <p>
     * This method starts the web driver, then navigates the
     * current browser to the specified URL.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * Selentic.open("https://example.com");
     * }</pre>
     *
     *
     * @param url the URL to navigate to
     * @see #open()
     * @see #driver()
     */
    public static void open(@NonNull String url) {
        driver().get(url);
    }

    /**
     * Navigates to a blank page.
     *
     * <p>
     * This method starts the web driver, then navigates the current browser to about:blank, which is useful for clearing the browser
     * state or initializing a clean slate before test execution.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * Selentic.open();  // Navigate to blank page
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
     * <p>
     * To ensure WebDriver state and thread integrity, it is highly recommended to call this method rather than
     * calling {@link WebDriver#quit()} directly by accessing the web driver object.
     *
     * <p>
     * <strong>Thread Safety:</strong> This method is synchronized to ensure thread-safe access to the
     * driver map.
     * 
     *
     * @see #driver()
     */
    public static synchronized void quit() {
        try {
            driver().quit();
        } finally {
            CONTEXT.remove();
        }
    }

    /**
     * Executes JavaScript code in the browser context.
     *
     * <p>
     * This method executes the provided JavaScript string in the context of the current page loaded in the
     * WebDriver. It automatically converts {@link ScComponent} parameters to their underlying {@link WebElement}
     * objects, allowing for seamless integration with the framework's component model.
     * 
     *
     * <p>
     * <strong>Parameter Conversion:</strong> If any parameter is an instance of {@link ScComponent}, it is
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
     *              {@link ScComponent} instances are converted to {@link WebElement}
     * @return the result of JavaScript execution, or null if no value is returned
     * @see #executeAsyncScript(String, Object...)
     * @see JavascriptExecutor
     */
    public static @Nullable Object executeScript(@NonNull String script, @NonNull Object... params) {
        final JavascriptExecutor executor = (JavascriptExecutor) driver();
        final Object[] objects = Arrays.stream(params).map(o -> o instanceof ScComponent ? ((ScComponent) o).existingElement() : o).toArray();

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
     * <strong>Parameter Conversion:</strong> If any parameter is an instance of {@link ScComponent}, it is
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
     *              {@link ScComponent} instances are converted to {@link WebElement}
     * @return the result of asynchronous JavaScript execution, or null if no value is returned
     * @see #executeScript(String, Object...)
     * @see JavascriptExecutor
     */
    public static @Nullable Object executeAsyncScript(@NonNull String script, @NonNull Object... params) {
        final JavascriptExecutor executor = (JavascriptExecutor) driver();
        final List<Object> objects = Arrays.stream(params).map(o -> o instanceof ScComponent ? ((ScComponent) o).existingElement() : o).toList();

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
     * {@link ScLogHandler#screenshotDirectory()}.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * Selentic.screenshot();  // Saves as: 1697234567890.png
     * }</pre>
     *
     *
     * @see #screenshot(String)
     * @see ScLogHandler#screenshotDirectory()
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
     * {@link ScLogHandler#screenshotDirectory()}.
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
     * Selentic.screenshot("login-success");        // Saves as: 1697234567890-login-success.png
     * Selentic.screenshot("checkout-page");        // Saves as: 1697234567890-checkout-page.png
     *
     * // Without name
     * Selentic.screenshot();                       // Saves as: 1697234567890.png
     * }</pre>
     *
     *
     * @param screenshotName an optional descriptive name for the screenshot; if empty, only timestamp is used
     * @see #screenshot()
     * @see ScLogHandler#screenshotDirectory()
     * @see TakesScreenshot
     */
    public static void screenshot(@NonNull String screenshotName) {
        if (!screenshotName.isEmpty()) {
            screenshotName = "-" + screenshotName;
        }

        try {
            final byte[] screenshotData = ((TakesScreenshot) driver()).getScreenshotAs(OutputType.BYTES);
            final Path screenshotPath = Path.of(ScLogHandler.screenshotDirectory().getAbsolutePath(), System.currentTimeMillis() + screenshotName + ".png");

            // Write the byte array to the file
            Files.write(screenshotPath, screenshotData);
            LOG.debug("Screenshot: file:///{}", screenshotPath.toFile().getAbsolutePath().replace("\\", "/"));
        } catch (IOException ex) {
            LOG.error("Error while taking screenshot." , ex);
        }
    }

    /**
     * Get context for this thread.
     * @return a context
     */
    private static @NonNull SelenticWebDriverContext context() {
        return ScNullCheck.requiresNonNull(CONTEXT.get(), SelenticWebDriverContext.class);
    }
}