package org.emwhyware.selentic.lib;

public class ScSelectorAttributeCondition {
    private final String prefix;
    private final String attribute;

    ScSelectorAttributeCondition(String prefix, String attribute) {
        this.prefix = prefix;
        this.attribute = attribute;
    }

    public ScSelectorAttributeProperty is(String text) {
        return new ScSelectorAttributeProperty(this.prefix, this.attribute, ScSelectorAttributeProperty.Conditions.Is, text);
    }

    public ScSelectorAttributeProperty isPresent() {
        return new ScSelectorAttributeProperty(this.prefix, this.attribute, ScSelectorAttributeProperty.Conditions.IsPresent, "");
    }

    public ScSelectorAttributeProperty contains(String text) {
        return new ScSelectorAttributeProperty(this.prefix, this.attribute, ScSelectorAttributeProperty.Conditions.Contains, text);
    }

    public ScSelectorAttributeProperty startsWith(String text) {
        return new ScSelectorAttributeProperty(this.prefix, this.attribute, ScSelectorAttributeProperty.Conditions.StartsWith, text);
    }

    public ScSelectorAttributeProperty endsWith(String text) {
        return new ScSelectorAttributeProperty(this.prefix, this.attribute, ScSelectorAttributeProperty.Conditions.EndsWith, text);
    }

    public ScSelectorAttributeProperty wholeWord(String text) {
        return new ScSelectorAttributeProperty(this.prefix, this.attribute, ScSelectorAttributeProperty.Conditions.WholeWord, text);
    }
}
