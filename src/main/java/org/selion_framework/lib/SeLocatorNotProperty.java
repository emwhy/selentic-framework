package org.selion_framework.lib;

public final class SeLocatorNotProperty extends SeLocatorProperty {
    private final SeLocatorProperty locatorProperty;

    SeLocatorNotProperty(SeLocatorProperty locatorProperty) {
        this.locatorProperty = locatorProperty;
    }

    @Override
    protected String build() {
        locatorProperty.setNegate();
        return locatorProperty.build();
    }
}
