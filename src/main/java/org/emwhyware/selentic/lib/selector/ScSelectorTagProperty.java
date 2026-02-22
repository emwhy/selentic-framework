package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.exception.ScSelectorException;

public final class ScSelectorTagProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {
    private final String tag;

    ScSelectorTagProperty(String tag) {
        this.tag = tag.trim();
    }

    @Override
    public String build(@NonNull Types type) {
        if (this.tag.contains(" ")) {
            throw new ScSelectorException("Tag contains space character. This can yield unexpected results.");
        }

        if (this.negated()) {
            return ":not(" + tag + ")";
        } else {
            return tag;
        }
    }
}
