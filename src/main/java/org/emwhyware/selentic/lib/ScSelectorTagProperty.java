package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.exception.ScSelectorException;

public final class ScSelectorTagProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {
    private final String tag;

    ScSelectorTagProperty(String tag) {
        this.tag = tag.trim();

        if (this.tag.contains(" ")) {
            throw new ScSelectorException("Tag contains space character. This can yield unexpected results.");
        }
    }

    @Override
    public String build(Types type) {
        if (this.negated()) {
            return ":not(" + tag + ")";
        } else {
            return tag;
        }
    }
}
