package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.ScComponentCollection;
import org.emwhyware.selentic.lib.ScFrameContent;
import org.emwhyware.selentic.lib.ScTextbox;
import org.emwhyware.selentic.lib.selector.ScXPath;

public class ScTestFrameContent extends ScFrameContent {
    private static final ScXPath TEST_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("test-external-textbox"));
    private static final ScXPath TEST_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("test-external-table")).descendant("tr", _cssClasses("data"));

    @Override
    protected void waitForDisplayedPage() {
        waitForComponent(testExternalTextbox(), ScWaitCondition.ToBeDisplayed);
    }

    public ScTextbox testExternalTextbox() {
        return $component(TEST_EXTERNAL_TEXTBOX, ScTextbox.class);
    }

    public ScComponentCollection<ScTestTableRow> testExternalTableRows() {
        return $$components(TEST_EXTERNAL_ROWS, ScTestTableRow.class);
    }
}
