package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.ScTextbox;
import org.emwhyware.selentic.lib.SnComponentCollection;
import org.emwhyware.selentic.lib.ScFrameContent;
import org.emwhyware.selentic.lib.SnXPath;

public class ScSanityTestFrameContent extends ScFrameContent {
    private static final SnXPath TEST_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("test-external-textbox"));
    private static final SnXPath TEST_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("test-external-table")).descendant("tr", _cssClasses("data"));

    @Override
    protected void waitForDisplayed() {
        waitForComponent(testExternalTextbox);
    }

    public final ScTextbox testExternalTextbox = $component(TEST_EXTERNAL_TEXTBOX, ScTextbox.class);
    public final SnComponentCollection<ScTestTableRow> testExternalTableRows = $$components(TEST_EXTERNAL_ROWS, ScTestTableRow.class);
}
