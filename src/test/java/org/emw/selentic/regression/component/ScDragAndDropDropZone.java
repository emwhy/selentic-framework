package org.emw.selentic.regression.component;

import org.emw.selentic.lib.ScComponent;
import org.emw.selentic.lib.ScComponentRule;
import org.emw.selentic.lib.ScGenericComponent;
import org.emw.selentic.lib.selector.ScCssSelector;

public class ScDragAndDropDropZone extends ScComponent {
    private static final ScCssSelector LABEL_TEXT = _cssSelector.descendant("p");
    private static final ScCssSelector DRAGGED_ITEM = _cssSelector.descendant(_cssClasses("draggable-item"));

    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("div");
        rule.cssClasses().has("drop-zone");
    }

    @Override
    public String text() {
        return $genericComponent(LABEL_TEXT).text();
    }

    public ScGenericComponent draggedItem() {
        return $component(DRAGGED_ITEM, ScGenericComponent.class);
    }
}
