package org.emwhyware.selion.regression.page;

import org.emwhyware.selion.lib.SnButton;
import org.emwhyware.selion.lib.SnCssSelector;
import org.emwhyware.selion.lib.SnPage;
import org.emwhyware.selion.lib.SnTextbox;
import org.emwhyware.selion.regression.component.SnSlimSelectDropdown;

public class SnLoginEnhancedPage extends SnPage {
    private static final SnCssSelector USERNAME_TEXTBOX = _cssSelector.descendant(_id("username"));
    private static final SnCssSelector PASSWORD_TEXTBOX = _cssSelector.descendant(_id("password"));
    private static final SnCssSelector ACCOUNT_TYPE_DROPDOWN = _cssSelector.descendant(_tag("select"), _id("user-role")).nextSibling(_tag("div"), _attr("role").is("combobox"));
    private static final SnCssSelector LOGIN_BUTTON = _cssSelector.descendant(_tag("button"), _type().is("submit"));

    @Override
    protected void waitForDisplayed() {
        waitForComponent(userNameTextbox);
    }

    public final SnTextbox userNameTextbox = $textbox(USERNAME_TEXTBOX);
    public final SnTextbox passwordTextbox = $textbox(PASSWORD_TEXTBOX);
    public final SnSlimSelectDropdown accountTypeDropdown = $component(ACCOUNT_TYPE_DROPDOWN, SnSlimSelectDropdown.class);
    public final SnButton loginButton = $button(LOGIN_BUTTON);
}
