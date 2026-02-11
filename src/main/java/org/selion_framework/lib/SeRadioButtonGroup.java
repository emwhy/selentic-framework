package org.selion_framework.lib;

public class SeRadioButtonGroup<T extends SeRadioButton> extends SeComponentCollection<T> {
    public String selectedText() {
        return this.filter(SeRadioButton::isSelected).getFirst().text();
    }

    public void select(String text) {
        this.entry(text).select();
    }

}
