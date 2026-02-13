package org.selion_framework.lib;

public abstract sealed class SnSelectorProperty permits SnSelectorAttributeProperty, SnSelectorCssClassesProperty, SnSelectorIndexProperty, SnSelectorNotProperty {
    private boolean negate = false;

    abstract protected String build();

    protected SnSelectorProperty setNegate() {
        this.negate = true;
        return this;
    }

    protected boolean negated() {
        return negate;
    }
}
