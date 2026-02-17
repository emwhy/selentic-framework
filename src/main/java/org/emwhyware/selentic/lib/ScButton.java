package org.emwhyware.selentic.lib;

/**
 * {@code SnButton} is a component class that represents button elements in web pages.
 *
 * <p>
 * This class handles both HTML button elements ({@code <button>}) and input elements with button-like types
 * ({@code <input type="button">}, {@code <input type="submit">}, {@code <input type="reset">}).
 * It provides a unified interface for interacting with buttons regardless of their underlying HTML structure.
 * 
 *
 * <h2>Supported Button Types</h2>
 * <p>
 * This component recognizes and validates the following button implementations:
 * 
 * <ul>
 *   <li><strong>{@code <button>}</strong> - Standard HTML button elements</li>
 *   <li><strong>{@code <input type="button">}</strong> - Generic input button</li>
 *   <li><strong>{@code <input type="submit">}</strong> - Form submission button</li>
 *   <li><strong>{@code <input type="reset">}</strong> - Form reset button</li>
 * </ul>
 *
 * <h2>HTML Examples</h2>
 * <pre>{@code
 * <!-- Standard button element -->
 * <button type="button" id="submit-btn">Submit Form</button>
 *
 * <!-- Input button element -->
 * <input type="submit" value="Send" />
 *
 * <!-- Reset button -->
 * <input type="reset" value="Clear Form" />
 *
 * <!-- Generic input button -->
 * <input type="button" value="Click Me" onclick="handleClick()" />
 * }</pre>
 *
 * <h2>Usage Examples</h2>
 * <pre>{@code
 * // Define button components in a page class
 * public class LoginPage extends SnPage {
 *     private static final SnCssSelector SUBMIT_BUTTON = _cssSelector.descendant(_id("submit-btn"));
 *     private static final SnCssSelector RESET_BUTTON = _cssSelector.descendant(_id("reset-btn"));
 *
 *     public final SnButton resetButton = $component(RESET_BUTTON, SnButton.class);
 *
 *     // Shorthand.
 *     public final SnButton submitButton = $button(SUBMIT_BUTTON);
 * }
 *
 * // In test code
 * final SnWithPage<LoginPage> loginPage = SnPage.with(LoginPage.class);
 *
 * loginPage.inPage(p -> {
 *      // Click a button
 *      p.submitButton.click();
 *
 *      // Get button text/label
 *      String buttonLabel = p.submitButton.text();
 *      System.out.println("Button: " + buttonLabel);
 * });
 * }</pre>
 *
 * <h2>Component Rules</h2>
 * <p>
 * The {@link #rules(ScComponentRule)} method validates that an element is a valid button by checking:
 * <ul>
 *   <li>The tag name is either "button" or "input"</li>
 *   <li>If it's an "input" element, the type attribute must be one of: "button", "submit", or "reset"</li>
 * </ul>
 *
 * @see ScFormComponent
 * @see ScComponent
 * @see ScComponentRule
 */
public class ScButton extends ScFormComponent {
    /**
     * Defines validation rules for button elements.
     *
     * <p>
     * This method validates that the component wraps a valid button element. Buttons can be implemented as:
     * <ul>
     *   <li>HTML {@code <button>} elements</li>
     *   <li>HTML {@code <input>} elements with type "button", "submit", or "reset"</li>
     * </ul>
     * 
     *
     * <p>
     * <strong>Validation Logic:</strong>
     * <ol>
     *   <li>Checks that the element's tag name is either "input" or "button"</li>
     *   <li>If it's an input element, additionally verifies the type is one of: "button", "submit", or "reset"</li>
     *   <li>For button elements, no additional type validation is required</li>
     * </ol>
     * 
     *
     * <p>
     * <strong>Implementation Example:</strong>
     * <pre>{@code
     * // These elements will pass validation:
     * <button type="button">Click Me</button>                    // Valid button
     * <input type="submit" value="Submit" />                     // Valid input
     * <input type="reset" value="Clear" />                       // Valid input
     * <input type="button" value="Action" />                     // Valid input
     *
     * // These elements will fail validation:
     * <input type="text" value="Text Field" />                   // Invalid - wrong input type
     * <span class="button">Click Me</span>                       // Invalid - not a button element
     * }</pre>
     * 
     *
     * @param rule the {@link ScComponentRule} object used to define and verify button validation rules
     * @see ScComponentRule#tag()
     * @see ScComponentRule#type()
     */
    @Override
    protected void rules(ScComponentRule rule) {
        // Needs to do extra IF because button can be both input and button.
        rule.tag().isOneOf("input", "button");
        if (this.tag().equals("input")) {
            rule.type().isOneOf("button", "submit", "reset");
        }
    }

    /**
     * Returns the text or value representation of this button.
     *
     * <p>
     * This method returns the user-visible label of the button, handling both {@code <button>} and
     * {@code <input>} element implementations appropriately.
     * 
     *
     * @return the text content for {@code <button>} elements or the value attribute for {@code <input>}
     *         button elements
     * @see #value()
     * @see ScComponent#text()
     */
    @Override
    public String text() {
        if (this.tag().equals("button")) {
            return super.text();
        } else {
            return this.value();
        }
    }
}