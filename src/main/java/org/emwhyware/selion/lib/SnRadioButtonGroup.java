package org.emwhyware.selion.lib;

public class SnRadioButtonGroup<T extends SnRadioButton> extends SnComponentCollection<T> {
    public String selectedText() {
        return this.filter(SnRadioButton::isSelected).getFirst().text();
    }

    public void select(String text) {
        this.entry(text).select();
    }

}
