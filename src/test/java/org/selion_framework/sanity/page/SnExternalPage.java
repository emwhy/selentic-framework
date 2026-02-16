package org.selion_framework.sanity.page;

import org.selion_framework.lib.*;
import org.selion_framework.sanity.component.SnTestTableRow;

public class SnExternalPage extends SnPage {
    private static final SnXPath TEST_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("test-external-textbox"));
    private static final SnXPath TEST_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("test-external-table")).descendant("tr", _cssClasses("data"));
    private static final SnXPath TEST_EXTERNAL_WINDOW_LINK = _xpath.descendant("a", _id().is("test-external-window-link"));
    private static final SnXPath TEST_CLOSE_CURRENT_WINDOW_BUTTON = _xpath.descendant("button", _id().is("test-external-close-window-button"));

    @Override
    protected void waitForDisplayed() {
        waitForComponent(testExternalTextbox);
    }

    public final SnTextbox testExternalTextbox = $component(TEST_EXTERNAL_TEXTBOX, SnTextbox.class);

    public final SnComponentCollection<SnTestTableRow> testExternalTableRows = $$components(TEST_EXTERNAL_ROWS, SnTestTableRow.class);

    public final SnLink openExternalWindowLink = $component(TEST_EXTERNAL_WINDOW_LINK, SnLink.class);

    public final SnButton closeCurrentWindowButton = $component(TEST_CLOSE_CURRENT_WINDOW_BUTTON, SnButton.class);
}
