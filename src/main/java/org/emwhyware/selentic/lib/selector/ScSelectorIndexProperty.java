package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScSelectorIndexProperty extends ScSelectorProperty implements ScXpathPropertyType {
    private final Conditions condition;
    private final int index;

    ScSelectorIndexProperty(ScSelectorIndexProperty.Conditions condition, int index) {
        this.condition = condition;
        this.index = index + 1;
    }

    ScSelectorIndexProperty(ScSelectorIndexProperty.Conditions condition) {
        this.condition = condition;
        this.index = -1;
    }

    @Override
    public String build(@NonNull Types type) {
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
