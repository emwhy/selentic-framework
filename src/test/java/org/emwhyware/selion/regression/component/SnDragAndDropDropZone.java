package org.emwhyware.selion.regression.component;

import org.emwhyware.selion.lib.SnComponent;
import org.emwhyware.selion.lib.SnComponentRule;
import org.emwhyware.selion.lib.SnCssSelector;
import org.emwhyware.selion.lib.SnGenericComponent;

public class SnDragAndDropDropZone extends SnComponent {
    private static final SnCssSelector LABEL_TEXT = _cssSelector.descendant(_tag("p"));
    private static final SnCssSelector DRAGGED_ITEM = _cssSelector.descendant(_cssClasses("draggable-item"));

    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("div");
        rule.cssClasses().has("drop-zone");
    }

    @Override
    public String text() {
        return $genericComponent(LABEL_TEXT).text();
    }

    public final SnGenericComponent draggedItem = $component(DRAGGED_ITEM, SnGenericComponent.class);
}
