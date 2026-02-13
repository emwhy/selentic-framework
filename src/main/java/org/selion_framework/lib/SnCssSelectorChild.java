package org.selion_framework.lib;

public final class SnCssSelectorChild extends SnCssSelector {
    SnCssSelectorChild(SnCssSelectorPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    SnCssSelectorChild(SnCssSelector priorSelectorNode, SnCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return " > ";
    }
}
