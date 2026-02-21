package org.emwhyware.selentic.lib.selector;


import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.exception.ScSelectorException;

public final class ScSelectorAttributeProperty extends ScSelectorProperty implements ScXpathPropertyType, ScCssSelectorPropertyType {
    private final String prefix;
    private final String attribute;
    private final Conditions condition;
    private final String text;

    ScSelectorAttributeProperty(@NonNull String prefix, @NonNull String attribute, @NonNull Conditions condition, @NonNull String text) {
        this.prefix = prefix;
        this.attribute = attribute;
        this.condition = condition;
        this.text = text;
    }

    @Override
    public String build(@NonNull Types type) {
        String selector = "";

        if (this.attribute.contains(" ")) {
            throw new ScSelectorException("Attribute contains space character. This can yield unexpected results.");
        }

        if (type == Types.XPath) {
            switch (this.condition) {
                case Is -> selector = this.prefix + this.attribute + "='" + this.text + "'";
                case IsPresent -> selector = this.prefix + this.attribute;
                case Contains -> selector = "contains(" + this.prefix + this.attribute + ",'" + this.text + "')";
                case StartsWith -> selector = "starts-with(" + this.prefix + this.attribute + ",'" + this.text + "')";
                case EndsWith -> selector = "substring(" + this.prefix + this.attribute + ", string-length(" + this.prefix + this.attribute + ") - string-length('" + this.text + "')+1) = '" + this.text + "'";
                case WholeWord -> selector = "contains(concat(' ', normalize-space(" + this.prefix + this.attribute + "), ' '), ' " + this.text + " ')";
            }

            if (this.negated()) {
                selector = "not(" + selector + ")";
            }
            return "[" + selector + "]";
        } else if (type == Types.CssSelector) {
            switch (this.condition) {
                case Is -> selector = this.attribute + "='" + this.text + "'";
                case IsPresent -> selector = this.attribute;
                case Contains -> selector = this.attribute + "*='" + this.text + "'";
                case StartsWith -> selector = this.attribute + "^='" + this.text + "'";
                case EndsWith -> selector = this.attribute + "$='" + this.text + "'";
                case WholeWord -> selector = this.attribute + "~='" + this.text + "'";
            }

            if (this.negated()) {
                return ":not([" + selector + "])";
            } else {
                return "[" + selector + "]";
            }
        } else {
            return "";
        }
    }

    enum Conditions {
        Is, IsPresent, Contains, StartsWith, EndsWith, WholeWord;
    }

}
