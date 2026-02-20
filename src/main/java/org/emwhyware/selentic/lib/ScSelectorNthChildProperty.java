package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScSelectorNthChildProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {
    private final int index;

    ScSelectorNthChildProperty(int index) {
        this.index = index + 1;
    }

    @Override
    public String build(@NonNull Types type) {
        String selector = ":nth-child(" + index + ")";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
