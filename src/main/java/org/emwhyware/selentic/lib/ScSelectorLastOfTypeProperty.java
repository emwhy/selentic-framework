package org.emwhyware.selentic.lib;

public final class ScSelectorLastOfTypeProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {

    ScSelectorLastOfTypeProperty() {
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
