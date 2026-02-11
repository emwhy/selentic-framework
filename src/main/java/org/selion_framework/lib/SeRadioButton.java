package org.selion_framework.lib;

public class SeRadioButton extends SeSelectableComponent {
    @Override
    protected void rules(SeComponentRule rule) {
        rule.tag().is("input");
        rule.type().is("radio");
    }
}
