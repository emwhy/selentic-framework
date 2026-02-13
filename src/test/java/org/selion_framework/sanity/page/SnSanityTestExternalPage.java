package org.selion_framework.sanity.page;

import org.selion_framework.lib.*;
import org.selion_framework.sanity.component.SnSanityTestTableRow;

public class SnSanityTestExternalPage extends SnPage {
    private static final SnXPath SANITYTEST_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("sanitytest-external-textbox"));
    private static final SnXPath SANITYTEST_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("sanitytest-external-table")).descendant("tr", _cssClasses("data"));
    private static final SnXPath SANITYTEST_EXTERNAL_WINDOW_LINK = _xpath.descendant("a", _id().is("sanitytest-external-window-link"));
    private static final SnXPath SANITYTEST_CLOSE_CURRENT_WINDOW_BUTTON = _xpath.descendant("button", _id().is("sanitytest-external-close-window-button"));

    @Override
    protected void additionalWait() {
    }

    public final SnTextbox sanitytestExternalTextbox = $component(SANITYTEST_EXTERNAL_TEXTBOX, SnTextbox.class);

    public final SnComponentCollection<SnSanityTestTableRow> sanitytestExternalTableRows = $$components(SANITYTEST_EXTERNAL_ROWS, SnSanityTestTableRow.class);

    public final SnLink openExternalWindowLink = $component(SANITYTEST_EXTERNAL_WINDOW_LINK, SnLink.class);

    public final SnButton closeCurrentWindowButton = $component(SANITYTEST_CLOSE_CURRENT_WINDOW_BUTTON, SnButton.class);
}
