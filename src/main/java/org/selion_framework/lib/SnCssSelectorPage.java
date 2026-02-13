package org.selion_framework.lib;

public final class SnCssSelectorPage extends SnCssSelector {
    SnCssSelectorPage(SnCssSelectorPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    SnCssSelectorPage(SnCssSelector priorSelectorNode, SnCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "";
    }
}
