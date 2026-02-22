package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScSelectorAttributeCondition {
    private final String prefix;
    private final String attribute;

    ScSelectorAttributeCondition(@NonNull String prefix, @NonNull String attribute) {
        this.prefix = prefix;
        this.attribute = attribute;
    }

    public ScSelectorAttributeProperty is(@NonNull String text) {
        return new ScSelectorAttributeProperty(this.prefix, this.attribute, ScSelectorAttributeProperty.Conditions.Is, text);
    }

    public ScSelectorAttributeProperty isPresent() {
        return new ScSelectorAttributeProperty(this.prefix, this.attribute, ScSelectorAttributeProperty.Conditions.IsPresent, "");
    }

    public ScSelectorAttributeProperty contains(@NonNull String text) {
        return new ScSelectorAttributeProperty(this.prefix, this.attribute, ScSelectorAttributeProperty.Conditions.Contains, text);
    }

    public ScSelectorAttributeProperty startsWith(@NonNull String text) {
        return new ScSelectorAttributeProperty(this.prefix, this.attribute, ScSelectorAttributeProperty.Conditions.StartsWith, text);
    }

    public ScSelectorAttributeProperty endsWith(@NonNull String text) {
        return new ScSelectorAttributeProperty(this.prefix, this.attribute, ScSelectorAttributeProperty.Conditions.EndsWith, text);
    }

    public ScSelectorAttributeProperty wholeWord(@NonNull String text) {
        return new ScSelectorAttributeProperty(this.prefix, this.attribute, ScSelectorAttributeProperty.Conditions.WholeWord, text);
    }
}
