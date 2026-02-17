package org.emwhyware.selentic.regression.component.dialog;

import org.emwhyware.selentic.lib.*;

public class ScTestDialog extends ScDialog {
    private static final SnXPath TEXTBOX = _xpath.descendant("input", _id().is("test-dialog-textbox"));
    private static final SnXPath CLOSE_BUTTON = _xpath.descendant("button", _cssClasses("close"));

    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("div");
        rule.id().is("test-dialog");
    }


    public final ScTextbox textbox = $component(TEXTBOX, ScTextbox.class);
    public final ScButton closeButton = $component(CLOSE_BUTTON, ScButton.class);
}
