package org.selion_framework.lib;

public class SnLocatorPropertyCondition {
    final String property;

    SnLocatorPropertyCondition(String property) {
        this.property = property;
    }

    public SnLocatorAttributeProperty is(String text) {
        return new SnLocatorAttributeProperty(this.property, SnLocatorAttributeProperty.Conditions.Is, text);
    }

    public SnLocatorAttributeProperty contains(String text) {
        return new SnLocatorAttributeProperty(this.property, SnLocatorAttributeProperty.Conditions.Contains, text);
    }

    public SnLocatorAttributeProperty startsWith(String text) {
        return new SnLocatorAttributeProperty(this.property, SnLocatorAttributeProperty.Conditions.StartsWith, text);
    }

    public SnLocatorAttributeProperty endsWith(String text) {
        return new SnLocatorAttributeProperty(this.property, SnLocatorAttributeProperty.Conditions.EndsWith, text);
    }
}
