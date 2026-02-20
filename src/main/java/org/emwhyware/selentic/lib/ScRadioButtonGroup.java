package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScRadioButtonGroup<T extends ScRadioButton> extends ScComponentCollection<T> {
    public String selectedText() {
        return this.filter(ScRadioButton::isSelected).getFirst().text();
    }

    public void select(@NonNull String text) {
        this.entry(text).select();
    }

}
