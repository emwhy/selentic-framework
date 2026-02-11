package org.selion_framework.sanity.component.dialog;

import org.selion_framework.lib.*;

public class SeSanityTestDialog extends SeDialog {
    private static final SeLocatorNode TEXTBOX = _xpath.descendant("input", _id().is("sanitytest-dialog-textbox"));
    private static final SeLocatorNode CLOSE_BUTTON = _xpath.descendant("button", _cssClasses("close"));

    @Override
    protected void rules(SeComponentRule rule) {
        rule.tag().is("div");
        rule.id().is("sanitytest-dialog");
    }


    public final SeTextbox textbox = $component(TEXTBOX, SeTextbox.class);
    public final SeButton closeButton = $component(CLOSE_BUTTON, SeButton.class);
}
