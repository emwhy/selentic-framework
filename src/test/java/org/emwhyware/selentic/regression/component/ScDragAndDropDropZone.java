package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.ScComponent;
import org.emwhyware.selentic.lib.ScGenericComponent;
import org.emwhyware.selentic.lib.SnComponentRule;
import org.emwhyware.selentic.lib.SnCssSelector;

public class ScDragAndDropDropZone extends ScComponent {
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

    public final ScGenericComponent draggedItem = $component(DRAGGED_ITEM, ScGenericComponent.class);
}
