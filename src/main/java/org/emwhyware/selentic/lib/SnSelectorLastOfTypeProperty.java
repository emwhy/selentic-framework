package org.emwhyware.selentic.lib;

public final class SnSelectorLastOfTypeProperty extends SnSelectorProperty implements SnCssSelectorPropertyType {

    SnSelectorLastOfTypeProperty() {
    }

    @Override
    public String build(Types type) {
        String selector = ":last-of-type";

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
