package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScSelectorNthOfTypeProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {
    private final int index;

    ScSelectorNthOfTypeProperty(int index) {
        this.index = index + 1;
    }

    @Override
    public String build(@NonNull Types type) {
        String selector = ":nth-of-type(" + index + ")";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
