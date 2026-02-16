package org.emwhyware.selion.lib;

public class SnSelectorTextCondition {

    SnSelectorTextCondition() {
    }

    public SnSelectorTextProperty is(String text) {
        return new SnSelectorTextProperty(SnSelectorTextProperty.Conditions.Is, text);
    }

    public SnSelectorTextProperty contains(String text) {
        return new SnSelectorTextProperty(SnSelectorTextProperty.Conditions.Contains, text);
    }

    public SnSelectorTextProperty startsWith(String text) {
        return new SnSelectorTextProperty(SnSelectorTextProperty.Conditions.StartsWith, text);
    }

    public SnSelectorTextProperty endsWith(String text) {
        return new SnSelectorTextProperty(SnSelectorTextProperty.Conditions.EndsWith, text);
    }
}
