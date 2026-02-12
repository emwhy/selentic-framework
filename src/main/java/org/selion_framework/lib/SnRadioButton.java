package org.selion_framework.lib;

public class SnRadioButton extends SnSelectableComponent {
    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("input");
        rule.type().is("radio");
    }
}
