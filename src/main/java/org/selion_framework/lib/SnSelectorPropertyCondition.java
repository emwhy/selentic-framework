package org.selion_framework.lib;

public class SnSelectorPropertyCondition {
    final String property;

    SnSelectorPropertyCondition(String property) {
        this.property = property;
    }

    public SnSelectorAttributeProperty is(String text) {
        return new SnSelectorAttributeProperty(this.property, SnSelectorAttributeProperty.Conditions.Is, text);
    }

    public SnSelectorAttributeProperty contains(String text) {
        return new SnSelectorAttributeProperty(this.property, SnSelectorAttributeProperty.Conditions.Contains, text);
    }

    public SnSelectorAttributeProperty startsWith(String text) {
        return new SnSelectorAttributeProperty(this.property, SnSelectorAttributeProperty.Conditions.StartsWith, text);
    }

    public SnSelectorAttributeProperty endsWith(String text) {
        return new SnSelectorAttributeProperty(this.property, SnSelectorAttributeProperty.Conditions.EndsWith, text);
    }
}
