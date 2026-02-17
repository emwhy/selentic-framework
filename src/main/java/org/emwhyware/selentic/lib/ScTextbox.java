package org.emwhyware.selentic.lib;

import org.openqa.selenium.WebElement;

public class ScTextbox extends ScFormComponent {
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
        final WebElement scrolled = this.scrolledElement();

        scrolled.clear();
        scrolled.click();
        scrolled.sendKeys(text);
    }
}
