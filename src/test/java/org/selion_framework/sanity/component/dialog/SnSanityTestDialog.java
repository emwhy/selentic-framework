package org.selion_framework.sanity.component.dialog;

import org.selion_framework.lib.*;

public class SnSanityTestDialog extends SnDialog {
    private static final SnXPathSelector TEXTBOX = _xpath.descendant("input", _id().is("sanitytest-dialog-textbox"));
    private static final SnXPathSelector CLOSE_BUTTON = _xpath.descendant("button", _cssClasses("close"));

    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("div");
        rule.id().is("sanitytest-dialog");
    }


    public final SnTextbox textbox = $component(TEXTBOX, SnTextbox.class);
    public final SnButton closeButton = $component(CLOSE_BUTTON, SnButton.class);
}
