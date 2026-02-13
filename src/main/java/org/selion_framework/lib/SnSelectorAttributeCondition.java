package org.selion_framework.lib;

public class SnSelectorAttributeCondition {
    private final String prefix;
    private final String attribute;

    SnSelectorAttributeCondition(String prefix, String attribute) {
        this.prefix = prefix;
        this.attribute = attribute;
    }

    public SnSelectorAttributeProperty is(String text) {
        return new SnSelectorAttributeProperty(this.prefix, this.attribute, SnSelectorAttributeProperty.Conditions.Is, text);
    }

    public SnSelectorAttributeProperty contains(String text) {
        return new SnSelectorAttributeProperty(this.prefix, this.attribute, SnSelectorAttributeProperty.Conditions.Contains, text);
    }

    public SnSelectorAttributeProperty startsWith(String text) {
        return new SnSelectorAttributeProperty(this.prefix, this.attribute, SnSelectorAttributeProperty.Conditions.StartsWith, text);
    }

    public SnSelectorAttributeProperty endsWith(String text) {
        return new SnSelectorAttributeProperty(this.prefix, this.attribute, SnSelectorAttributeProperty.Conditions.EndsWith, text);
    }
}
