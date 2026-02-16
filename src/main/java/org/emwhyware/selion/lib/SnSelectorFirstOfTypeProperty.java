package org.emwhyware.selion.lib;

public final class SnSelectorFirstOfTypeProperty extends SnSelectorProperty implements SnCssSelectorPropertyType {

    SnSelectorFirstOfTypeProperty() {
    }

    @Override
    public String build(Types type) {
        String selector = ":first-of-type";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
