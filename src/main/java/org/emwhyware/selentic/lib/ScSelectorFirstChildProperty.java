package org.emwhyware.selentic.lib;

public final class ScSelectorFirstChildProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {

    ScSelectorFirstChildProperty() {
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
