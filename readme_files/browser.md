[< Return to README](../README.md)
## Handling Browsers

Selentic Framework supports Chrome, Firefox, Edge, and Safari browser.

### Browser Selection

Chrome browser is selected by default. The browser can be set in 2 different ways.

- **selentic.conf** file
- **Selentic.setBrowser(ScBrowser)** method call

When the browser value is set in **selentic.conf** file, the value is set as a global default. This can be overridden by 
using **Selentic.setBrowser(ScBrowser)** as needed.

**Selentic.setBrowser(ScBrowser)** method must be called before the first time the web driver is loaded by calling 
**Selentic.driver()**, **Selentic.open(URL)** or **Selentic.open()** method.

If running tests in multithreaded execution, each thread can be set with different browser, because each thread gets its own context. 
Simply call **Selentic.setBrowser(ScBrowser)** method in each thread to set specific browser per thread.

### Headless Option

There are 2 ways to set tests to run in headless mode.

- **selentic.conf** file
- **Selentic.enableHeadless()** method call

**Selentic.enableHeadless** method must be called before the first time the web driver is loaded.

When the browser value is set in **selentic.conf** file, the value is set as a global default. If the headless options is 
omitted in the conf file or is set to false, it can be overridden by calling **Selentic.enableHeadless()** method. 

If running tests in multithreaded execution, each thread can be set with different browser, because each thread gets its own context.
Simply call **Selentic.enableHeadless()** method in each thread to set specific browser per thread.


### Browser Options

Browser options are different for each browser type. Selentic Framework provides methods to set options for each browser type. The arguments take effect when calling the methods before the web driver starts.

As with the browser type, each thread in multithreaded execution gets its own browser option, and it is possible to set different options for each thread.

```java
    Selentic.withChromeOptions((options, prefs) -> {
        options.addArguments("arguments");
    });

    Selentic.withEdgeOptions(((options, prefs) -> {
        options.addArguments("arguments");
    }));
    
    Selentic.withFirefoxOptions((options, mimeTypes) -> {
        options.addArguments("arguments");
    });
    
    Selentic.withSafariOptions(options -> {
        options.setCapability("capability", true);
    });
```

The following commonly used options are predefined, so you don't need to take time to set them up yourself.

```java
    // Predefined Chrome options
    chromePrefs.put("download.default_directory", downloadDirectory.getAbsolutePath());
    chromePrefs.put("download.prompt_for_download", false);
    chromePrefs.put("download.directory_upgrade", true);
    chromePrefs.put("plugins.always_open_pdf_externally", true);
    chromePrefs.put("profile.default_content_settings.popups", 0);
    chromePrefs.put("profile.default_content_setting_values.notifications", 2);
    chromePrefs.put("profile.default_content_setting_values.automatic_downloads", 1);
    chromePrefs.put("browser.show_hub_popup_on_download_start", false);
    chromeOptions.addArguments("--disable-dev-shm-usage");
    chromeOptions.addArguments("--disable-extensions");
    chromeOptions.addArguments("--disable-gpu");
    chromeOptions.addArguments("--no-sandbox");
    chromeOptions.addArguments("--disable-search-engine-choice-screen");
    chromeOptions.addArguments("--disable-features=DownloadBubble,DownloadBubbleV2");
    chromeOptions.addArguments("--disable-popup-blocking");

    // Predefined Edge options.
    edgePrefs.put("download.default_directory", downloadDirectory.getAbsolutePath());
    edgePrefs.put("download.prompt_for_download", false);
    edgePrefs.put("download.directory_upgrade", true);
    edgePrefs.put("plugins.always_open_pdf_externally", true);
    edgePrefs.put("profile.default_content_settings.popups", 0);
    edgePrefs.put("profile.default_content_setting_values.notifications", 2);
    edgePrefs.put("profile.default_content_setting_values.automatic_downloads", 1);
    edgePrefs.put("browser.show_hub_popup_on_download_start", false);
    edgeOptions.addArguments("--disable-dev-shm-usage");
    edgeOptions.addArguments("--disable-extensions");
    edgeOptions.addArguments("--disable-gpu");
    edgeOptions.addArguments("--no-sandbox");
    edgeOptions.addArguments("--disable-search-engine-choice-screen");
    edgeOptions.addArguments("--disable-features=DownloadBubble,DownloadBubbleV2");
    edgeOptions.addArguments("--disable-popup-blocking");

    // Predefined Firefox never to ask to save mime types.
    firefoxNeverAskToSaveMimeTypes.add("application/zip");
    firefoxNeverAskToSaveMimeTypes.add("application/pdf");
    firefoxNeverAskToSaveMimeTypes.add("application/x-zip-compressed");
    firefoxNeverAskToSaveMimeTypes.add("multipart/x-zip");
    firefoxNeverAskToSaveMimeTypes.add("application/x-rar-compressed");
    firefoxNeverAskToSaveMimeTypes.add("application/msword");
    firefoxNeverAskToSaveMimeTypes.add("application/vnd.ms-word.document.macroEnabled.12");
    firefoxNeverAskToSaveMimeTypes.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    firefoxNeverAskToSaveMimeTypes.add("application/vnd.ms-excel");
    firefoxNeverAskToSaveMimeTypes.add("application/rtf");
    firefoxNeverAskToSaveMimeTypes.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    firefoxNeverAskToSaveMimeTypes.add("application/vnd.ms-excel");
    firefoxNeverAskToSaveMimeTypes.add("application/vnd.ms-word.document.macroEnabled.12");
    firefoxNeverAskToSaveMimeTypes.add("application/xls");
    firefoxNeverAskToSaveMimeTypes.add("text/csv");
    firefoxNeverAskToSaveMimeTypes.add("application/vnd.ms-excel.sheet.binary.macroEnabled.12");
    firefoxNeverAskToSaveMimeTypes.add("text/plain");
    firefoxNeverAskToSaveMimeTypes.add("text/csv/xls/xlsb");
    firefoxNeverAskToSaveMimeTypes.add("application/csv");
    firefoxNeverAskToSaveMimeTypes.add("application/download");
    firefoxNeverAskToSaveMimeTypes.add("application/vnd.openxmlformats-officedocument.presentationml.presentation");
    firefoxNeverAskToSaveMimeTypes.add("application/octet-stream");

    // Predefined Firefox options.
    firefoxOptions.addPreference("browser.helperApps.alwaysAsk.force", false);
    firefoxOptions.addPreference("browser.download.dir", downloadDirectory.getAbsolutePath());
    firefoxOptions.addPreference("browser.download.always_ask_before_handling_new_types", false);
    firefoxOptions.addPreference("browser.download.panel.shown", false);
    firefoxOptions.addPreference("browser.download.folderList", 2);
    firefoxOptions.addPreference("browser.download.useDownloadDir", true);
    firefoxOptions.addPreference("browser.download.forbid_open_with", true);
    firefoxOptions.addPreference("browser.download.alwaysOpenPanel", false);
    firefoxOptions.addPreference("browser.download.viewableInternally.enabledTypes", "");
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

```
