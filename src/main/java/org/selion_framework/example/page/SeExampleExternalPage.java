package org.selion_framework.example.page;

import org.selion_framework.example.component.SeExampleTableRow;
import org.selion_framework.lib.*;

public class SeExampleExternalPage extends SePage {
    private static final SeLocatorNode EXAMPLE_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("example-external-textbox"));
    private static final SeLocatorNode EXAMPLE_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("example-external-table")).descendant("tr", _cssClasses("data"));
    private static final SeLocatorNode EXAMPLE_EXTERNAL_WINDOW_LINK = _xpath.descendant("a", _id().is("example-external-window-link"));
    private static final SeLocatorNode EXAMPLE_CLOSE_CURRENT_WINDOW_BUTTON = _xpath.descendant("button", _id().is("example-external-close-window-button"));

    @Override
    protected void additionalWait() {
    }

    public final SeTextbox exampleExternalTextbox = $component(EXAMPLE_EXTERNAL_TEXTBOX, SeTextbox.class);

    public final SeComponentCollection<SeExampleTableRow> exampleExternalTableRows = $$components(EXAMPLE_EXTERNAL_ROWS, SeExampleTableRow.class);

    public final SeLink openExternalWindowLink = $component(EXAMPLE_EXTERNAL_WINDOW_LINK, SeLink.class);

    public final SeButton closeCurrentWindowButton = $component(EXAMPLE_CLOSE_CURRENT_WINDOW_BUTTON, SeButton.class);
}
