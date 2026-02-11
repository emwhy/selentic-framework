package org.selion_framework.lib.config;

import ch.qos.logback.classic.Level;
import org.selion_framework.lib.SeBrowser;

public class SelionConfig {
    private static final SelionConfig GLOBAL_CONFIG = new SelionConfig();

    private SeBrowser browser = SeBrowser.Chrome;
    public long waitTimeoutMilliseconds = 5000;
    public String rootLogLevel = Level.INFO.levelStr;
    public String selionLogLevel = Level.DEBUG.levelStr;
    public long keepLogInMinutes = 0;

    public static SelionConfig config() {
        return GLOBAL_CONFIG;
    }

    public SeBrowser browser() {
        return this.browser;
    }

    public void setBrowser(SeBrowser browser) {
        this.browser = browser;
    }

    private SelionConfig() {
    }

}
