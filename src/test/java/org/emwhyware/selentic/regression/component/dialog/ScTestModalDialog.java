package org.emwhyware.selentic.regression.component.dialog;

import org.emwhyware.selentic.lib.*;

public class ScTestModalDialog extends ScDialog {
    private static final ScXPath TEXTBOX = _xpath.descendant("input", _id().is("test-modal-dialog-textbox"));
    private static final ScXPath CLOSE_BUTTON = _xpath.descendant("button", _cssClasses("close"));

    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("dialog");
        rule.id().is("test-modal-dialog");
    }

    public ScTextbox textbox() {
        return $component(TEXTBOX, ScTextbox.class);
    }

    public ScButton closeButton() {
        return $component(CLOSE_BUTTON, ScButton.class);
    }
}
