package org.selion_framework.lib;

public final class SnCssSelectorDescendant extends SnCssSelector {
    SnCssSelectorDescendant(SnSelectorProperty... selectorProperties) {
        super(selectorProperties);
    }

    SnCssSelectorDescendant(SnCssSelector priorSelectorNode, SnSelectorProperty... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "";
    }
}
