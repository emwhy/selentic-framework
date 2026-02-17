package org.emwhyware.selion.lib;

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
                    .moveToElement(this.draggedComponent.scrolledElement())
                    .clickAndHold()
                    .moveToElement(targetComponent.scrolledElement())
                    .release()
                    .build().perform();
        }

        public void to(SnComponent targetComponent, int offsetX, int offsetY) {
            SnDraggableComponent.this.actions()
                    .moveToElement(this.draggedComponent.scrolledElement())
                    .clickAndHold()
                    .moveToElement(targetComponent.scrolledElement(), offsetX, offsetY)
                    .release()
                    .build().perform();
        }
    }
}
