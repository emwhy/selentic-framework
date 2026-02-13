package org.selion_framework.lib;

public final class SnCssSelectorPage extends SnCssSelector {
    SnCssSelectorPage(SnSelectorProperty... selectorProperties) {
        super(selectorProperties);
    }

    SnCssSelectorPage(SnCssSelector priorSelectorNode, SnSelectorProperty... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "";
    }
}
