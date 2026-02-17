package org.emwhyware.selentic.lib;

public abstract sealed class ScSelectorProperty implements ScSelectorPropertyType permits ScSelectorAttributeProperty, ScSelectorCssClassesProperty, ScSelectorIndexProperty, ScSelectorNotProperty, ScSelectorNthOfTypeProperty, ScSelectorNthChildProperty, ScSelectorTextProperty, ScSelectorIdProperty, ScSelectorTagProperty, ScSelectorNthLastOfTypeProperty, ScSelectorFirstOfTypeProperty, ScSelectorLastOfTypeProperty, ScSelectorNthLastChildProperty, ScSelectorFirstChildProperty, ScSelectorLastChildProperty {
    private boolean negate = false;

    @Override
    abstract public String build(Types type);

    protected ScSelectorProperty setNegate() {
        this.negate = true;
        return this;
    }

    protected boolean negated() {
        return negate;
    }
}
