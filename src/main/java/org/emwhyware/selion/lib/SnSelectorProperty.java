package org.emwhyware.selion.lib;

public abstract sealed class SnSelectorProperty implements SnSelectorPropertyType permits SnSelectorAttributeProperty, SnSelectorCssClassesProperty, SnSelectorIndexProperty, SnSelectorNotProperty, SnSelectorNthOfTypeProperty, SnSelectorNthChildProperty, SnSelectorTextProperty, SnSelectorIdProperty, SnSelectorTagProperty, SnSelectorNthLastOfTypeProperty, SnSelectorFirstOfTypeProperty, SnSelectorLastOfTypeProperty, SnSelectorNthLastChildProperty, SnSelectorFirstChildProperty, SnSelectorLastChildProperty {
    private boolean negate = false;

    @Override
    abstract public String build(Types type);

    protected SnSelectorProperty setNegate() {
        this.negate = true;
        return this;
    }

    protected boolean negated() {
        return negate;
    }
}
