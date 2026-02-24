package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.exception.ScSelectorException;

public final class ScXPathLimitedBy extends ScXPath {
    private final ScXPath xpathLimit;

    ScXPathLimitedBy(@NonNull ScXPath priorSelectorNode, @NonNull ScXPath xpathLimit) {
        super(priorSelectorNode, "");
        this.xpathLimit = xpathLimit;
    }

    @Override
    protected String nodeText() {
        if (this.xpathLimit.isAbsolute()) {
            return "[" + this.xpathLimit.toString().replaceAll("^/[a-z-]+", "following") + "]";
        } else {
            throw new ScSelectorException("XPath for 'limited by' must be based on _xpath.page() which looks at the entire page rather than inside of a component.");
        }
    }
}
