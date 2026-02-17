package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.*;
import org.emwhyware.selentic.lib.util.ScWait;

public class ScSlimSelectDropdown extends ScComponent {
    private static final SnCssSelector ARROW_BUTTON = _cssSelector.descendant(_tag("svg"), _cssClasses("ss-arrow"));
    private static final SnCssSelector SELECTED_TEXT = _cssSelector.descendant(_tag("div"), _cssClasses("ss-single"));
    private static final SnCssSelector CONTENT_PANEL = _cssSelector.page(_tag("div"), _cssClasses("ss-content", "ss-open"));
    private static final SnCssSelector LIST_ITEMS = CONTENT_PANEL.descendant(_tag("div"), _cssClasses("ss-option"));

    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("div");
        rule.attr("aria-label").is("Combobox");
        rule.attr("role").is("combobox");
        rule.attr("aria-controls").isPresent();
    }

    private final ScGenericComponent selectedText = $genericComponent(SELECTED_TEXT);
    private final ScArrowButton arrowButton = $component(ARROW_BUTTON, ScArrowButton.class, this);
    private final ScGenericComponent contentPanel = $genericComponent(CONTENT_PANEL);
    private final SnComponentCollection<ScListItem> listItems = $$components(LIST_ITEMS, ScListItem.class, this);

    @Override
    public String text() {
        return selectedText();
    }

    public String selectedText() {
        return selectedText.isDisplayed() ? selectedText.text() : "";
    }

    public void select(String text) {
        arrowButton.click();
        ScWait.waitUntil(() -> contentPanel.isDisplayed());
        listItems.entry(text).click();
        ScWait.waitUntil(() -> !contentPanel.isDisplayed());
    }

    /*
        Inner classes.
     */

    public class ScArrowButton extends ScClickableComponent {
        @Override
        protected void rules(SnComponentRule rule) {
            rule.tag().is("svg");
            rule.cssClasses().has("ss-arrow");
        }
    }

    public class ScListItem extends ScClickableComponent {
        @Override
        protected void rules(SnComponentRule rule) {
            rule.tag().is("div");
            rule.cssClasses().has("ss-option");
        }

        public boolean isSelected() {
            return this.cssClasses().contains("ss-selected");
        }
    }
}

