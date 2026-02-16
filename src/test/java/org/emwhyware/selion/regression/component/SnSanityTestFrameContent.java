package org.emwhyware.selion.regression.component;

import org.emwhyware.selion.lib.SnComponentCollection;
import org.emwhyware.selion.lib.SnFrameContent;
import org.emwhyware.selion.lib.SnTextbox;
import org.emwhyware.selion.lib.SnXPath;

public class SnSanityTestFrameContent extends SnFrameContent {
    private static final SnXPath TEST_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("test-external-textbox"));
    private static final SnXPath TEST_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("test-external-table")).descendant("tr", _cssClasses("data"));

    @Override
    protected void waitForDisplayed() {
        waitForComponent(testExternalTextbox);
    }

    public final SnTextbox testExternalTextbox = $component(TEST_EXTERNAL_TEXTBOX, SnTextbox.class);
    public final SnComponentCollection<SnTestTableRow> testExternalTableRows = $$components(TEST_EXTERNAL_ROWS, SnTestTableRow.class);
}
