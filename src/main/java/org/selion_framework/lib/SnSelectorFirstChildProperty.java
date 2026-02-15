package org.selion_framework.lib;

public final class SnSelectorFirstChildProperty extends SnSelectorProperty implements SnCssSelectorPropertyType {

    SnSelectorFirstChildProperty() {
    }

    @Override
    public String build(Types type) {
        String selector = ":first-child";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
