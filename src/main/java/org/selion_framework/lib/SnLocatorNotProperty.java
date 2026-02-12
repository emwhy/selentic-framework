package org.selion_framework.lib;

public final class SnLocatorNotProperty extends SnLocatorProperty {
    private final SnLocatorProperty locatorProperty;

    SnLocatorNotProperty(SnLocatorProperty locatorProperty) {
        this.locatorProperty = locatorProperty;
    }

    @Override
    protected String build() {
        locatorProperty.setNegate();
        return locatorProperty.build();
    }
}
