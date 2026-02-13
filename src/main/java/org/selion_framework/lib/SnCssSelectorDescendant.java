package org.selion_framework.lib;

public final class SnCssSelectorDescendant extends SnCssSelector {
    SnCssSelectorDescendant(SnCssSelectorPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    SnCssSelectorDescendant(SnCssSelector priorSelectorNode, SnCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return " ";
    }
}
