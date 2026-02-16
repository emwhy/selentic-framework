package org.emwhyware.selion.lib;

public final class SnCssSelectorSibling extends SnCssSelector {

    SnCssSelectorSibling(SnCssSelector priorSelectorNode, SnCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return " ~ ";
    }
}
