package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScXPathParent extends ScXPath {
    ScXPathParent() {
        super("*");
    }

    ScXPathParent(@NonNull ScXPath priorSelectorNode) {
        super(priorSelectorNode, "*");
    }

    @Override
    protected String nodeText() {
        return "/parent::";
    }
}
