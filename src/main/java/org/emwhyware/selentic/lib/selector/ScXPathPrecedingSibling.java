package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScXPathPrecedingSibling extends ScXPath {
    ScXPathPrecedingSibling(@NonNull ScXPath priorSelectorNode, @NonNull String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    ScXPathPrecedingSibling(@NonNull ScXPath priorSelectorNode, @NonNull ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    ScXPathPrecedingSibling(@NonNull String tag, ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathPrecedingSibling(@NonNull ScXpathPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding-sibling::";
    }
}
