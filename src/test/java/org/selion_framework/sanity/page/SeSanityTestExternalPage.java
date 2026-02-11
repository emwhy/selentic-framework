package org.selion_framework.sanity.page;

import org.selion_framework.lib.*;
import org.selion_framework.sanity.component.SeSanityTestTableRow;

public class SeSanityTestExternalPage extends SePage {
    private static final SeLocatorNode SANITYTEST_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("sanitytest-external-textbox"));
    private static final SeLocatorNode SANITYTEST_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("sanitytest-external-table")).descendant("tr", _cssClasses("data"));
    private static final SeLocatorNode SANITYTEST_EXTERNAL_WINDOW_LINK = _xpath.descendant("a", _id().is("sanitytest-external-window-link"));
    private static final SeLocatorNode SANITYTEST_CLOSE_CURRENT_WINDOW_BUTTON = _xpath.descendant("button", _id().is("sanitytest-external-close-window-button"));

    @Override
    protected void additionalWait() {
    }

    public final SeTextbox sanitytestExternalTextbox = $component(SANITYTEST_EXTERNAL_TEXTBOX, SeTextbox.class);

    public final SeComponentCollection<SeSanityTestTableRow> sanitytestExternalTableRows = $$components(SANITYTEST_EXTERNAL_ROWS, SeSanityTestTableRow.class);

    public final SeLink openExternalWindowLink = $component(SANITYTEST_EXTERNAL_WINDOW_LINK, SeLink.class);

    public final SeButton closeCurrentWindowButton = $component(SANITYTEST_CLOSE_CURRENT_WINDOW_BUTTON, SeButton.class);
}
