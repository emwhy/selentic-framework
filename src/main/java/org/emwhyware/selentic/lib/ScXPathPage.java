package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScXPathPage extends ScXPath {
    ScXPathPage(@NonNull String tag, @NonNull ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathPage(@NonNull ScXpathPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
