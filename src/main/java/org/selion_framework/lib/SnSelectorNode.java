package org.selion_framework.lib;

import org.openqa.selenium.By;

import java.util.Optional;

public abstract sealed class SnSelectorNode permits SnCssSelectorNode, SnXPathSelector {
    private final Optional<SnSelectorNode> priorSelectorNode;

    protected SnSelectorNode() {
        this.priorSelectorNode = Optional.empty();
    }

    protected SnSelectorNode(SnSelectorNode priorSelectorNode) {
        this.priorSelectorNode = Optional.of(priorSelectorNode);
    }

    protected Optional<SnSelectorNode> priorSelectorNode() {
        return priorSelectorNode;
    }

    protected abstract By build();
}
