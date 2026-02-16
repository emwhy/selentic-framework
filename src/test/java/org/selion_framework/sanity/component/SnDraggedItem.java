package org.selion_framework.sanity.component;

import org.selion_framework.lib.SnComponentRule;
import org.selion_framework.lib.SnDraggableComponent;

public class SnDraggedItem extends SnDraggableComponent {
    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("div");
        rule.cssClasses().has("draggable-item");
        rule.attr("draggable").is("true");
    }
}
