package org.selion_framework.lib;

import org.openqa.selenium.By;

import java.util.Optional;

public abstract sealed class SnSelector permits SnCssSelector, SnXPathSelector {
    private final Optional<SnSelector> priorSelectorNode;

    protected SnSelector() {
        this.priorSelectorNode = Optional.empty();
    }

    protected SnSelector(SnSelector priorSelectorNode) {
        this.priorSelectorNode = Optional.of(priorSelectorNode);
    }

    protected Optional<SnSelector> priorSelectorNode() {
        return priorSelectorNode;
    }

    protected abstract String nodeText();
    
    protected abstract By build();
}
