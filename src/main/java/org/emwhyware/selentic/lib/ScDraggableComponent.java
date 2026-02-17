package org.emwhyware.selentic.lib;

public abstract class ScDraggableComponent extends ScClickableComponent {

    public ScDragAction drag() {
        return new ScDragAction(this);
    }

    public class ScDragAction {
        private final ScComponent draggedComponent;

        private ScDragAction(ScComponent draggedComponent) {
            this.draggedComponent = draggedComponent;
        }

        public void to(ScComponent targetComponent) {
            ScDraggableComponent.this.actions()
                    .moveToElement(this.draggedComponent.scrolledElement())
                    .clickAndHold()
                    .moveToElement(targetComponent.scrolledElement())
                    .release()
                    .build().perform();
        }

        public void to(ScComponent targetComponent, int offsetX, int offsetY) {
            ScDraggableComponent.this.actions()
                    .moveToElement(this.draggedComponent.scrolledElement())
                    .clickAndHold()
                    .moveToElement(targetComponent.scrolledElement(), offsetX, offsetY)
                    .release()
                    .build().perform();
        }
    }
}
