package org.selion_framework.lib;

public final class SnCssSelectorNextSibling extends SnCssSelector {
    SnCssSelectorNextSibling(SnSelectorProperty... selectorProperties) {
        super(selectorProperties);
    }

    SnCssSelectorNextSibling(SnCssSelector priorSelectorNode, SnSelectorProperty... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "";
    }
}
