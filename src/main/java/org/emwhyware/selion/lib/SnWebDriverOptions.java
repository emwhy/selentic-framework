package org.emwhyware.selion.lib;

import org.emwhyware.selion.lib.util.SnLogHandler;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class SnWebDriverOptions {
    private final ChromeOptions chromeOptions = new ChromeOptions();
    private final Map<String, Object> chromePrefs = new HashMap<>();
    private final FirefoxOptions firefoxOptions = new FirefoxOptions();
    private final EdgeOptions edgeOptions = new EdgeOptions();
    private final Map<String, Object> edgePrefs = new HashMap<>();
    private final SafariOptions safariOptions = new SafariOptions();

    SnWebDriverOptions() {
        final File downloadDirectory = SnLogHandler.downloadDirectory();

        initializeChromeOptions(downloadDirectory);
        initializeFirefoxOptions(downloadDirectory);
    }

    private void initializeChromeOptions(File downloadDirectory) {
        edgePrefs.put("download.default_directory", downloadDirectory.getAbsolutePath());
        edgePrefs.put("download.prompt_for_download", false);
        edgePrefs.put("download.directory_upgrade", true);
        edgePrefs.put("plugins.always_open_pdf_externally", true);
        edgePrefs.put("profile.default_content_settings.popups", 0);
        edgePrefs.put("profile.default_content_setting_values.notifications", 2);
        edgePrefs.put("profile.default_content_setting_values.automatic_downloads", 1);
        chromeOptions.setExperimentalOption("prefs", edgePrefs);
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-features=DownloadBubble,DownloadBubbleV2");
    }

    private void initializeEdgeOptions(File downloadDirectory) {
        chromePrefs.put("download.default_directory", downloadDirectory.getAbsolutePath());
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.directory_upgrade", true);
        chromePrefs.put("plugins.always_open_pdf_externally", true);
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("profile.default_content_setting_values.notifications", 2);
        chromePrefs.put("profile.default_content_setting_values.automatic_downloads", 1);
        edgeOptions.setExperimentalOption("prefs", chromePrefs);
        edgeOptions.addArguments("--disable-extensions");
        edgeOptions.addArguments("--disable-gpu");
        edgeOptions.addArguments("--no-sandbox");
        edgeOptions.addArguments("--disable-features=DownloadBubble,DownloadBubbleV2");
    }

    private void initializeFirefoxOptions(File downloadDirectory) {
        firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk", "text/comma-separated-values,application/vnd.ms-excel,application/msword,application/csv,application/ris,text/csv,image/png,application/pdf,text/plain,application/zip,application/x-zip,application/x-zip-compressed,application/download,application/octet-stream");
        firefoxOptions.addPreference("browser.helperApps.alwaysAsk.force", false);
        firefoxOptions.addPreference("browser.download.dir", downloadDirectory.getAbsolutePath());
        firefoxOptions.addPreference("browser.download.panel.shown", true);
        firefoxOptions.addPreference("browser.download.folderList", 2);
        firefoxOptions.addPreference("browser.download.useDownloadDir", true);
        firefoxOptions.addPreference("browser.download.alwaysOpenPanel", false);
        firefoxOptions.addPreference("browser.download.manager.showWhenStarting", false);
        firefoxOptions.addPreference("browser.download.manager.alertOnEXEOpen", false);
        firefoxOptions.addPreference("browser.download.manager.focusWhenStarting", false);
        firefoxOptions.addPreference("browser.download.manager.alertOnEXEOpen", false);
        firefoxOptions.addPreference("browser.download.manager.closeWhenDone", true);
        firefoxOptions.addPreference("browser.download.manager.showAlertOnComplete", false);
        firefoxOptions.addPreference("browser.download.manager.useWindow", false);
        firefoxOptions.addPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
        firefoxOptions.addPreference("pdfjs.disabled", true);

        firefoxOptions.setAcceptInsecureCerts(true);
    }

    ChromeOptions chromeOptions() {
        return chromeOptions;
    }

    Map<String, Object> chromePrefs() {
        return chromePrefs;
    }

    FirefoxOptions firefoxOptions() {
        return firefoxOptions;
    }

    SafariOptions safariOptions() {
        return safariOptions;
    }

    EdgeOptions edgeOptions() {
        return edgeOptions;
    }

    Map<String, Object> edgePrefs() {
        return edgePrefs;
    }

    public interface ChromeOptionSetup {
        void options(ChromeOptions options, Map<String, Object> prefs);
    }

    public interface FirefoxOptionSetup {
        void options(FirefoxOptions options);
    }

    public interface EdgeOptionSetup {
        void options(EdgeOptions options, Map<String, Object> prefs);
    }

    public interface SafariOptionSetup {
        void options(SafariOptions options);
    }
}
