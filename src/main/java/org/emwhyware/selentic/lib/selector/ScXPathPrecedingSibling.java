package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScXPathPrecedingSibling extends ScXPath {
    ScXPathPrecedingSibling(@NonNull ScXPath priorSelectorNode, @NonNull String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    ScXPathPrecedingSibling(@NonNull ScXPath priorSelectorNode, @NonNull ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding-sibling::";
    }
}
