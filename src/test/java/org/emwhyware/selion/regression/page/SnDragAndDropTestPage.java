package org.emwhyware.selion.regression.page;

import org.emwhyware.selion.lib.SnCssSelector;
import org.emwhyware.selion.lib.SnPage;
import org.emwhyware.selion.regression.component.SnDragAndDropDropZone;
import org.emwhyware.selion.regression.component.SnDraggedItem;

public class SnDragAndDropTestPage extends SnPage {
    private static final SnCssSelector DRAGGED_ITEM = _cssSelector.descendant(_id("dragged-item"));
    private static final SnCssSelector DROP_ZONE1 = _cssSelector.descendant(_id("target-zone1"));
    private static final SnCssSelector DROP_ZONE2 = _cssSelector.descendant(_id("target-zone2"));

    public final SnDraggedItem draggedItem = $component(DRAGGED_ITEM, SnDraggedItem.class);
    public final SnDragAndDropDropZone dropZone1 = $component(DROP_ZONE1, SnDragAndDropDropZone.class);
    public final SnDragAndDropDropZone dropZone2 = $component(DROP_ZONE2, SnDragAndDropDropZone.class);
}
