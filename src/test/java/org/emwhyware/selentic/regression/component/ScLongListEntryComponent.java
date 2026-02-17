package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.*;

public class ScLongListEntryComponent extends ScComponent {
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

    public final ScGenericComponent titleText = $genericComponent(TITLE_TEXT);
    public final ScCheckbox checkbox = $component(CHECKBOX, ScCheckbox.class);
    public final ScTextbox textbox = $component(TEXTBOX, ScTextbox.class);
}
