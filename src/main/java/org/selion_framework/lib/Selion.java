package org.selion_framework.lib;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.selion_framework.lib.config.SelionConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Selion
 * Definition: A medieval term for a strip of land between two furrows.
 * Why it works: It evokes a clear, defined path or "strip." In a modular web component architecture,
 * you are testing individual "strips" (components) that make up the whole field (the page).
 */
public class Selion {
    private static final HashMap<Long, WebDriver> DRIVERS = new HashMap<>();
    private static final SnWebDriverOptions WEBDRIVER_OPTIONS = new SnWebDriverOptions();
    private static Optional<WebDriverListener> webDriverListener = Optional.empty();

    public static synchronized WebDriver driver() {
        return driver(SelionConfig.config().browser());
    }

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

    public static synchronized void withChromeOptions(SnWebDriverOptions.ChromeOptionSetup optionSetup) {
        optionSetup.options(WEBDRIVER_OPTIONS.chromeOptions(), WEBDRIVER_OPTIONS.chromePrefs());

        WEBDRIVER_OPTIONS.chromeOptions().setExperimentalOption("pref", WEBDRIVER_OPTIONS.chromePrefs());
    }

    public static synchronized void withFirefoxOptions(SnWebDriverOptions.FirefoxOptionSetup optionSetup) {
        optionSetup.options(WEBDRIVER_OPTIONS.firefoxOptions());
    }

    public static synchronized void withEdgeOptions(SnWebDriverOptions.EdgeOptionSetup optionSetup) {
        optionSetup.options(WEBDRIVER_OPTIONS.edgeOptions(), WEBDRIVER_OPTIONS.edgePrefs());

        WEBDRIVER_OPTIONS.edgeOptions().setExperimentalOption("pref", WEBDRIVER_OPTIONS.edgeOptions());
    }

    public static synchronized void withSafariOptions(SnWebDriverOptions.SafariOptionSetup optionSetup) {
        optionSetup.options(WEBDRIVER_OPTIONS.safariOptions());
    }

    public static void setListener(WebDriverListener listener) {
        webDriverListener = Optional.of(listener);
    }

    public static void open(String url) {
        driver().get(url);
    }

    public static void open() {
        driver().get("about:blank");
    }

    public static synchronized void quit() {
        final long threadId = Thread.currentThread().threadId();

        if (DRIVERS.containsKey(threadId)) {
            DRIVERS.get(threadId).quit();
            DRIVERS.remove(threadId);
        }
    }

    public static Object executeScript(String script, Object... params) {
        final JavascriptExecutor executor = (JavascriptExecutor) driver();
        final Object[] objects = Arrays.stream(params).map(o -> o instanceof SnComponent ? ((SnComponent) o).existing() : o).toArray();

        return executor.executeScript(script, objects);
    }

    public static String executeAsyncScript(String script, Object... params) {
        final JavascriptExecutor executor = (JavascriptExecutor) driver();
        final List<Object> objects = Arrays.stream(params).map(o -> o instanceof SnComponent ? ((SnComponent) o).existing() : o).toList();

        return String.valueOf(executor.executeAsyncScript(script, objects));
    }
}
