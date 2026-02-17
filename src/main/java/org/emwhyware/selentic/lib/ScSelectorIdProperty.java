package org.emwhyware.selentic.lib;

public final class ScSelectorIdProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {
    private final String id;

    ScSelectorIdProperty(String id) {
        this.id = id;
    }

    @Override
    public String build(Types type) {
        String selector = "#" + id;

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
