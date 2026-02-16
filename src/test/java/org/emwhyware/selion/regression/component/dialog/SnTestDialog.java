package org.emwhyware.selion.regression.component.dialog;

import org.emwhyware.selion.lib.*;

public class SnTestDialog extends SnDialog {
    private static final SnXPath TEXTBOX = _xpath.descendant("input", _id().is("test-dialog-textbox"));
    private static final SnXPath CLOSE_BUTTON = _xpath.descendant("button", _cssClasses("close"));

    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("div");
        rule.id().is("test-dialog");
    }


    public final SnTextbox textbox = $component(TEXTBOX, SnTextbox.class);
    public final SnButton closeButton = $component(CLOSE_BUTTON, SnButton.class);
}
