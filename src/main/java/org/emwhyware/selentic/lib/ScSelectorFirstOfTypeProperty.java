package org.emwhyware.selentic.lib;

public final class ScSelectorFirstOfTypeProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {

    ScSelectorFirstOfTypeProperty() {
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
