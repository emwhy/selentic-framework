package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScSelectorLastChildProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {

    ScSelectorLastChildProperty() {
    }

    @Override
    public String build(@NonNull Types type) {
        String selector = ":last-child";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
