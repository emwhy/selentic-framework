package org.selion_framework.lib;

import org.openqa.selenium.interactions.Actions;

public abstract class SnDraggableComponent extends SnClickableComponent {

    public SnDragAction drag() {
        return new SnDragAction(this);
    }

    public class SnDragAction {
        private final SnComponent draggedComponent;

        private SnDragAction(SnComponent draggedComponent) {
            this.draggedComponent = draggedComponent;
        }

        public void to(SnComponent targetComponent) {
            SnDraggableComponent.this.actions()
                    .moveToElement(this.draggedComponent.scrolled())
                    .clickAndHold()
                    .moveToElement(targetComponent.scrolled())
                    .release()
                    .build().perform();
        }

        public void to(SnComponent targetComponent, int offsetX, int offsetY) {
            SnDraggableComponent.this.actions()
                    .moveToElement(this.draggedComponent.scrolled())
                    .clickAndHold()
                    .moveToElement(targetComponent.scrolled(), offsetX, offsetY)
                    .release()
                    .build().perform();
        }
    }
}
