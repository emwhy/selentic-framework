package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.exception.ScSelectorException;

public final class ScXPathBoundary extends ScXPath {
    private final ScXPath xpathBoundary;

    ScXPathBoundary(@NonNull ScXPath priorSelectorNode, @NonNull ScXPath xpathBoundary) {
        super(priorSelectorNode, "");
        this.xpathBoundary = xpathBoundary;
    }

    @Override
    protected String nodeText() {
        if (this.xpathBoundary.isAbsolute()) {
            return "[" + this.xpathBoundary.toString().replaceAll("^/[a-z-]+", "following") + "]";
        } else {
            throw new ScSelectorException("XPath for 'boundary' must be based on _xpath.page(...) which looks at the entire page rather than inside of a component.");
        }
    }
}
