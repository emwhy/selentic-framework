package org.emwhyware.selion.lib;

/**
 * {@code SnCheckbox} is a component class that represents checkbox input elements in web pages.
 *
 * <p>
 * This class provides a specialized interface for interacting with HTML checkbox elements ({@code <input type="checkbox">}).
 * It extends {@link SnSelectableComponent} to inherit selection state checking and selection functionality,
 * and adds the ability to deselect checkboxes.
 * 
 *
 * <h2>Supported Element Type</h2>
 * <p>
 * This component exclusively handles HTML checkbox elements:
 * 
 * <ul>
 *   <li><strong>{@code <input type="checkbox">}</strong> - Standard HTML checkbox input</li>
 * </ul>
 *
 * <h2>HTML Examples</h2>
 * <pre>{@code
 * <!-- Standard checkbox with label as parent -->
 * <label>
 *     <input type="checkbox" id="agree" />
 *     I agree to the terms
 * </label>
 *
 * <!-- Checkbox with associated label -->
 * <input type="checkbox" id="newsletter" />
 * <label for="newsletter">Subscribe to newsletter</label>
 *
 * <!-- Checkbox with custom attributes -->
 * <input type="checkbox" id="remember" value="true" data-test="remember-me" />
 *
 * <!-- Checked checkbox (pre-selected) -->
 * <input type="checkbox" id="default-checked" checked="checked" />
 * }</pre>
 *
 * <h2>Usage Examples</h2>
 * <pre>{@code
 * // Define checkbox components in a page class
 * public class SettingsPage extends SnPage {
 *     private static final SnCssSelector AGREE_CHECKBOX = _cssSelector.descendant(_id("agree"));
 *     private static final SnCssSelector NEWSLETTER_CHECKBOX = _cssSelector.descendant(_id("newsletter"));
 *     private static final SnCssSelector REMEMBER_CHECKBOX = _cssSelector.descendant(_id("remember"));
 *
 *     public final SnCheckbox agreeCheckbox = $component(AGREE_CHECKBOX, SnCheckbox.class);
 *     public final SnCheckbox newsletterCheckbox = $component(NEWSLETTER_CHECKBOX, SnCheckbox.class);
 *
 *     // Shorthand.
 *     public final SnCheckbox rememberCheckbox = $checkbox(REMEMBER_CHECKBOX;
 * }
 *
 * // In test code
 * final final SnWithPage<SettingsPage> settingsPage = SnPage.with(SettingsPage.class);
 *
 * settingsPage.inPage(p -> {
 *      // Select a checkbox
 *      p.agreeCheckbox.select();
 *
 *      // Check if checkbox is selected
 *      if (p.newsletterCheckbox.isSelected()) {
 *          System.out.println("Newsletter is already subscribed");
 *      }
 *
 *      // Deselect a checkbox
 *      p.rememberCheckbox.deselect();
 *
 *      // Get checkbox label text
 *      String label = p.agreeCheckbox.text();
 *      System.out.println("Checkbox label: " + label);
 * });
 * }</pre>
 *
 * <h2>Component Rules</h2>
 * <p>
 * The {@link #rules(SnComponentRule)} method validates that an element is a valid checkbox by checking:
 * <ul>
 *   <li>The tag name is exactly "input"</li>
 *   <li>The type attribute is exactly "checkbox"</li>
 * </ul>
 *
 *
 * <p>
 * <strong>Idempotent Behavior:</strong> All state change operations are idempotent:
 * <pre>{@code
 * // These calls are safe to make repeatedly:
 * checkbox.select();      // Safe to call multiple times - no effect if already selected
 * checkbox.select();      // No harm done
 *
 * checkbox.deselect();    // Safe to call multiple times - no effect if already deselected
 * checkbox.deselect();    // No harm done
 * }</pre>
 *
 *
 * @see SnSelectableComponent
 * @see SnFormComponent
 * @see SnComponent
 * @see SnComponentRule
 */
public class SnCheckbox extends SnSelectableComponent {
    /**
     * Defines validation rules for checkbox elements.
     *
     * <p>
     * This method validates that the component wraps a valid checkbox input element. Checkboxes must be
     * implemented specifically as HTML {@code <input>} elements with the type attribute set to "checkbox".
     * 
     *
     * <p>
     * <strong>Validation Logic:</strong>
     * <ol>
     *   <li>Checks that the element's tag name is exactly "input"</li>
     *   <li>Verifies the type attribute is exactly "checkbox"</li>
     * </ol>
     * 
     *
     * <p>
     * <strong>Valid and Invalid Examples:</strong>
     * <pre>{@code
     * <!-- Valid checkbox -->
     * <input type="checkbox" id="agree" />                 // Valid
     *
     * <!-- Invalid - wrong tag -->
     * <button type="checkbox">Check</button>               // Invalid - not an input
     *
     * <!-- Invalid - wrong type -->
     * <input type="radio" id="option" />                   // Invalid - radio, not checkbox
     * <input type="text" id="field" />                     // Invalid - text input
     * }</pre>
     * 
     *
     * @param rule the {@link SnComponentRule} object used to define and verify checkbox validation rules
     * @see SnComponentRule#tag()
     * @see SnComponentRule#type()
     */
    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("input");
        rule.type().is("checkbox");
    }

    /**
     * Deselects this checkbox if it is currently selected.
     *
     * <p>
     * The automatic scrolling ensures that the checkbox is visible on the screen before attempting
     * to click it, which helps prevent issues with elements that are not in the viewport.
     * 
     *
     * <p>
     * <strong>Idempotent Behavior:</strong> This method is safe to call multiple times. Calling deselect()
     * on an already-deselected checkbox has no effect.
     * 
     *
     * <p>
     * <strong>Usage Examples:</strong>
     * <pre>{@code
     * SnCheckbox checkbox = page.checkbox;
     *
     * // Deselect the checkbox
     * checkbox.deselect();
     *
     * // Verify it's deselected
     * assert !checkbox.isSelected() : "Checkbox should be deselected";
     *
     * // Safe to call again - no effect
     * checkbox.deselect();  // Already deselected, no action taken
     *
     * // Combined with select for state toggling
     * checkbox.select();     // Select it
     * checkbox.deselect();   // Now deselect it
     * }</pre>
     * 
     *
     * @see #select()
     * @see #isSelected()
     * @see SnComponent#scrolledElement()
     */
    public void deselect() {
        if (this.isSelected()) {
            this.enabledElement().click();
        }
    }
}