package org.selion_framework.lib;

public final class SnSelectorNotProperty extends SnSelectorProperty {
    private final SnSelectorProperty selectorProperty;

    SnSelectorNotProperty(SnSelectorProperty selectorProperty) {
        this.selectorProperty = selectorProperty;
    }

    @Override
    protected String build() {
        selectorProperty.setNegate();
        return selectorProperty.build();
    }
}
