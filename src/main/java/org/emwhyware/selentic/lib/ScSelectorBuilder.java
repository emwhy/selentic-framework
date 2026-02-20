package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.util.ScNullCheck;

public abstract sealed class ScSelectorBuilder permits ScComponentXPathBuilder, ScPageXPathBuilder, ScComponentCssSelectorBuilder, ScPageCssSelectorBuilder {
    private @MonotonicNonNull ScSelector selector;

    protected ScSelector selector() {
        return ScNullCheck.requiresNonNull(this.selector, ScSelector.class);
    }

    public abstract @NonNull ScSelector raw(@NonNull String selectorText);

    protected void setSelector(@NonNull ScSelector selector) {
        this.selector = selector;
    }
}
