package org.selion_framework.lib;

public final class SeLocatorIndexProperty extends SeLocatorProperty {
    private final Conditions condition;
    private final int index;

    SeLocatorIndexProperty(SeLocatorIndexProperty.Conditions condition, int index) {
        this.condition = condition;
        this.index = index + 1;
    }

    SeLocatorIndexProperty(SeLocatorIndexProperty.Conditions condition) {
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
