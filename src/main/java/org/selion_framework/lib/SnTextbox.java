package org.selion_framework.lib;

import org.openqa.selenium.WebElement;

public class SnTextbox extends SnFormComponent {
    @Override
    protected void rules(SnComponentRule rule) {
        // Needs to do extra IF because textbox can be both input and textarea.
        rule.tag().isOneOf("input", "textarea");
        if (this.tag().equals("input")) {
            rule.type().isOneOf("text", "password", "email", "tel", "search", "number", "hidden", "number", "url");
        }
    }

    @Override
    public String text() {
        return this.value();
    }

    public void enterText(CharSequence... text) {
        final WebElement scrolled = this.scrolled();

        scrolled.clear();
        scrolled.click();
        scrolled.sendKeys(text);
    }
}
