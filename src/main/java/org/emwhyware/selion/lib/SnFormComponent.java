package org.emwhyware.selion.lib;

/**
 * Abstract base class for web form components.
 * <p>
 * The {@code SnFormComponent} extends {@link SnClickableComponent} and provides foundational
 * functionality for interactive form elements such as text inputs, text areas, select dropdowns,
 * checkboxes, and radio buttons. It combines the click interaction capabilities inherited from
 * {@link SnClickableComponent} with form-specific operations.
 * 
 *
 * <h2>Inheritance Hierarchy:</h2>
 * <pre>
 * SnComponent (base component)
 *     ↓
 * SnClickableComponent (clickable interactions)
 *     ↓
 * SnFormComponent (form element support)
 *     ↓
 * Concrete implementations (SnTextbox, SnDropdown, etc.)
 * </pre>
 *
 * @see SnClickableComponent
 * @see SnComponent
 */
public abstract class SnFormComponent extends SnClickableComponent {

    /**
     * Retrieves the current value of this form component.
     * <p>
     * This method retrieves the value of the "value".
     * For most standard HTML form elements (input, textarea, etc.), this returns the current
     * value that would be submitted with a form.
     * 
     *
     * @return the value of the "value" attribute, or empty string if the attribute is not present
     * @see org.openqa.selenium.WebElement#getAttribute(String)
     */
    protected String value() {
        String value;

        return (value = this.existingElement().getAttribute("value")) == null ? "" : value;
    }
}