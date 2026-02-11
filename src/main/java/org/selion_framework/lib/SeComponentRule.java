package org.selion_framework.lib;

import org.openqa.selenium.WebElement;
import org.selion_framework.lib.exception.SeComponentRulesException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SeComponentRule {
    private final WebElement element;
    private final List<AbstractRunResult> ruleResults = new ArrayList<>();

    SeComponentRule(WebElement element) {
        this.element = element;
    }

    <T extends SeComponent> void verifyRules(Class<T> componentType) {
        if (this.ruleResults.isEmpty()) {
            throw new SeComponentRulesException(componentType);
        } else {
            final List<String> ruleFailureTexts = this.ruleResults.stream().filter(r -> !r.result).map(r -> r.errorText()).toList();

            if (!ruleFailureTexts.isEmpty()) {
                throw new SeComponentRulesException(componentType, ruleFailureTexts);
            }
        }
    }

    public void any() {
        this.ruleResults.add(new RuleResult(RuleType.Any));
    }

    public RuleCondition tag() {
        return new RuleCondition("tag", Optional.of(this.element.getTagName()));
    }

    public RuleCondition attr(String attr) {
        final String a = this.element.getDomAttribute(attr);

        return new RuleCondition("'" + attr + "' attribute", a == null ? Optional.empty() : Optional.of(a));
    }

    public RuleCondition type() {
        return this.attr("type");
    }

    public RuleCondition name() {
        return this.attr("name");
    }

    public RuleCondition id() {
        return this.attr("id");
    }

    public RuleCondition href() {
        return this.attr("href");
    }

    public RuleCondition title() {
        return this.attr("title");
    }

    public CssClassRuleCondition cssClasses() {
        return new CssClassRuleCondition(this.element.getAttribute("class").split(" "));
    }

    public class RuleCondition {
        private final String valueType;
        private final Optional<String> actualValue;

        private RuleCondition(String valueType, Optional<String> actualValue) {
            this.valueType = valueType;
            this.actualValue = actualValue;
        }

        public void isPresent() {
            SeComponentRule.this.ruleResults.add(new RuleResult(valueType, RuleType.IsPresent, actualValue));
        }

        public void isAbsent() {
            SeComponentRule.this.ruleResults.add(new RuleResult(valueType, RuleType.IsAbsent, actualValue));
        }

        public void is(String expected) {
            SeComponentRule.this.ruleResults.add(new RuleResult(valueType, RuleType.Is, actualValue, expected));
        }

        public void isNot(String expected) {
            SeComponentRule.this.ruleResults.add(new RuleResult(valueType, RuleType.IsNot, actualValue, expected));
        }

        public void isOneOf(String... expected) {
            SeComponentRule.this.ruleResults.add(new RuleResult(valueType, RuleType.IsOneOf, actualValue, expected));
        }

        public void doesNotContain(String expected) {
            SeComponentRule.this.ruleResults.add(new RuleResult(valueType, RuleType.DoesNotContain, actualValue, expected));
        }

        public void contains(String expected) {
            SeComponentRule.this.ruleResults.add(new RuleResult(valueType, RuleType.Contains, actualValue, expected));
        }

        public void startsWith(String expected) {
            SeComponentRule.this.ruleResults.add(new RuleResult(valueType, RuleType.StartsWith, actualValue, expected));
        }

        public void endsWith(String expected) {
            SeComponentRule.this.ruleResults.add(new RuleResult(valueType, RuleType.EndsWith, actualValue, expected));
        }

        public void matches(String regexPattern) {
            SeComponentRule.this.ruleResults.add(new RuleResult(valueType, RuleType.Matches, actualValue, regexPattern));
        }
    }

    public class CssClassRuleCondition {
        private final Optional<String[]> actualCssClasses;

        private CssClassRuleCondition() {
            this.actualCssClasses = Optional.empty();
        }

        private CssClassRuleCondition(String... actualCssClasses) {
            this.actualCssClasses = Optional.of(actualCssClasses);
        }

        public void isPresent() {
            SeComponentRule.this.ruleResults.add(new CssClassRunResult(CssClassRuleType.IsPresent, actualCssClasses));

        }

        public void isAbsent() {
            SeComponentRule.this.ruleResults.add(new CssClassRunResult(CssClassRuleType.IsAbsent, actualCssClasses));
        }

        public void has(String expectedCssClass) {
            SeComponentRule.this.ruleResults.add(new CssClassRunResult(CssClassRuleType.Has, actualCssClasses, new String[] { expectedCssClass }));
        }

        public void doesNotHave(String expectedCssClass) {
            SeComponentRule.this.ruleResults.add(new CssClassRunResult(CssClassRuleType.DoesNotHave, actualCssClasses, new String[] { expectedCssClass }));
        }

        public void hasAllOf(String... expectedCssClasses) {
            SeComponentRule.this.ruleResults.add(new CssClassRunResult(CssClassRuleType.HasAllOf, actualCssClasses, expectedCssClasses));
        }

        public void hasAnyOf(String... expectedCssClasses) {
            SeComponentRule.this.ruleResults.add(new CssClassRunResult(CssClassRuleType.HasAnyOf, actualCssClasses, expectedCssClasses));
        }

        public void hasNoneOf(String... expectedCssClasses) {
            SeComponentRule.this.ruleResults.add(new CssClassRunResult(CssClassRuleType.HasNoneOf, actualCssClasses, expectedCssClasses));
        }
    }

    private enum RuleType {
        Any,
        IsPresent,
        IsAbsent,
        Is,
        IsNot,
        IsOneOf,
        Contains,
        DoesNotContain,
        StartsWith,
        EndsWith,
        Matches,
    }

    private enum CssClassRuleType {
        IsPresent,
        IsAbsent,
        Has,
        DoesNotHave,
        HasAllOf,
        HasAnyOf,
        HasNoneOf
    }

    private abstract class AbstractRunResult {
        private boolean result = false;

        void setResult(boolean result) {
            this.result = result;
        }

        boolean result() {
            return result;
        }

        abstract String errorText();
    }

    private class RuleResult extends AbstractRunResult {
        private final String valueType;
        private final Optional<String> actualValue;
        private final RuleType ruleType;
        private final String[] expectedValues;

        private RuleResult(RuleType ruleType) {
            this.valueType = "";
            this.actualValue = Optional.empty();
            this.ruleType = ruleType;
            this.expectedValues = new String[] {};
        }

        private RuleResult(String valueType, RuleType ruleType, Optional<String> actualValue) {
            this(valueType, ruleType, actualValue, new String[] {});
        }

        private RuleResult(String valueType, RuleType ruleType, Optional<String> actualValue, String... expectedValues) {
            this.valueType = valueType;
            this.actualValue = actualValue;
            this.ruleType = ruleType;
            this.expectedValues = expectedValues;

            if (actualValue.isEmpty()) {
                this.setResult(this.ruleType == RuleType.IsAbsent);
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

        @Override
        String errorText() {
            String errorText = "Expected that " + this.valueType;

            if (actualValue.isEmpty()) {
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

    private class CssClassRunResult extends AbstractRunResult {
        private final CssClassRuleType ruleType;
        private final Optional<String[]> actualCssValues;
        private final String[] expectedCssValues;

        private CssClassRunResult(CssClassRuleType ruleType, Optional<String[]> actualCssValues) {
            this(ruleType, actualCssValues, new String[] {});
        }

        private CssClassRunResult(CssClassRuleType ruleType, Optional<String[]> actualCssValues, String[] expectedCssValues) {
            this.ruleType = ruleType;
            this.actualCssValues = actualCssValues;
            this.expectedCssValues = expectedCssValues;

            if (actualCssValues.isEmpty()) {
                this.setResult(ruleType == CssClassRuleType.IsAbsent);
            } else {
                switch (ruleType) {
                    case CssClassRuleType.IsPresent -> this.setResult(true);
                    case CssClassRuleType.IsAbsent -> this.setResult(false);
                    case CssClassRuleType.Has -> this.setResult(Arrays.stream(actualCssValues.get()).toList().contains(expectedCssValues[0]));
                    case CssClassRuleType.DoesNotHave -> this.setResult(!Arrays.stream(actualCssValues.get()).toList().contains(expectedCssValues[0]));
                    case CssClassRuleType.HasAllOf -> this.setResult(Arrays.stream(expectedCssValues).allMatch(v -> Arrays.stream(actualCssValues.get()).toList().contains(v)));
                    case CssClassRuleType.HasAnyOf -> this.setResult(Arrays.stream(expectedCssValues).anyMatch(v -> Arrays.stream(actualCssValues.get()).toList().contains(v)));
                    case CssClassRuleType.HasNoneOf -> this.setResult(Arrays.stream(expectedCssValues).noneMatch(v -> Arrays.stream(actualCssValues.get()).toList().contains(v)));
                }
            }
        }

        @Override
        String errorText() {
            if (result()) {
                return "";
            } else {
                String errorText = "Expected that CSS class ";

                if (actualCssValues.isEmpty()) {
                    switch (ruleType) {
                        case CssClassRuleType.IsPresent -> errorText += " is present, but it is absent.";
                        case CssClassRuleType.IsAbsent -> errorText = "";
                        case CssClassRuleType.Has -> errorText += " has " + listToString(expectedCssValues) + ", but CSS class does not exist.";
                        case CssClassRuleType.DoesNotHave -> errorText += " does not have " + listToString(expectedCssValues) + ", but CSS class does not exist.";
                        case CssClassRuleType.HasAllOf -> errorText += " has all of " + listToString(expectedCssValues) + ", but CSS class does not exist.";
                        case CssClassRuleType.HasAnyOf -> errorText += " has any of " + listToString(expectedCssValues) + ", but CSS class does not exist.";
                        case CssClassRuleType.HasNoneOf -> errorText += " has none of " + listToString(expectedCssValues) + ", but CSS class does not exist.";
                    }
                } else {
                    switch (ruleType) {
                        case CssClassRuleType.IsPresent -> errorText = "";
                        case CssClassRuleType.IsAbsent -> errorText += " is absent, but it is present (actual: " + listToString(actualCssValues.get()) + ").";
                        case CssClassRuleType.Has -> errorText += " has " + listToString(expectedCssValues) + ", but it is missing the class (actual: " + listToString(actualCssValues.get()) + ").";
                        case CssClassRuleType.DoesNotHave -> errorText += " does not have " + listToString(expectedCssValues) + ", but it is missing the class (actual: " + listToString(actualCssValues.get()) + ").";
                        case CssClassRuleType.HasAllOf -> errorText += " has all of " + listToString(expectedCssValues) + ", but it is missing one or more classes (actual: " + listToString(actualCssValues.get()) + ").";
                        case CssClassRuleType.HasAnyOf -> errorText += " has any of " + listToString(expectedCssValues) + ", but it has none of classes (actual: " + listToString(actualCssValues.get()) + ").";
                        case CssClassRuleType.HasNoneOf -> errorText += " has none of " + listToString(expectedCssValues) + ", but it has one or more classes (actual: " + listToString(actualCssValues.get()) + ").";
                    }
                }

                return errorText;
            }

        }
    }
    private static String listToString(String[] values) {
        return "'" + String.join("', '", values) + "'";
    }
}
