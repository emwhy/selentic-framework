package org.emwhyware.selentic.lib;

public final class ScCssSelectorSibling extends ScCssSelector {

    ScCssSelectorSibling(ScCssSelector priorSelectorNode, ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return " ~ ";
    }
}
