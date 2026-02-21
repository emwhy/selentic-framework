package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.config.SelelenticConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import java.util.Optional;

public final class SelenticWebDriverContext {
    private final ScWebDriverOptions webDriverOptions = new ScWebDriverOptions();
    private @NonNull ScBrowser browser = SelelenticConfig.config().browser();
    private Optional<WebDriver> driver = Optional.empty();
    private Optional<WebDriverListener> webDriverListener = Optional.empty();

    SelenticWebDriverContext() {}

    synchronized void setBrowser(@NonNull ScBrowser browser) {
        this.browser = browser;
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
     *   <li>{@link #withChromeOptions(ScWebDriverOptions.ChromeOptionSetup)}</li>
     *   <li>{@link #withFirefoxOptions(ScWebDriverOptions.FirefoxOptionSetup)}</li>
     *   <li>{@link #withEdgeOptions(ScWebDriverOptions.EdgeOptionSetup)}</li>
     *   <li>{@link #withSafariOptions(ScWebDriverOptions.SafariOptionSetup)}</li>
     * </ul>
     *
     *
     * @return the {@link WebDriver} instance for the current thread with the specified browser
     * @see Selentic#driver()
     * @see ScBrowser
     */
    synchronized WebDriver driver() {
        if (this.driver.isEmpty()) {
            // Set preferences.
            webDriverOptions.chromeOptions().setExperimentalOption("prefs", webDriverOptions.chromePrefs());
            webDriverOptions.edgeOptions().setExperimentalOption("prefs", webDriverOptions.edgePrefs());

            WebDriver driver = switch (browser) {
                case Chrome -> new ChromeDriver(webDriverOptions.chromeOptions());
                case Edge -> new EdgeDriver(webDriverOptions.edgeOptions());
                case Firefox -> new FirefoxDriver(webDriverOptions.firefoxOptions());
                case Safari -> new SafariDriver();
            };

            // Add listener class, if available.
            if (webDriverListener.isPresent()) {
                driver = new EventFiringDecorator<>(webDriverListener.get()).decorate(driver);
            }
            this.driver = Optional.of(driver);
            return driver;
        } else {
            return this.driver.get();
        }
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
     * <strong>Important:</strong> This method must be called BEFORE calling {@link Selentic#driver()} or
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
     * @see ScWebDriverOptions.ChromeOptionSetup
     * @see #withFirefoxOptions(ScWebDriverOptions.FirefoxOptionSetup)
     * @see #withEdgeOptions(ScWebDriverOptions.EdgeOptionSetup)
     */
    synchronized void withChromeOptions(ScWebDriverOptions.@NonNull ChromeOptionSetup optionSetup) {
        optionSetup.options(webDriverOptions.chromeOptions(), webDriverOptions.chromePrefs());
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
     * <strong>Important:</strong> This method must be called BEFORE calling {@link Selentic#driver()} or
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
     * @see ScWebDriverOptions.FirefoxOptionSetup
     * @see #withChromeOptions(ScWebDriverOptions.ChromeOptionSetup)
     * @see #withEdgeOptions(ScWebDriverOptions.EdgeOptionSetup)
     */
    synchronized void withFirefoxOptions(ScWebDriverOptions.@NonNull FirefoxOptionSetup optionSetup) {
        optionSetup.options(webDriverOptions.firefoxOptions());
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
     * <strong>Important:</strong> This method must be called BEFORE calling {@link Selentic#driver()} or
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
     * @see ScWebDriverOptions.EdgeOptionSetup
     * @see #withChromeOptions(ScWebDriverOptions.ChromeOptionSetup)
     * @see #withFirefoxOptions(ScWebDriverOptions.FirefoxOptionSetup)
     */
    synchronized void withEdgeOptions(ScWebDriverOptions.@NonNull EdgeOptionSetup optionSetup) {
        optionSetup.options(webDriverOptions.edgeOptions(), webDriverOptions.edgePrefs());
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
     * <strong>Important:</strong> This method must be called BEFORE calling {@link Selentic#driver()} or
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
     * @see ScWebDriverOptions.SafariOptionSetup
     * @see #withChromeOptions(ScWebDriverOptions.ChromeOptionSetup)
     * @see #withFirefoxOptions(ScWebDriverOptions.FirefoxOptionSetup)
     */
    synchronized void withSafariOptions(ScWebDriverOptions.@NonNull SafariOptionSetup optionSetup) {
        optionSetup.options(webDriverOptions.safariOptions());
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
     * <strong>Important:</strong> This method must be called BEFORE calling {@link Selentic#driver()} to ensure
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
     * @see Selentic#driver()
     */
    synchronized void setWebDriverListener(@NonNull WebDriverListener listener) {
        this.webDriverListener = Optional.of(listener);
    }

}
