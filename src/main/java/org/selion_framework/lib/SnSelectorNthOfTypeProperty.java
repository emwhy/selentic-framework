package org.selion_framework.lib;

public final class SnSelectorNthOfTypeProperty extends SnSelectorProperty implements SnCssSelectorPropertyType {
    private final int index;

    SnSelectorNthOfTypeProperty(int index) {
        this.index = index + 1;
    }

    @Override
    public String build(Types type) {
        String selector = ":nth-of-type(" + index + ")";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
