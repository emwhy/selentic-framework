package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScXPathPreceding extends ScXPath {
    ScXPathPreceding(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathPreceding(@NonNull ScXPath priorSelectorNode, @NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    ScXPathPreceding(@NonNull ScXpathPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    ScXPathPreceding(@NonNull ScXPath priorSelectorNode, @NonNull ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding::";
    }
}
