package org.selion_framework.lib.config;

import ch.qos.logback.classic.Level;
import org.selion_framework.lib.SnBrowser;

public class SelionConfig {
    private static final SelionConfig GLOBAL_CONFIG = new SelionConfig();

    private SnBrowser browser = SnBrowser.Chrome;
    public long waitTimeoutMilliseconds = 5000;
    public String rootLogLevel = Level.INFO.levelStr;
    public String selionLogLevel = Level.DEBUG.levelStr;
    public long keepLogInMinutes = 0;

    public static SelionConfig config() {
        return GLOBAL_CONFIG;
    }

    public SnBrowser browser() {
        return this.browser;
    }

    public void setBrowser(SnBrowser browser) {
        this.browser = browser;
    }

    private SelionConfig() {
    }

}
