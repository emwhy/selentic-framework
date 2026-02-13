package org.selion_framework.lib;

public final class SnSelectorIdProperty extends SnSelectorProperty implements SnCssSelectorPropertyType {
    private final String id;

    SnSelectorIdProperty(String id) {
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
