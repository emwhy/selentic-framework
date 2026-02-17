package org.emwhyware.selentic.regression.page;

import org.emwhyware.selentic.lib.*;
import org.emwhyware.selentic.lib.ScPage;
import org.emwhyware.selentic.lib.ScTextbox;
import org.emwhyware.selentic.regression.component.ScSlimSelectDropdown;

public class ScLoginEnhancedPage extends ScPage {
    private static final SnCssSelector USERNAME_TEXTBOX = _cssSelector.descendant(_id("username"));
    private static final SnCssSelector PASSWORD_TEXTBOX = _cssSelector.descendant(_id("password"));
    private static final SnCssSelector ACCOUNT_TYPE_DROPDOWN = _cssSelector.descendant(_tag("select"), _id("user-role")).nextSibling(_tag("div"), _attr("role").is("combobox"));
    private static final SnCssSelector LOGIN_BUTTON = _cssSelector.descendant(_tag("button"), _type().is("submit"));

    @Override
    protected void waitForDisplayed() {
        waitForComponent(userNameTextbox);
    }

    public final ScTextbox userNameTextbox = $textbox(USERNAME_TEXTBOX);
    public final ScTextbox passwordTextbox = $textbox(PASSWORD_TEXTBOX);
    public final ScSlimSelectDropdown accountTypeDropdown = $component(ACCOUNT_TYPE_DROPDOWN, ScSlimSelectDropdown.class);
    public final ScButton loginButton = $button(LOGIN_BUTTON);
}
