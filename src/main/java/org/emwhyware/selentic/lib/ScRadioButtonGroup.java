package org.emwhyware.selentic.lib;

public class ScRadioButtonGroup<T extends ScRadioButton> extends ScComponentCollection<T> {
    public String selectedText() {
        return this.filter(ScRadioButton::isSelected).getFirst().text();
    }

    public void select(String text) {
        this.entry(text).select();
    }

}
