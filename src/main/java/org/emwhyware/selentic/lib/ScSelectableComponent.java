package org.emwhyware.selentic.lib;

/**
 * An abstract component class representing selectable form elements.
 *
 * <p>This class extends {@link ScFormComponent} and provides functionality for interacting with
 * selectable elements such as checkboxes and radio buttons. It includes methods for checking
 * selection state, selecting elements, and retrieving element labels.
 *
 * @see ScFormComponent
 * @see ScCheckbox
 * @see ScRadioButton
 * @see SnRadioButtonGroup
 */
public abstract class ScSelectableComponent extends ScFormComponent {

    /**
     * Retrieves the text content associated with this selectable element.
     *
     * @return the text content associated with this selectable element, either from an associated
     *         {@code <label>} element or from the element's {@code value} attribute
     * @see ScComponent#key()
     */
    @Override
    public String text() {
        final ScGenericComponent $parentComponent = $component(_xpath.parent(), ScGenericComponent.class);

        if ($parentComponent.tag().equals("label")) {
            return $parentComponent.text();
        } else if (this.id().isPresent()){
            final ScGenericComponent $label = $component(_xpath.page("label", _attr("for").is(this.id().get())), ScGenericComponent.class);

            if ($label.exists()) {
                return $label.text();
            }
        }
        return this.value();
    }

    /**
     * Checks whether this selectable element is currently selected.
     *
     * <p>This method queries the underlying Selenium element to determine its selection state.
     * It is applicable to elements such as checkboxes and radio buttons.
     *
     * <p><strong>Example:</strong>
     * <pre>{@code
     * if (checkbox.isSelected()) {
     *     System.out.println("Checkbox is checked");
     * } else {
     *     checkbox.select();  // Select it if not already selected
     * }
     * }</pre>
     *
     * @return {@code true} if the element is currently selected; {@code false} otherwise
     */
    public boolean isSelected() {
        return this.existingElement().isSelected();
    }

    /**
     * Selects this element if it is not already selected.
     *
     * <p>This method performs a smart selection operation:
     * <ol>
     *   <li>First checks if the element is already selected via {@link #isSelected()}.</li>
     *   <li>If not selected, scrolls the element into view and clicks it to select it.</li>
     *   <li>If already selected, performs no action (idempotent behavior).</li>
     * </ol>
     *
     * <p>The automatic scrolling ensures that the element is visible on the screen before attempting
     * to click it, which helps prevent issues with elements that are not in the viewport.
     *
     * <p><strong>Example:</strong>
     * <pre>{@code
     * checkbox.select();  // Selects the checkbox if not already selected
     *
     * checkbox.select();  // Second call does nothing (already selected)
     * }</pre>
     *
     * @see #isSelected()
     * @see ScFormComponent#scrolledElement()
     */
    public void select() {
        if (!this.isSelected()) {
            this.enabledElement().click();
        }
    }

}