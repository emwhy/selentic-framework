package org.selion_framework.lib;

public final class SnSelectorNotProperty extends SnSelectorProperty {
    private final SnSelectorProperty locatorProperty;

    SnSelectorNotProperty(SnSelectorProperty locatorProperty) {
        this.locatorProperty = locatorProperty;
    }

    @Override
    protected String build() {
        locatorProperty.setNegate();
        return locatorProperty.build();
    }
}
