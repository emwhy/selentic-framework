package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScSelectorFirstOfTypeProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {

    ScSelectorFirstOfTypeProperty() {
    }

    @Override
    public String build(@NonNull Types type) {
        String selector = ":first-of-type";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
