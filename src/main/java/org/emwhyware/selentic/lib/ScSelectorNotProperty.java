package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScSelectorNotProperty extends ScSelectorProperty implements ScXpathPropertyType, ScCssSelectorPropertyType {
    private final ScSelectorProperty selectorProperty;

    ScSelectorNotProperty(ScSelectorProperty selectorProperty) {
        this.selectorProperty = selectorProperty.setNegate();
    }

    @Override
    public String build(@NonNull Types type) {
        return selectorProperty.build(type);
    }
}
