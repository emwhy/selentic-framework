package org.selion_framework.sanity.component;

import org.selion_framework.lib.SnComponentCollection;
import org.selion_framework.lib.SnFrameContent;
import org.selion_framework.lib.SnXPath;
import org.selion_framework.lib.SnTextbox;

public class SnSanityTestFrameContent extends SnFrameContent {
    private static final SnXPath SANITYTEST_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("sanitytest-external-textbox"));
    private static final SnXPath SANITYTEST_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("sanitytest-external-table")).descendant("tr", _cssClasses("data"));

    @Override
    protected void waitForDisplayed() {
        waitForComponent(sanitytestExternalTextbox);
    }

    public final SnTextbox sanitytestExternalTextbox = $component(SANITYTEST_EXTERNAL_TEXTBOX, SnTextbox.class);
    public final SnComponentCollection<SnSanityTestTableRow> sanitytestExternalTableRows = $$components(SANITYTEST_EXTERNAL_ROWS, SnSanityTestTableRow.class);
}
