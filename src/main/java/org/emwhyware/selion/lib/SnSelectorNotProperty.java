package org.emwhyware.selion.lib;

public final class SnSelectorNotProperty extends SnSelectorProperty implements SnXpathPropertyType, SnCssSelectorPropertyType {
    private final SnSelectorProperty selectorProperty;

    SnSelectorNotProperty(SnSelectorProperty selectorProperty) {
        this.selectorProperty = selectorProperty.setNegate();
    }

    @Override
    public String build(Types type) {
        return selectorProperty.build(type);
    }
}
