package org.emwhyware.selion.lib;

public final class SnSelectorLastChildProperty extends SnSelectorProperty implements SnCssSelectorPropertyType {

    SnSelectorLastChildProperty() {
    }

    @Override
    public String build(Types type) {
        String selector = ":last-child";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
