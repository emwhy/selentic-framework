package org.selion_framework.lib;

public final class SnSelectorNthChildProperty extends SnSelectorProperty implements SnCssSelectorPropertyType {
    private final int index;

    SnSelectorNthChildProperty(int index) {
        this.index = index + 1;
    }

    @Override
    public String build(Types type) {
        String selector = ":nth-child(" + index + ")";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
