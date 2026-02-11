package org.selion_framework.example.component;

import org.selion_framework.lib.*;

public class SeExampleLongListEntryComponent extends SeComponent {
    private static final SeLocatorNode TITLE_TEXT = _xpath.descendant("span", _cssClasses("title"));
    private static final SeLocatorNode CHECKBOX = _xpath.descendant("input", _type().is("checkbox"));
    private static final SeLocatorNode TEXTBOX = _xpath.descendant("input", _type().is("text"));

    @Override
    protected void rules(SeComponentRule rule) {
        rule.tag().is("div");
        rule.cssClasses().has("long-component-list-entry");
    }

    @Override
    public String key() {
        return titleText.key();
    }

    public final SeGenericComponent titleText = $genericComponent(TITLE_TEXT);
    public final SeCheckbox checkbox = $component(CHECKBOX, SeCheckbox.class);
    public final SeTextbox textbox = $component(TEXTBOX, SeTextbox.class);
}
