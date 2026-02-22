package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public abstract sealed class ScSelectorProperty implements ScSelectorPropertyType permits ScSelectorAttributeProperty, ScSelectorCssClassesProperty, ScSelectorIndexProperty, ScSelectorNotProperty, ScSelectorNthOfTypeProperty, ScSelectorNthChildProperty, ScSelectorTextProperty, ScSelectorIdProperty, ScSelectorTagProperty, ScSelectorNthLastOfTypeProperty, ScSelectorFirstOfTypeProperty, ScSelectorLastOfTypeProperty, ScSelectorNthLastChildProperty, ScSelectorFirstChildProperty, ScSelectorLastChildProperty {
    private boolean negate = false;

    @Override
    abstract public String build(@NonNull Types type);

    protected ScSelectorProperty setNegate() {
        this.negate = true;
        return this;
    }

    protected boolean negated() {
        return negate;
    }
}
