package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScXPathSibling extends ScXPath {
    ScXPathSibling(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathSibling(@NonNull ScXPath priorSelectorNode, @NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    ScXPathSibling(@NonNull ScXpathPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    ScXPathSibling(@NonNull ScXPath priorSelectorNode, @NonNull ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/following-sibling::";
    }
}
