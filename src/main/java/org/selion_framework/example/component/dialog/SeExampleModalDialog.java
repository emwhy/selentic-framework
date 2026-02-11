package org.selion_framework.example.component.dialog;

import org.selion_framework.lib.*;

public class SeExampleModalDialog extends SeDialog {
    private static final SeLocatorNode TEXTBOX = _xpath.descendant("input", _id().is("example-modal-dialog-textbox"));
    private static final SeLocatorNode CLOSE_BUTTON = _xpath.descendant("button", _cssClasses("close"));

    @Override
    protected void rules(SeComponentRule rule) {
        rule.tag().is("dialog");
        rule.id().is("example-modal-dialog");
    }

    public final SeTextbox textbox = $component(TEXTBOX, SeTextbox.class);
    public final SeButton closeButton = $component(CLOSE_BUTTON, SeButton.class);
}
