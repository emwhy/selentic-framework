package org.emwhyware.selion.regression.component;

import org.emwhyware.selion.lib.*;
import org.emwhyware.selion.lib.util.SnWait;

public class SnSlimSelectDropdown extends SnComponent {
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

    private final SnGenericComponent selectedText = $genericComponent(SELECTED_TEXT);
    private final SnArrowButton arrowButton = $component(ARROW_BUTTON, SnArrowButton.class, this);
    private final SnGenericComponent contentPanel = $genericComponent(CONTENT_PANEL);
    private final SnComponentCollection<SnListItem> listItems = $$components(LIST_ITEMS, SnListItem.class, this);

    @Override
    public String text() {
        return selectedText();
    }

    public String selectedText() {
        return selectedText.isDisplayed() ? selectedText.text() : "";
    }

    public void select(String text) {
        arrowButton.click();
        SnWait.waitUntil(() -> contentPanel.isDisplayed());
        listItems.entry(text).click();
        SnWait.waitUntil(() -> !contentPanel.isDisplayed());
    }

    /*
        Inner classes.
     */

    public class SnArrowButton extends SnClickableComponent {
        @Override
        protected void rules(SnComponentRule rule) {
            rule.tag().is("svg");
            rule.cssClasses().has("ss-arrow");
        }
    }

    public class SnListItem extends SnClickableComponent {
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

