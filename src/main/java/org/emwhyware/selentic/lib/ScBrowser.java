package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;

public enum ScBrowser {
    Chrome, Firefox, Edge, Safari;

    public static ScBrowser toEnum(@NonNull String browserText) {
        return Arrays.stream(ScBrowser.values()).filter(b -> b.toString().equalsIgnoreCase(browserText)).findFirst().orElse(Chrome);
    }
}
