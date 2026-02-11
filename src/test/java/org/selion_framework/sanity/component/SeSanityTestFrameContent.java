package org.selion_framework.sanity.component;

import org.selion_framework.lib.SeComponentCollection;
import org.selion_framework.lib.SeFrameContent;
import org.selion_framework.lib.SeLocatorNode;
import org.selion_framework.lib.SeTextbox;

public class SeSanityTestFrameContent extends SeFrameContent {
    private static final SeLocatorNode SANITYTEST_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("sanitytest-external-textbox"));
    private static final SeLocatorNode SANITYTEST_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("sanitytest-external-table")).descendant("tr", _cssClasses("data"));

    @Override
    protected void additionalWait() {
        waitForComponent(sanitytestExternalTextbox);
    }

    public final SeTextbox sanitytestExternalTextbox = $component(SANITYTEST_EXTERNAL_TEXTBOX, SeTextbox.class);
    public final SeComponentCollection<SeSanityTestTableRow> sanitytestExternalTableRows = $$components(SANITYTEST_EXTERNAL_ROWS, SeSanityTestTableRow.class);
}
