package org.selion_framework.sanity.component;

import org.selion_framework.lib.*;

public class SnSanityTestLongListEntryComponent extends SnComponent {
    private static final SnXPathSelector TITLE_TEXT = _xpath.descendant("span", _cssClasses("title"));
    private static final SnXPathSelector CHECKBOX = _xpath.descendant("input", _type().is("checkbox"));
    private static final SnXPathSelector TEXTBOX = _xpath.descendant("input", _type().is("text"));

    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("div");
        rule.cssClasses().has("long-component-list-entry");
    }

    @Override
    public String key() {
        return titleText.key();
    }

    public final SnGenericComponent titleText = $genericComponent(TITLE_TEXT);
    public final SnCheckbox checkbox = $component(CHECKBOX, SnCheckbox.class);
    public final SnTextbox textbox = $component(TEXTBOX, SnTextbox.class);
}
