package org.selion_framework.lib;

public class SnCheckbox extends SnSelectableComponent {
    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("input");
        rule.type().is("checkbox");
    }

    public void deselect() {
        if (this.isSelected()) {
            this.scrolled().click();
        }
    }

}
