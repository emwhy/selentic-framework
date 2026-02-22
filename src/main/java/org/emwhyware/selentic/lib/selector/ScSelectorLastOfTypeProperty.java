package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScSelectorLastOfTypeProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {

    ScSelectorLastOfTypeProperty() {
    }

    @Override
    public String build(@NonNull Types type) {
        String selector = ":last-of-type";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
