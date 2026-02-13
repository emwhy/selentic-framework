package org.selion_framework.lib;

public final class SnSelectorIndexProperty extends SnSelectorProperty implements SnXpathPropertyType {
    private final Conditions condition;
    private final int index;

    SnSelectorIndexProperty(SnSelectorIndexProperty.Conditions condition, int index) {
        this.condition = condition;
        this.index = index + 1;
    }

    SnSelectorIndexProperty(SnSelectorIndexProperty.Conditions condition) {
        this.condition = condition;
        this.index = -1;
    }

    @Override
    public String build(Types type) {
        String selector = "";

        switch (this.condition) {
            case At -> selector = "position() = " + this.index;
            case From -> selector = "position() >= " + this.index;
            case To -> selector =  "position() <= " + this.index;
            case Last -> selector = "last()";
        }

        if (this.negated()) {
            selector = "not(" + selector + ")";
        }
        return "[" + selector + "]";
    }

    enum Conditions {
        At, From, To, Last
    }
}
