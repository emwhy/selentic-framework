package org.emwhyware.selentic.regression.page;

import org.emwhyware.selentic.lib.ScPage;
import org.emwhyware.selentic.lib.selector.ScCssSelector;
import org.emwhyware.selentic.regression.component.ScDragAndDropDropZone;
import org.emwhyware.selentic.regression.component.ScDraggedItem;

public class ScDragAndDropTestPage extends ScPage {
    private static final ScCssSelector DRAGGED_ITEM = _cssSelector.descendant(_id("dragged-item"));
    private static final ScCssSelector DROP_ZONE1 = _cssSelector.descendant(_id("target-zone1"));
    private static final ScCssSelector DROP_ZONE2 = _cssSelector.descendant(_id("target-zone2"));

    public ScDraggedItem draggedItem() {
        return $component(DRAGGED_ITEM, ScDraggedItem.class);
    }

    public ScDragAndDropDropZone dropZone1() {
        return $component(DROP_ZONE1, ScDragAndDropDropZone.class);
    }

    public ScDragAndDropDropZone dropZone2() {
        return $component(DROP_ZONE2, ScDragAndDropDropZone.class);
    }
}
