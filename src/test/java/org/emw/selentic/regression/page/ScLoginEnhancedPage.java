package org.emw.selentic.regression.page;

import org.emw.selentic.lib.ScButton;
import org.emw.selentic.lib.ScPage;
import org.emw.selentic.lib.ScTextbox;
import org.emw.selentic.lib.selector.ScCssSelector;
import org.emw.selentic.regression.component.ScSlimSelectDropdown;

public class ScLoginEnhancedPage extends ScPage {
    private static final ScCssSelector USERNAME_TEXTBOX = _cssSelector.descendant(_id("username"));
    private static final ScCssSelector PASSWORD_TEXTBOX = _cssSelector.descendant(_id("password"));
    private static final ScCssSelector ACCOUNT_TYPE_DROPDOWN = _cssSelector.descendant("select", _id("user-role")).nextSibling(_tag("div"), _attr("role").is("combobox"));
    private static final ScCssSelector LOGIN_BUTTON = _cssSelector.descendant("button", _type().is("submit"));

    @Override
    protected void waitForDisplayedPage() {
        waitForComponent(userNameTextbox(), ScWaitCondition.ToBeDisplayed);
    }

    public ScTextbox userNameTextbox() {
        return $textbox(USERNAME_TEXTBOX);
    }

    public ScTextbox passwordTextbox() {
        return $textbox(PASSWORD_TEXTBOX);
    }

    public ScSlimSelectDropdown accountTypeDropdown() {
        return $component(ACCOUNT_TYPE_DROPDOWN, ScSlimSelectDropdown.class);
    }

    public ScButton loginButton() {
        return $button(LOGIN_BUTTON);
    }
}