package org.emwhyware.selentic.lib;

public final class ScCssSelectorSibling extends ScCssSelector {

    ScCssSelectorSibling(ScCssSelector priorSelectorNode, ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    ScCssSelectorSibling(ScCssSelector priorSelectorNode, String tag, ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return " ~ ";
    }
}
