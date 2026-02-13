package org.selion_framework.lib;

public final class SnSelectorNotProperty extends SnSelectorProperty {
    private final SnSelectorProperty selectorProperty;

    SnSelectorNotProperty(SnSelectorProperty selectorProperty) {
        this.selectorProperty = selectorProperty.setNegate();
    }

    @Override
    protected String build() {
        return selectorProperty.build();
    }
}
