package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.exception.ScComponentRulesException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * {@code ScComponentRule} provides a fluent API for defining and verifying validation rules
 * for {@link ScComponent} instances. These rules ensure that a web element matches the expected
 * properties before being used as a component.
 *
 * <p>
 * <strong>Purpose:</strong> This class enables component validation by allowing developers
 * to specify what HTML attributes, CSS classes, tag names, and other properties must be present or
 * match certain conditions on the underlying web element.
 * 
 *
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * protected void rules(ScComponentRule rule) {
 *     rule.tag().is("div");                           // Element must be a div
 *     rule.cssClasses().has("card");                   // Must have 'card' class
 *     rule.attr("data-type").is("product");            // data-type attribute must be 'product'
 *     rule.attr("id").isPresent();                     // id attribute must be present
 * }
 * }</pre>
 *
 *
 * <p>
 * <strong>Rule Conditions:</strong> Rules can verify:
 * <ul>
 *   <li>Tag names (e.g., "div", "button", "input")</li>
 *   <li>HTML attributes (id, name, href, title, type, custom attributes)</li>
 *   <li>CSS classes (presence, specific classes, multiple classes)</li>
 *   <li>Text content matching patterns or specific values</li>
 * </ul>
 *
 *
 * <p>
 * <strong>Verification:</strong> Rules are verified when {@link #verifyRules(Class)} is called.
 * If any rule fails, a {@link ScComponentRulesException} is thrown with detailed error messages
 * about which rules failed and why.
 * 
 *
 * @see ScComponent
 * @see ScComponentRulesException
 * @see ScRuleCondition
 * @see ScCssClassRuleCondition
 */
public class ScComponentRule {
    private final WebElement element;
    private final List<ScAbstractRunResult> ruleResults = new ArrayList<>();

    /**
     * Constructs a {@code ScComponentRule} for the specified web element.
     *
     * <p>
     * This constructor is package-private and is called internally by the framework.
     * Use this class through the {@link ScComponent#rules(ScComponentRule)} method.
     * 
     *
     * @param element the {@link WebElement} to validate with rules
     */
    ScComponentRule(WebElement element) {
        this.element = element;
    }

    /**
     * Verifies all rules that have been defined for the component. This is executed when loading web element in
     * a component object. <b>rule.any()</b> can be used.
     *
     * <p>
     * This method checks that at least one rule has been defined and that all defined rules pass.
     * If no rules are defined, an exception is thrown. If any rule fails verification, detailed
     * error messages are included in the exception.
     * 
     *
     * @param <T> the component type being verified
     * @param componentType the class of the component being verified
     * @throws ScComponentRulesException if no rules are defined or any rule fails
     * @see #any()
     *
     */
    <T extends ScComponent> void verifyRules(Class<T> componentType) {
        if (this.ruleResults.isEmpty()) {
            throw new ScComponentRulesException(componentType);
        } else {
            final List<String> ruleFailureTexts = this.ruleResults.stream().filter(r -> !r.result).map(r -> r.errorText()).toList();

            if (!ruleFailureTexts.isEmpty()) {
                throw new ScComponentRulesException(componentType, ruleFailureTexts);
            }
        }
    }

    /**
     * Defines an "any" rule that always passes.
     *
     * <p>
     * This rule can be used as a placeholder or when no specific validation is needed
     * but at least one rule is required by the framework.
     * 
     */
    public void any() {
        this.ruleResults.add(new ScRuleResult(ScRuleType.Any));
    }

    /**
     * Returns a rule condition for validating the HTML tag name of the element.
     *
     * <p>
     * Example: {@code rule.tag().is("div"); }
     * 
     *
     * @return a {@link ScRuleCondition} for the element's tag name
     */
    public ScRuleCondition tag() {
        return new ScRuleCondition("tag", this.element.getTagName());
    }

    /**
     * Returns a rule condition for validating a specific HTML attribute value.
     *
     * <p>
     * Example: {@code rule.attr("data-id").is("123"); }
     * 
     *
     * @param attr the name of the HTML attribute to validate
     * @return a {@link ScRuleCondition} for the specified attribute
     */
    public ScRuleCondition attr(String attr) {
        return new ScRuleCondition("'" + attr + "' attribute", this.element.getDomAttribute(attr));
    }

    /**
     * Returns a rule condition for validating the "type" attribute of the element.
     *
     * <p>
     * This is a convenience method equivalent to {@code attr("type")}.
     * Example: {@code rule.type().is("text"); }
     * 
     *
     * @return a {@link ScRuleCondition} for the "type" attribute
     */
    public ScRuleCondition type() {
        return this.attr("type");
    }

    /**
     * Returns a rule condition for validating the "name" attribute of the element.
     *
     * <p>
     * This is a convenience method equivalent to {@code attr("name")}.
     * Example: {@code rule.name().is("username"); }
     * 
     *
     * @return a {@link ScRuleCondition} for the "name" attribute
     */
    public ScRuleCondition name() {
        return this.attr("name");
    }

    /**
     * Returns a rule condition for validating the "id" attribute of the element.
     *
     * <p>
     * This is a convenience method equivalent to {@code attr("id")}.
     * Example: {@code rule.id().is("submit-btn"); }
     * 
     *
     * @return a {@link ScRuleCondition} for the "id" attribute
     */
    public ScRuleCondition id() {
        return this.attr("id");
    }

    /**
     * Returns a rule condition for validating the "href" attribute of the element.
     *
     * <p>
     * This is a convenience method equivalent to {@code attr("href")}.
     * Typically used for link elements.
     * Example: {@code rule.href().startsWith("https://"); }
     * 
     *
     * @return a {@link ScRuleCondition} for the "href" attribute
     */
    public ScRuleCondition href() {
        return this.attr("href");
    }

    /**
     * Returns a rule condition for validating the "title" attribute of the element.
     *
     * <p>
     * This is a convenience method equivalent to {@code attr("title")}.
     * Example: {@code rule.title().contains("Click"); }
     * 
     *
     * @return a {@link ScRuleCondition} for the "title" attribute
     */
    public ScRuleCondition title() {
        return this.attr("title");
    }

    /**
     * Returns a rule condition for validating CSS classes of the element.
     *
     * <p>
     * This method provides specialized handling for CSS class validation with methods like
     * {@code has()}, {@code hasAllOf()}, and {@code hasAnyOf()}.
     * Example: {@code rule.cssClasses().has("active"); }
     * 
     *
     * @return a {@link ScCssClassRuleCondition} for validating CSS classes
     */
    public ScCssClassRuleCondition cssClasses() {
        String cssClasses;
        return new ScCssClassRuleCondition(((cssClasses = this.element.getAttribute("class")) == null ? new String[0] : cssClasses.split(" ")));
    }

    /**
     * Provides a fluent API for defining rule conditions on attribute values.
     *
     * <p>
     * This inner class allows developers to chain validation methods to check whether
     * an attribute or element property matches certain conditions. Each method in this class
     * adds a rule result that will be verified later.
     * 
     *
     * <p>
     * <strong>Available Conditions:</strong>
     * <ul>
     *   <li>{@code isPresent()} - attribute exists</li>
     *   <li>{@code isAbsent()} - attribute does not exist</li>
     *   <li>{@code is(String)} - attribute equals exact value</li>
     *   <li>{@code isNot(String)} - attribute does not equal value</li>
     *   <li>{@code isOneOf(String...)} - attribute is one of multiple values</li>
     *   <li>{@code contains(String)} - attribute contains substring</li>
     *   <li>{@code doesNotContain(String)} - attribute does not contain substring</li>
     *   <li>{@code startsWith(String)} - attribute starts with prefix</li>
     *   <li>{@code endsWith(String)} - attribute ends with suffix</li>
     *   <li>{@code matches(String)} - attribute matches regex pattern</li>
     * </ul>
     *
     */
    public class ScRuleCondition {
        private final String valueType;
        private final Optional<String> actualValue;

        /**
         * Constructs a rule condition with a value type and actual value.
         *
         * @param valueType description of the value being tested (e.g., "tag", "attribute")
         * @param actualValue the actual value from the element
         */
        private ScRuleCondition(String valueType, String actualValue) {
            this.valueType = valueType;
            this.actualValue = Optional.ofNullable(actualValue);
        }

        /**
         * Adds a rule that the attribute must be present.
         *
         * <p>
         * Example: {@code rule.id().isPresent(); }
         * 
         */
        public void isPresent() {
            ScComponentRule.this.ruleResults.add(new ScRuleResult(valueType, ScRuleType.IsPresent, actualValue));
        }

        /**
         * Adds a rule that the attribute must be absent (not present).
         *
         * <p>
         * Example: {@code rule.attr("disabled").isAbsent(); }
         * 
         */
        public void isAbsent() {
            ScComponentRule.this.ruleResults.add(new ScRuleResult(valueType, ScRuleType.IsAbsent, actualValue));
        }

        /**
         * Adds a rule that the attribute value must exactly equal the expected value.
         *
         * <p>
         * The comparison is case-sensitive.
         * Example: {@code rule.tag().is("button"); }
         * 
         *
         * @param expected the expected value
         */
        public void is(String expected) {
            ScComponentRule.this.ruleResults.add(new ScRuleResult(valueType, ScRuleType.Is, actualValue, expected));
        }

        /**
         * Adds a rule that the attribute value must not equal the expected value.
         *
         * <p>
         * The comparison is case-sensitive.
         * Example: {@code rule.type().isNot("hidden"); }
         * 
         *
         * @param expected the unexpected value
         */
        public void isNot(String expected) {
            ScComponentRule.this.ruleResults.add(new ScRuleResult(valueType, ScRuleType.IsNot, actualValue, expected));
        }

        /**
         * Adds a rule that the attribute value must be one of the provided values.
         *
         * <p>
         * Example: {@code rule.type().isOneOf("text", "email", "password"); }
         * 
         *
         * @param expected one or more acceptable values
         */
        public void isOneOf(String... expected) {
            ScComponentRule.this.ruleResults.add(new ScRuleResult(valueType, ScRuleType.IsOneOf, actualValue, expected));
        }

        /**
         * Adds a rule that the attribute value must not contain the expected substring.
         *
         * <p>
         * Example: {@code rule.attr("class").doesNotContain("hidden"); }
         * 
         *
         * @param expected the substring that must not be present
         */
        public void doesNotContain(String expected) {
            ScComponentRule.this.ruleResults.add(new ScRuleResult(valueType, ScRuleType.DoesNotContain, actualValue, expected));
        }

        /**
         * Adds a rule that the attribute value must contain the expected substring.
         *
         * <p>
         * Example: {@code rule.attr("class").contains("btn"); }
         * 
         *
         * @param expected the substring that must be present
         */
        public void contains(String expected) {
            ScComponentRule.this.ruleResults.add(new ScRuleResult(valueType, ScRuleType.Contains, actualValue, expected));
        }

        /**
         * Adds a rule that the attribute value must start with the expected prefix.
         *
         * <p>
         * Example: {@code rule.href().startsWith("https://"); }
         * 
         *
         * @param expected the required prefix
         */
        public void startsWith(String expected) {
            ScComponentRule.this.ruleResults.add(new ScRuleResult(valueType, ScRuleType.StartsWith, actualValue, expected));
        }

        /**
         * Adds a rule that the attribute value must end with the expected suffix.
         *
         * <p>
         * Example: {@code rule.href().endsWith(".html"); }
         * 
         *
         * @param expected the required suffix
         */
        public void endsWith(String expected) {
            ScComponentRule.this.ruleResults.add(new ScRuleResult(valueType, ScRuleType.EndsWith, actualValue, expected));
        }

        /**
         * Adds a rule that the attribute value must match the provided regular expression pattern.
         *
         * <p>
         * Example: {@code rule.attr("data-id").matches("\\d+"); }
         * 
         *
         * @param regexPattern the regex pattern the value must match
         */
        public void matches(String regexPattern) {
            ScComponentRule.this.ruleResults.add(new ScRuleResult(valueType, ScRuleType.Matches, actualValue, regexPattern));
        }
    }

    /**
     * Provides a fluent API for defining CSS class validation rules.
     *
     * <p>
     * This inner class specializes in validating the CSS classes of an element.
     * It provides methods to check for presence of specific classes, combinations of classes,
     * and absence of classes.
     * 
     *
     * <p>
     * <strong>Available CSS Class Conditions:</strong>
     * <ul>
     *   <li>{@code isPresent()} - element has any CSS classes</li>
     *   <li>{@code isAbsent()} - element has no CSS classes</li>
     *   <li>{@code has(String)} - element has a specific class</li>
     *   <li>{@code doesNotHave(String)} - element does not have a specific class</li>
     *   <li>{@code hasAllOf(String...)} - element has all specified classes</li>
     *   <li>{@code hasAnyOf(String...)} - element has at least one of the specified classes</li>
     *   <li>{@code hasNoneOf(String...)} - element has none of the specified classes</li>
     * </ul>
     *
     *
     * <p>
     * <strong>Example:</strong>
     * <pre>{@code
     * rule.cssClasses().has("btn");                    // Must have 'btn' class
     * rule.cssClasses().hasAllOf("btn", "primary");    // Must have both classes
     * rule.cssClasses().hasNoneOf("disabled", "hidden"); // Must not have these classes
     * }</pre>
     *
     */
    public class ScCssClassRuleCondition {
        private final Optional<String[]> actualCssClasses;

        /**
         * Constructs a CSS class rule condition with no CSS classes.
         */
        private ScCssClassRuleCondition() {
            this.actualCssClasses = Optional.empty();
        }

        /**
         * Constructs a CSS class rule condition with the provided CSS classes.
         *
         * @param actualCssClasses the CSS classes to validate
         */
        private ScCssClassRuleCondition(String... actualCssClasses) {
            this.actualCssClasses = Optional.of(actualCssClasses);
        }

        /**
         * Adds a rule that the element must have at least one CSS class.
         *
         * <p>
         * Example: {@code rule.cssClasses().isPresent(); }
         * 
         */
        public void isPresent() {
            ScComponentRule.this.ruleResults.add(new ScCssClassRunResult(ScCssClassRuleType.IsPresent, actualCssClasses));

        }

        /**
         * Adds a rule that the element must not have any CSS classes.
         *
         * <p>
         * Example: {@code rule.cssClasses().isAbsent(); }
         * 
         */
        public void isAbsent() {
            ScComponentRule.this.ruleResults.add(new ScCssClassRunResult(ScCssClassRuleType.IsAbsent, actualCssClasses));
        }

        /**
         * Adds a rule that the element must have the specified CSS class.
         *
         * <p>
         * Example: {@code rule.cssClasses().has("active"); }
         * 
         *
         * @param expectedCssClass the CSS class that must be present
         */
        public void has(String expectedCssClass) {
            ScComponentRule.this.ruleResults.add(new ScCssClassRunResult(ScCssClassRuleType.Has, actualCssClasses, new String[] { expectedCssClass }));
        }

        /**
         * Adds a rule that the element must not have the specified CSS class.
         *
         * <p>
         * Example: {@code rule.cssClasses().doesNotHave("hidden"); }
         * 
         *
         * @param expectedCssClass the CSS class that must not be present
         */
        public void doesNotHave(String expectedCssClass) {
            ScComponentRule.this.ruleResults.add(new ScCssClassRunResult(ScCssClassRuleType.DoesNotHave, actualCssClasses, new String[] { expectedCssClass }));
        }

        /**
         * Adds a rule that the element must have all of the specified CSS classes.
         *
         * <p>
         * Example: {@code rule.cssClasses().hasAllOf("btn", "btn-primary", "active"); }
         * 
         *
         * @param expectedCssClasses all CSS classes that must be present
         */
        public void hasAllOf(String... expectedCssClasses) {
            ScComponentRule.this.ruleResults.add(new ScCssClassRunResult(ScCssClassRuleType.HasAllOf, actualCssClasses, expectedCssClasses));
        }

        /**
         * Adds a rule that the element must have at least one of the specified CSS classes.
         *
         * <p>
         * Example: {@code rule.cssClasses().hasAnyOf("primary", "secondary", "tertiary"); }
         * 
         *
         * @param expectedCssClasses one or more CSS classes where at least one must be present
         */
        public void hasAnyOf(String... expectedCssClasses) {
            ScComponentRule.this.ruleResults.add(new ScCssClassRunResult(ScCssClassRuleType.HasAnyOf, actualCssClasses, expectedCssClasses));
        }

        /**
         * Adds a rule that the element must not have any of the specified CSS classes.
         *
         * <p>
         * Example: {@code rule.cssClasses().hasNoneOf("disabled", "hidden", "loading"); }
         * 
         *
         * @param expectedCssClasses CSS classes that must not be present
         */
        public void hasNoneOf(String... expectedCssClasses) {
            ScComponentRule.this.ruleResults.add(new ScCssClassRunResult(ScCssClassRuleType.HasNoneOf, actualCssClasses, expectedCssClasses));
        }
    }

    /**
     * Enum representing the types of validation rules for attribute values.
     */
    private enum ScRuleType {
        /**
         * Rule that always passes.
         */
        Any,
        /**
         * Attribute must be present.
         */
        IsPresent,
        /**
         * Attribute must be absent.
         */
        IsAbsent,
        /**
         * Attribute must equal exact value.
         */
        Is,
        /**
         * Attribute must not equal value.
         */
        IsNot,
        /**
         * Attribute must be one of multiple values.
         */
        IsOneOf,
        /**
         * Attribute must contain substring.
         */
        Contains,
        /**
         * Attribute must not contain substring.
         */
        DoesNotContain,
        /**
         * Attribute must start with prefix.
         */
        StartsWith,
        /**
         * Attribute must end with suffix.
         */
        EndsWith,
        /**
         * Attribute must match regex pattern.
         */
        Matches,
    }

    /**
     * Enum representing the types of validation rules for CSS classes.
     */
    private enum ScCssClassRuleType {
        /**
         * Element must have at least one CSS class.
         */
        IsPresent,
        /**
         * Element must not have any CSS classes.
         */
        IsAbsent,
        /**
         * Element must have a specific class.
         */
        Has,
        /**
         * Element must not have a specific class.
         */
        DoesNotHave,
        /**
         * Element must have all specified classes.
         */
        HasAllOf,
        /**
         * Element must have at least one of the specified classes.
         */
        HasAnyOf,
        /**
         * Element must not have any of the specified classes.
         */
        HasNoneOf
    }

    /**
     * Abstract base class for rule verification results.
     *
     * <p>
     * This class tracks whether a rule has passed or failed and provides an error message
     * if the rule failed.
     * 
     */
    private abstract class ScAbstractRunResult {
        private boolean result = false;

        /**
         * Sets the result of the rule verification.
         *
         * @param result true if the rule passed, false if it failed
         */
        void setResult(boolean result) {
            this.result = result;
        }

        /**
         * Returns the result of the rule verification.
         *
         * @return true if the rule passed, false if it failed
         */
        boolean result() {
            return result;
        }

        /**
         * Returns an error message describing why the rule failed.
         *
         * @return the error message, or empty string if the rule passed
         */
        abstract String errorText();
    }

    /**
     * Represents the result of a single attribute value validation rule.
     *
     * <p>
     * This class evaluates the rule based on the rule type and actual/expected values,
     * and provides detailed error messages when the rule fails.
     * 
     */
    private class ScRuleResult extends ScAbstractRunResult {
        private final String valueType;
        private final Optional<String> actualValue;
        private final ScRuleType ruleType;
        private final String[] expectedValues;

        /**
         * Constructs a rule result for an "Any" type rule.
         *
         * @param ruleType the type of rule
         */
        private ScRuleResult(ScRuleType ruleType) {
            this.valueType = "";
            this.actualValue = Optional.empty();
            this.ruleType = ruleType;
            this.expectedValues = new String[] {};
            this.setResult(ruleType == ScRuleType.Any);
        }

        /**
         * Constructs a rule result with a rule type and actual value.
         *
         * @param valueType description of the value being tested
         * @param ruleType the type of rule
         * @param actualValue the actual value from the element
         */
        private ScRuleResult(String valueType, ScRuleType ruleType, Optional<String> actualValue) {
            this(valueType, ruleType, actualValue, new String[] {});
        }

        /**
         * Constructs a rule result with all parameters.
         *
         * <p>
         * This constructor evaluates the rule immediately based on the actual and expected values.
         * 
         *
         * @param valueType description of the value being tested
         * @param ruleType the type of rule
         * @param actualValue the actual value from the element
         * @param expectedValues the expected values for the rule
         */
        private ScRuleResult(String valueType, ScRuleType ruleType, Optional<String> actualValue, String... expectedValues) {
            this.valueType = valueType;
            this.actualValue = actualValue;
            this.ruleType = ruleType;
            this.expectedValues = expectedValues;

            if (actualValue.isEmpty()) {
                this.setResult(this.ruleType == ScRuleType.IsAbsent);
            } else {
                switch (this.ruleType) {
                    case IsPresent -> this.setResult(true);
                    case IsAbsent -> this.setResult(false);
                    case Is -> this.setResult(expectedValues[0].equals(actualValue.get()));
                    case IsNot -> this.setResult(!expectedValues[0].equals(actualValue.get()));
                    case IsOneOf -> this.setResult(Arrays.stream(expectedValues).toList().contains(actualValue.get()));
                    case Contains -> this.setResult(expectedValues[0].contains(actualValue.get()));
                    case DoesNotContain -> this.setResult(!expectedValues[0].contains(actualValue.get()));
                    case StartsWith -> this.setResult(expectedValues[0].startsWith(actualValue.get()));
                    case EndsWith -> this.setResult(expectedValues[0].endsWith(actualValue.get()));
                    case Matches -> this.setResult(Pattern.matches(expectedValues[0], actualValue.get()));
                }
            }
        }

        /**
         * Returns a detailed error message if the rule failed.
         *
         * @return the error message describing the failure
         */
        @Override
        String errorText() {
            String errorText = "Expected that " + this.valueType;

            if (this.ruleType == ScRuleType.Any) {
              return "";
            } else if (actualValue.isEmpty()) {
                switch (this.ruleType) {
                    case IsPresent -> errorText += " is present, but it is absent.";
                    case IsAbsent -> errorText = "";
                    case Is -> errorText += " is " + listToString(expectedValues) + ", but " + this.valueType + " does not exist.";
                    case IsNot -> errorText += " is not " + listToString(expectedValues) + ", but " + this.valueType + " does not exist.";
                    case IsOneOf -> errorText += " is one of " + listToString(expectedValues) + ", but " + this.valueType + " does not exist.";
                    case Contains -> errorText += " contains " + listToString(expectedValues) + ", but " + this.valueType + " does not exist.";
                    case DoesNotContain -> errorText += " does not contain " + listToString(expectedValues) + ", but " + this.valueType + " does not exist.";
                    case StartsWith -> errorText += " starts with " + listToString(expectedValues) + ", but " + this.valueType + " does not exist.";
                    case EndsWith -> errorText += " ends with " + listToString(expectedValues) + ", but " + this.valueType + " does not exist.";
                    case Matches -> errorText += " matches pattern " + listToString(expectedValues) + ", but " + this.valueType + " does not exist.";
                }
            } else {
                switch (this.ruleType) {
                    case IsPresent -> errorText = "";
                    case IsAbsent -> errorText += " is absent, but it is present.";
                    case Is -> errorText += " is " + listToString(expectedValues) + ", but it is not. (actual: '" + actualValue.get() + "')";
                    case IsNot -> errorText += " is not " + listToString(expectedValues) + ", but it is. (actual: '" + actualValue.get() + "')";
                    case IsOneOf -> errorText += " is one of " + listToString(expectedValues) + ", but it is not. (actual: '" + actualValue.get() + "')";
                    case Contains -> errorText += " contains " + listToString(expectedValues) + ", but it does not. (actual: '" + actualValue.get() + "')";
                    case DoesNotContain -> errorText += " does not contain " + listToString(expectedValues) + ", but it does. (actual: '" + actualValue.get() + "')";
                    case StartsWith -> errorText += " starts with " + listToString(expectedValues) + ", but it does not. (actual: '" + actualValue.get() + "')";
                    case EndsWith -> errorText += " ends with " + listToString(expectedValues) + ", but it does not. (actual: '" + actualValue.get() + "')";
                    case Matches -> errorText += " matches pattern " + listToString(expectedValues) + ", but it does not. (actual: '" + actualValue.get() + "')";
                }
            }

            return errorText;
        }
    }

    /**
     * Represents the result of a single CSS class validation rule.
     *
     * <p>
     * This class evaluates CSS class rules based on the rule type and actual/expected classes,
     * and provides detailed error messages when the rule fails.
     * 
     */
    private class ScCssClassRunResult extends ScAbstractRunResult {
        private final ScCssClassRuleType ruleType;
        private final Optional<String[]> actualCssValues;
        private final String[] expectedCssValues;

        /**
         * Constructs a CSS class rule result with a rule type and actual classes.
         *
         * @param ruleType the type of CSS class rule
         * @param actualCssValues the actual CSS classes from the element
         */
        private ScCssClassRunResult(ScCssClassRuleType ruleType, Optional<String[]> actualCssValues) {
            this(ruleType, actualCssValues, new String[] {});
        }

        /**
         * Constructs a CSS class rule result with all parameters.
         *
         * <p>
         * This constructor evaluates the rule immediately based on the actual and expected classes.
         * 
         *
         * @param ruleType the type of CSS class rule
         * @param actualCssValues the actual CSS classes from the element
         * @param expectedCssValues the expected CSS classes for the rule
         */
        private ScCssClassRunResult(ScCssClassRuleType ruleType, Optional<String[]> actualCssValues, String[] expectedCssValues) {
            this.ruleType = ruleType;
            this.actualCssValues = actualCssValues;
            this.expectedCssValues = expectedCssValues;

            if (actualCssValues.isEmpty()) {
                this.setResult(ruleType == ScCssClassRuleType.IsAbsent);
            } else {
                switch (ruleType) {
                    case ScCssClassRuleType.IsPresent -> this.setResult(true);
                    case ScCssClassRuleType.IsAbsent -> this.setResult(false);
                    case ScCssClassRuleType.Has -> this.setResult(Arrays.stream(actualCssValues.get()).toList().contains(expectedCssValues[0]));
                    case ScCssClassRuleType.DoesNotHave -> this.setResult(!Arrays.stream(actualCssValues.get()).toList().contains(expectedCssValues[0]));
                    case ScCssClassRuleType.HasAllOf -> this.setResult(Arrays.stream(expectedCssValues).allMatch(v -> Arrays.stream(actualCssValues.get()).toList().contains(v)));
                    case ScCssClassRuleType.HasAnyOf -> this.setResult(Arrays.stream(expectedCssValues).anyMatch(v -> Arrays.stream(actualCssValues.get()).toList().contains(v)));
                    case ScCssClassRuleType.HasNoneOf -> this.setResult(Arrays.stream(expectedCssValues).noneMatch(v -> Arrays.stream(actualCssValues.get()).toList().contains(v)));
                }
            }
        }

        /**
         * Returns a detailed error message if the CSS class rule failed.
         *
         * @return the error message describing the failure
         */
        @Override
        String errorText() {
            if (result()) {
                return "";
            } else {
                String errorText = "Expected that CSS class ";

                if (actualCssValues.isEmpty()) {
                    switch (ruleType) {
                        case ScCssClassRuleType.IsPresent -> errorText += " is present, but it is absent.";
                        case ScCssClassRuleType.IsAbsent -> errorText = "";
                        case ScCssClassRuleType.Has -> errorText += " has " + listToString(expectedCssValues) + ", but CSS class does not exist.";
                        case ScCssClassRuleType.DoesNotHave -> errorText += " does not have " + listToString(expectedCssValues) + ", but CSS class does not exist.";
                        case ScCssClassRuleType.HasAllOf -> errorText += " has all of " + listToString(expectedCssValues) + ", but CSS class does not exist.";
                        case ScCssClassRuleType.HasAnyOf -> errorText += " has any of " + listToString(expectedCssValues) + ", but CSS class does not exist.";
                        case ScCssClassRuleType.HasNoneOf -> errorText += " has none of " + listToString(expectedCssValues) + ", but CSS class does not exist.";
                    }
                } else {
                    switch (ruleType) {
                        case ScCssClassRuleType.IsPresent -> errorText = "";
                        case ScCssClassRuleType.IsAbsent -> errorText += " is absent, but it is present (actual: " + listToString(actualCssValues.get()) + ").";
                        case ScCssClassRuleType.Has -> errorText += " has " + listToString(expectedCssValues) + ", but it is missing the class (actual: " + listToString(actualCssValues.get()) + ").";
                        case ScCssClassRuleType.DoesNotHave -> errorText += " does not have " + listToString(expectedCssValues) + ", but it is missing the class (actual: " + listToString(actualCssValues.get()) + ").";
                        case ScCssClassRuleType.HasAllOf -> errorText += " has all of " + listToString(expectedCssValues) + ", but it is missing one or more classes (actual: " + listToString(actualCssValues.get()) + ").";
                        case ScCssClassRuleType.HasAnyOf -> errorText += " has any of " + listToString(expectedCssValues) + ", but it has none of classes (actual: " + listToString(actualCssValues.get()) + ").";
                        case ScCssClassRuleType.HasNoneOf -> errorText += " has none of " + listToString(expectedCssValues) + ", but it has one or more classes (actual: " + listToString(actualCssValues.get()) + ").";
                    }
                }

                return errorText;
            }

        }
    }

    /**
     * Converts a string array into a comma-separated quoted string format.
     *
     * <p>
     * Example: {@code ["foo", "bar", "baz"]} becomes {@code "'foo', 'bar', 'baz'"}
     * 
     *
     * @param values the string array to convert
     * @return the formatted string with each value wrapped in single quotes and separated by commas
     */
    private static String listToString(String[] values) {
        return "'" + String.join("', '", values) + "'";
    }
}