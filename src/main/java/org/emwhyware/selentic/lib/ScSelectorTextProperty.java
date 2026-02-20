package org.emwhyware.selentic.lib;


import org.checkerframework.checker.nullness.qual.NonNull;

public final class ScSelectorTextProperty extends ScSelectorProperty implements ScXpathPropertyType {
    private final Conditions condition;
    private final String text;

    ScSelectorTextProperty(@NonNull Conditions condition, String text) {
        this.condition = condition;
        this.text = text;
    }

    @Override
    public String build(@NonNull Types type) {
        final String property = "text()";
        String selector = "";

        switch (this.condition) {
            case Is -> selector = property + "='" + this.text + "'";
            case Contains -> selector = "contains(" + property + ",'" + this.text + "')";
            case StartsWith -> selector = "starts-with(" + property + ",'" + this.text + "')";
            case EndsWith -> selector = "substring(" + property + ", string-length(" + property + ") - string-length('" + this.text + "')+1) = '" + this.text + "'";
            case WholeWord -> selector = "contains(concat(' ', normalize-space(text()), ' '), ' " + this.text + " ')";
        }

        if (this.negated()) {
            selector = "not(" + selector + ")";
        }
        return "[" + selector + "]";
    }

    enum Conditions {
        Is, Contains, StartsWith, EndsWith, WholeWord;
    }

}
