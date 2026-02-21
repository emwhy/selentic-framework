package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScSelectorTextCondition {

    ScSelectorTextCondition() {
    }

    public ScSelectorTextProperty is(@NonNull String text) {
        return new ScSelectorTextProperty(ScSelectorTextProperty.Conditions.Is, text);
    }

    public ScSelectorTextProperty contains(@NonNull String text) {
        return new ScSelectorTextProperty(ScSelectorTextProperty.Conditions.Contains, text);
    }

    public ScSelectorTextProperty startsWith(@NonNull String text) {
        return new ScSelectorTextProperty(ScSelectorTextProperty.Conditions.StartsWith, text);
    }

    public ScSelectorTextProperty endsWith(@NonNull String text) {
        return new ScSelectorTextProperty(ScSelectorTextProperty.Conditions.EndsWith, text);
    }

    public ScSelectorTextProperty wholeWord(@NonNull String text) {
        return new ScSelectorTextProperty(ScSelectorTextProperty.Conditions.WholeWord, text);
    }
}
