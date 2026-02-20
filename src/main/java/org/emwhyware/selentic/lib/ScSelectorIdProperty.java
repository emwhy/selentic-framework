package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.exception.ScSelectorException;

public final class ScSelectorIdProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {
    private final String id;

    ScSelectorIdProperty(@NonNull String id) {
        this.id = id.trim();

        if (this.id.contains(" ")) {
            throw new ScSelectorException("ID contains space character. This can yield unexpected results.");
        }
    }

    @Override
    public String build(@NonNull Types type) {
        String selector = "#" + id;

        if (this.negated()) {
            selector = ":not(" + selector + ")";
        }
        return selector;
    }
}
