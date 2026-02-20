package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScSelectorFirstChildProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {

    ScSelectorFirstChildProperty() {
    }

    @Override
    public String build(@NonNull Types type) {
        String selector = ":first-child";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
