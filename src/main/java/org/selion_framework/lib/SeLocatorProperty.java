package org.selion_framework.lib;

public abstract class SeLocatorProperty {
    private boolean negate = false;

    abstract protected String build();

    protected void setNegate() {
        this.negate = true;
    }

    protected boolean isNegated() {
        return negate;
    }
}
