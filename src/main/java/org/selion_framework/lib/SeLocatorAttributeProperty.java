package org.selion_framework.lib;


public class SeLocatorAttributeProperty extends SeLocatorProperty {
    private final String property;
    private final Conditions condition;
    private final String text;

    SeLocatorAttributeProperty(String property, Conditions condition, String text) {
        this.property = property;
        this.condition = condition;
        this.text = text;
    }

    @Override
    protected String build() {
        String xpath = "";

        switch (this.condition) {
            case Is -> xpath = this.property + "='" + this.text + "'";
            case Contains -> xpath = "contains(" + this.property + ", '" + this.text + "'";
            case StartsWith -> xpath =  "starts-width(" + this.property + "='" + this.text + "'";
            case EndsWith -> xpath = "substring(" + this.property + ", string-length(" + this.property + ") - string-length('" + this.text + "')+1) = '" + this.text + "'";
        }

        if (this.isNegated()) {
            xpath = "not(" + xpath + ")";
        }
        return "[" + xpath + "]";
    }

    enum Conditions {
        Is, Contains, StartsWith, EndsWith;
    }

}
