package org.selion_framework.lib;

public final class SnCssSelectorChild extends SnCssSelector {
    SnCssSelectorChild(SnSelectorProperty... selectorProperties) {
        super(selectorProperties);
    }

    SnCssSelectorChild(SnCssSelector priorSelectorNode, SnSelectorProperty... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "";
    }
}
