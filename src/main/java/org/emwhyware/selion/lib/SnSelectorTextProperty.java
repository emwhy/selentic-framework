package org.emwhyware.selion.lib;


public final class SnSelectorTextProperty extends SnSelectorProperty implements SnXpathPropertyType {
    private final Conditions condition;
    private final String text;

    SnSelectorTextProperty(Conditions condition, String text) {
        this.condition = condition;
        this.text = text;
    }

    @Override
    public String build(Types type) {
        final String property = "text()";
        String selector = "";

        if (type == Types.XPath) {
            switch (this.condition) {
                case Is -> selector = property + "='" + this.text + "'";
                case Contains -> selector = "contains(" + property + ", '" + this.text + "'";
                case StartsWith -> selector = "starts-width(" + property + "='" + this.text + "'";
                case EndsWith -> selector = "substring(" + property + ", string-length(" + property + ") - string-length('" + this.text + "')+1) = '" + this.text + "'";
            }

            if (this.negated()) {
                selector = "not(" + selector + ")";
            }
            return "[" + selector + "]";
        } else if (type == Types.CssSelector) {
            switch (this.condition) {
                case Is -> selector = property + "='" + this.text + "'";
                case Contains -> selector = property + "*='" + this.text + "'";
                case StartsWith -> selector = property + "^='" + this.text + "'";
                case EndsWith -> selector = property + "$='" + this.text + "'";
            }

            if (this.negated()) {
                return ":not([" + selector + "])";
            } else {
                return "[" + selector + "]";
            }
        } else {
            throw new RuntimeException();
        }
    }

    enum Conditions {
        Is, Contains, StartsWith, EndsWith;
    }

}
