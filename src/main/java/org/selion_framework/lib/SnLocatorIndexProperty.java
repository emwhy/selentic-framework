package org.selion_framework.lib;

public final class SnLocatorIndexProperty extends SnLocatorProperty {
    private final Conditions condition;
    private final int index;

    SnLocatorIndexProperty(SnLocatorIndexProperty.Conditions condition, int index) {
        this.condition = condition;
        this.index = index + 1;
    }

    SnLocatorIndexProperty(SnLocatorIndexProperty.Conditions condition) {
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

        if (this.isNegated()) {
            xpath = "not(" + xpath + ")";
        }
        return "[" + xpath + "]";
    }

    enum Conditions {
        At, From, To, Last
    }
}
