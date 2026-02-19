package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.*;
import org.emwhyware.selentic.lib.util.ScWait;

public class ScSlimSelectDropdown extends ScComponent {
    private static final ScCssSelector ARROW_BUTTON = _cssSelector.descendant("svg", _cssClasses("ss-arrow"));
    private static final ScCssSelector SELECTED_TEXT = _cssSelector.descendant("div", _cssClasses("ss-single"));
    private static final ScCssSelector CONTENT_PANEL = _cssSelector.page("div", _cssClasses("ss-content", "ss-open"));
    private static final ScCssSelector LIST_ITEMS = CONTENT_PANEL.descendant("div", _cssClasses("ss-option"));

    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("div");
        rule.attr("aria-label").is("Combobox");
        rule.attr("role").is("combobox");
        rule.attr("aria-controls").isPresent();
    }

    private ScGenericComponent arrowButton() {
        return $component(ARROW_BUTTON, ScGenericComponent.class);
    }

    private ScGenericComponent contentPanel() {
        return $genericComponent(CONTENT_PANEL);
    }

    private ScComponentCollection<ScGenericComponent> listItems() {
        return $$components(LIST_ITEMS, ScGenericComponent.class);
    }

    @Override
    public String text() {
        return selectedText();
    }

    public String selectedText() {
        final ScGenericComponent selectedText = $genericComponent(SELECTED_TEXT);

        return selectedText.isDisplayed() ? selectedText.text() : "";
    }

    public void select(String text) {
        clickGenericComponent(arrowButton());
        waitForComponent(contentPanel(), ScWaitCondition.ToBeDisplayed);
        clickGenericComponent(listItems().entry(text));
        waitForComponent(contentPanel(), ScWaitCondition.ToBeHidden);
    }
}

