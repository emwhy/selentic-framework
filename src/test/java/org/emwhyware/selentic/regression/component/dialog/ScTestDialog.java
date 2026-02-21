package org.emwhyware.selentic.regression.component.dialog;

import org.emwhyware.selentic.lib.ScButton;
import org.emwhyware.selentic.lib.ScComponentRule;
import org.emwhyware.selentic.lib.ScDialog;
import org.emwhyware.selentic.lib.ScTextbox;
import org.emwhyware.selentic.lib.selector.ScXPath;

public class ScTestDialog extends ScDialog {
    private static final ScXPath TEXTBOX = _xpath.descendant("input", _id().is("test-dialog-textbox"));
    private static final ScXPath CLOSE_BUTTON = _xpath.descendant("button", _cssClasses("close"));

    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("div");
        rule.id().is("test-dialog");
    }

    public ScTextbox textbox() {
        return $component(TEXTBOX, ScTextbox.class);
    }

    public ScButton closeButton() {
        return $component(CLOSE_BUTTON, ScButton.class);
    }
}
