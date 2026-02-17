package org.emwhyware.selentic.lib;

public final class SnSelectorNthLastOfTypeProperty extends SnSelectorProperty implements SnCssSelectorPropertyType {
    private final int index;

    SnSelectorNthLastOfTypeProperty(int index) {
        this.index = index + 1;
    }

    @Override
    public String build(Types type) {
        String selector = ":nth-last-of-type(" + index + ")";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
