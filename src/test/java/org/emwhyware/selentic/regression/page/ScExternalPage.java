package org.emwhyware.selentic.regression.page;

import org.emwhyware.selentic.lib.*;
import org.emwhyware.selentic.regression.component.ScTestTableRow;

public class ScExternalPage extends ScPage {
    private static final ScXPath TEST_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("test-external-textbox"));
    private static final ScXPath TEST_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("test-external-table")).descendant("tr", _cssClasses("data"));
    private static final ScXPath TEST_EXTERNAL_WINDOW_LINK = _xpath.descendant("a", _id().is("test-external-window-link"));
    private static final ScXPath TEST_CLOSE_CURRENT_WINDOW_BUTTON = _xpath.descendant("button", _id().is("test-external-close-window-button"));

    @Override
    protected void waitForDisplayed() {
        waitForComponent(testExternalTextbox);
    }

    public final ScTextbox testExternalTextbox = $component(TEST_EXTERNAL_TEXTBOX, ScTextbox.class);

    public final ScComponentCollection<ScTestTableRow> testExternalTableRows = $$components(TEST_EXTERNAL_ROWS, ScTestTableRow.class);

    public final ScLink openExternalWindowLink = $component(TEST_EXTERNAL_WINDOW_LINK, ScLink.class);

    public final ScButton closeCurrentWindowButton = $component(TEST_CLOSE_CURRENT_WINDOW_BUTTON, ScButton.class);
}
