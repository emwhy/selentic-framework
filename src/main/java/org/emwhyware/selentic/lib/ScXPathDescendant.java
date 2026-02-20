package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScXPathDescendant extends ScXPath {
    ScXPathDescendant(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathDescendant(@NonNull ScXPath priorSelectorNode, @NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    ScXPathDescendant(@NonNull ScXpathPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    ScXPathDescendant(@NonNull ScXPath priorSelectorNode, @NonNull ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
