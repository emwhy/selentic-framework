package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface ScSelectorPropertyType {
    String build(@NonNull Types type);

    enum Types {
        CssSelector, XPath
    }
}
