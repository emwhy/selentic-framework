package org.selion_framework.lib;

public abstract sealed class SeLocatorProperty permits SeLocatorAttributeProperty, SeLocatorCssClassesProperty, SeLocatorIndexProperty, SeLocatorNotProperty {
    private boolean negate = false;

    abstract protected String build();

    protected void setNegate() {
        this.negate = true;
    }

    protected boolean isNegated() {
        return negate;
    }
}
