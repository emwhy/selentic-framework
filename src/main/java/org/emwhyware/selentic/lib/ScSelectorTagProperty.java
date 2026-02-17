package org.emwhyware.selentic.lib;

public final class ScSelectorTagProperty extends ScSelectorProperty implements ScCssSelectorPropertyType {
    private final String tag;

    ScSelectorTagProperty(String tag) {
        this.tag = tag;
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
