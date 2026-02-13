package org.selion_framework.lib;

public final class SnSelectorIndexProperty extends SnSelectorProperty {
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
    protected String build() {
        String xpath = "";

        switch (this.condition) {
            case At -> xpath = "position() = " + this.index;
            case From -> xpath = "position() >= " + this.index;
            case To -> xpath =  "position() <= " + this.index;
            case Last -> xpath = "last()";
        }

        if (this.negated()) {
            xpath = "not(" + xpath + ")";
        }
        return "[" + xpath + "]";
    }

    enum Conditions {
        At, From, To, Last
    }
}
