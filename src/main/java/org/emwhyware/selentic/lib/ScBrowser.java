package org.emwhyware.selentic.lib;

import java.util.Arrays;

public enum ScBrowser {
    Chrome, Firefox, Edge, Safari;

    public static ScBrowser toEnum(String browserText) {
        return Arrays.stream(ScBrowser.values()).filter(b -> b.toString().equalsIgnoreCase(browserText)).findFirst().orElse(null);
    }
}
