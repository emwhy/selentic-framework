package org.selion_framework.lib;

public class SeCheckbox extends SeSelectableComponent {
    @Override
    protected void rules(SeComponentRule rule) {
        rule.tag().is("input");
        rule.type().is("checkbox");
    }

    public void deselect() {
        if (this.isSelected()) {
            this.scrolled().click();
        }
    }

}
