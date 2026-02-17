package org.emwhyware.selentic.lib;

public final class SnSelectorNthLastChildProperty extends SnSelectorProperty implements SnCssSelectorPropertyType {
    private final int index;

    SnSelectorNthLastChildProperty(int index) {
        this.index = index + 1;
    }

    @Override
    public String build(Types type) {
        String selector = ":nth-last-child(" + index + ")";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
