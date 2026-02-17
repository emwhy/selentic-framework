package org.emwhyware.selentic.lib;

import java.util.Arrays;

public enum SnBrowser {
    Chrome, Firefox, Edge, Safari;

    public static SnBrowser toEnum(String browserText) {
        return Arrays.stream(SnBrowser.values()).filter(b -> b.toString().equalsIgnoreCase(browserText)).findFirst().orElse(null);
    }
}
