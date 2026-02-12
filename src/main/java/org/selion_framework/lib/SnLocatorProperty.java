package org.selion_framework.lib;

public abstract sealed class SnLocatorProperty permits SnLocatorAttributeProperty, SnLocatorCssClassesProperty, SnLocatorIndexProperty, SnLocatorNotProperty {
    private boolean negate = false;

    abstract protected String build();

    protected void setNegate() {
        this.negate = true;
    }

    protected boolean isNegated() {
        return negate;
    }
}
