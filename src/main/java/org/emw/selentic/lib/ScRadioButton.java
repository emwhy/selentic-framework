package org.emw.selentic.lib;

public class ScRadioButton extends ScSelectableComponent {
    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("input");
        rule.type().is("radio");
    }
}
