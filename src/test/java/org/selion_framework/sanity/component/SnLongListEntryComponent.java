package org.selion_framework.sanity.component;

import org.selion_framework.lib.*;

public class SnLongListEntryComponent extends SnComponent {
    private static final SnCssSelector TITLE_TEXT = _cssSelector.descendant(_cssClasses("title"));
    private static final SnCssSelector CHECKBOX = _cssSelector.descendant(_type().is("checkbox"));
    private static final SnCssSelector TEXTBOX = _cssSelector.descendant(_type().is("text"));

    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("div");
        rule.cssClasses().has("long-component-list-entry");
    }

    @Override
    public String text() {
        return titleText.text();
    }

    public final SnGenericComponent titleText = $genericComponent(TITLE_TEXT);
    public final SnCheckbox checkbox = $component(CHECKBOX, SnCheckbox.class);
    public final SnTextbox textbox = $component(TEXTBOX, SnTextbox.class);
}
