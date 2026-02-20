package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScCssSelectorSibling extends ScCssSelector {

    ScCssSelectorSibling(@NonNull ScCssSelector priorSelectorNode, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    ScCssSelectorSibling(@NonNull ScCssSelector priorSelectorNode, @NonNull String tag, @NonNull ScCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return " ~ ";
    }
}
