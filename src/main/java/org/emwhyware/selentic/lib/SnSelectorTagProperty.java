package org.emwhyware.selentic.lib;

public final class SnSelectorTagProperty extends SnSelectorProperty implements SnCssSelectorPropertyType {
    private final String tag;

    SnSelectorTagProperty(String tag) {
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
