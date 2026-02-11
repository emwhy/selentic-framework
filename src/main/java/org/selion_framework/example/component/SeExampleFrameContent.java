package org.selion_framework.example.component;

import org.selion_framework.lib.SeComponentCollection;
import org.selion_framework.lib.SeFrameContent;
import org.selion_framework.lib.SeLocatorNode;
import org.selion_framework.lib.SeTextbox;

public class SeExampleFrameContent extends SeFrameContent {
    private static final SeLocatorNode EXAMPLE_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("example-external-textbox"));
    private static final SeLocatorNode EXAMPLE_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("example-external-table")).descendant("tr", _cssClasses("data"));

    @Override
    protected void additionalWait() {
        waitForComponent(exampleExternalTextbox);
    }

    public final SeTextbox exampleExternalTextbox = $component(EXAMPLE_EXTERNAL_TEXTBOX, SeTextbox.class);
    public final SeComponentCollection<SeExampleTableRow> exampleExternalTableRows = $$components(EXAMPLE_EXTERNAL_ROWS, SeExampleTableRow.class);
}
