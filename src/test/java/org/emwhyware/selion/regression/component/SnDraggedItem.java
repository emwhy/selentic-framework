package org.emwhyware.selion.regression.component;

import org.emwhyware.selion.lib.SnComponentRule;
import org.emwhyware.selion.lib.SnDraggableComponent;

public class SnDraggedItem extends SnDraggableComponent {
    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("div");
        rule.cssClasses().has("draggable-item");
        rule.attr("draggable").is("true");
    }
}
