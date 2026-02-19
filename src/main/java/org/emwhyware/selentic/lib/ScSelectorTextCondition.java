package org.emwhyware.selentic.lib;

public class ScSelectorTextCondition {

    ScSelectorTextCondition() {
    }

    public ScSelectorTextProperty is(String text) {
        return new ScSelectorTextProperty(ScSelectorTextProperty.Conditions.Is, text);
    }

    public ScSelectorTextProperty contains(String text) {
        return new ScSelectorTextProperty(ScSelectorTextProperty.Conditions.Contains, text);
    }

    public ScSelectorTextProperty startsWith(String text) {
        return new ScSelectorTextProperty(ScSelectorTextProperty.Conditions.StartsWith, text);
    }

    public ScSelectorTextProperty endsWith(String text) {
        return new ScSelectorTextProperty(ScSelectorTextProperty.Conditions.EndsWith, text);
    }

    public ScSelectorTextProperty wholeWord(String text) {
        return new ScSelectorTextProperty(ScSelectorTextProperty.Conditions.WholeWord, text);
    }
}
