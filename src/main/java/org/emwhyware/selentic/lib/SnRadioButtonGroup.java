package org.emwhyware.selentic.lib;

public class SnRadioButtonGroup<T extends ScRadioButton> extends SnComponentCollection<T> {
    public String selectedText() {
        return this.filter(ScRadioButton::isSelected).getFirst().text();
    }

    public void select(String text) {
        this.entry(text).select();
    }

}
