package org.selion_framework.lib;

public final class SnCssSelectorSibling extends SnCssSelector {
    SnCssSelectorSibling(SnSelectorProperty... selectorProperties) {
        super(selectorProperties);
    }

    SnCssSelectorSibling(SnCssSelector priorSelectorNode, SnSelectorProperty... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "";
    }
}
