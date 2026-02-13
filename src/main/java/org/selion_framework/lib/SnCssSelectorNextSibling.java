package org.selion_framework.lib;

public final class SnCssSelectorNextSibling extends SnCssSelector {
    SnCssSelectorNextSibling(SnCssSelectorPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    SnCssSelectorNextSibling(SnCssSelector priorSelectorNode, SnCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return " + ";
    }
}
