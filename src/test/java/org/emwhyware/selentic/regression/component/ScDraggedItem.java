package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.ScComponentRule;
import org.emwhyware.selentic.lib.ScDraggableComponent;

public class ScDraggedItem extends ScDraggableComponent {
    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("div");
        rule.cssClasses().has("draggable-item");
        rule.attr("draggable").is("true");
    }
}
