package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.ScComponent;
import org.emwhyware.selentic.lib.ScComponentRule;
import org.emwhyware.selentic.lib.ScCssSelector;
import org.emwhyware.selentic.lib.ScGenericComponent;

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

    public final ScGenericComponent draggedItem = $component(DRAGGED_ITEM, ScGenericComponent.class);
}
