package org.selion_framework.lib;

public class SeLocatorPropertyCondition {
    final String property;

    SeLocatorPropertyCondition(String property) {
        this.property = property;
    }

    public SeLocatorAttributeProperty is(String text) {
        return new SeLocatorAttributeProperty(this.property, SeLocatorAttributeProperty.Conditions.Is, text);
    }

    public SeLocatorAttributeProperty contains(String text) {
        return new SeLocatorAttributeProperty(this.property, SeLocatorAttributeProperty.Conditions.Contains, text);
    }

    public SeLocatorAttributeProperty startsWith(String text) {
        return new SeLocatorAttributeProperty(this.property, SeLocatorAttributeProperty.Conditions.StartsWith, text);
    }

    public SeLocatorAttributeProperty endsWith(String text) {
        return new SeLocatorAttributeProperty(this.property, SeLocatorAttributeProperty.Conditions.EndsWith, text);
    }
}
