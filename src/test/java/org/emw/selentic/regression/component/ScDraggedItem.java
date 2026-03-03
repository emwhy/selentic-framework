package org.emw.selentic.regression.component;

import org.emw.selentic.lib.ScComponentRule;
import org.emw.selentic.lib.ScDraggableComponent;

public class ScDraggedItem extends ScDraggableComponent {
    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("div");
        rule.cssClasses().has("draggable-item");
        rule.attr("draggable").is("true");
    }
}
