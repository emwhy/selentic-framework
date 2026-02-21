package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScSelectorNthLastChildProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {
    private final int index;

    ScSelectorNthLastChildProperty(int index) {
        this.index = index + 1;
    }

    @Override
    public String build(@NonNull Types type) {
        String selector = ":nth-last-child(" + index + ")";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
