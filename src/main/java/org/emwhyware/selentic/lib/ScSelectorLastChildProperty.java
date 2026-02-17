package org.emwhyware.selentic.lib;

public final class ScSelectorLastChildProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {

    ScSelectorLastChildProperty() {
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
