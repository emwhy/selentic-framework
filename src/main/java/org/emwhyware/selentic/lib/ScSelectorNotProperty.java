package org.emwhyware.selentic.lib;

public final class ScSelectorNotProperty extends ScSelectorProperty implements ScXpathPropertyType, ScCssSelectorPropertyType {
    private final ScSelectorProperty selectorProperty;

    ScSelectorNotProperty(ScSelectorProperty selectorProperty) {
        this.selectorProperty = selectorProperty.setNegate();
    }

    @Override
    public String build(Types type) {
        return selectorProperty.build(type);
    }
}
