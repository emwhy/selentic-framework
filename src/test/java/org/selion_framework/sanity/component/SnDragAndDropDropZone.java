package org.selion_framework.sanity.component;

import org.selion_framework.lib.SnComponent;
import org.selion_framework.lib.SnComponentRule;
import org.selion_framework.lib.SnCssSelector;
import org.selion_framework.lib.SnGenericComponent;

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
