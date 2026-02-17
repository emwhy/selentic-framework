package org.emwhyware.selentic.lib;


public final class SnSelectorAttributeProperty extends SnSelectorProperty implements SnXpathPropertyType, SnCssSelectorPropertyType {
    private final String prefix;
    private final String attribute;
    private final Conditions condition;
    private final String text;

    SnSelectorAttributeProperty(String prefix, String attribute, Conditions condition, String text) {
        this.prefix = prefix;
        this.attribute = attribute;
        this.condition = condition;
        this.text = text;
    }

    @Override
    public String build(Types type) {
        String selector = "";

        if (type == Types.XPath) {
            switch (this.condition) {
                case Is -> selector = this.prefix + this.attribute + "='" + this.text + "'";
                case Contains -> selector = "contains(" + this.prefix + this.attribute + ", '" + this.text + "'";
                case StartsWith -> selector = "starts-width(" + this.prefix + this.attribute + "='" + this.text + "'";
                case EndsWith -> selector = "substring(" + this.prefix + this.attribute + ", string-length(" + this.prefix + this.attribute + ") - string-length('" + this.text + "')+1) = '" + this.text + "'";
                case WholeWord -> selector = "[contains(concat(' ', normalize-space(" + this.prefix + this.attribute + "), ' '), ' " + this.text + " ')]";
            }

            if (this.negated()) {
                selector = "not(" + selector + ")";
            }
            return "[" + selector + "]";
        } else if (type == Types.CssSelector) {
            switch (this.condition) {
                case Is -> selector = this.attribute + "='" + this.text + "'";
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
        Is, Contains, StartsWith, EndsWith, WholeWord;
    }

}
