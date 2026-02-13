package org.selion_framework.lib;

import org.openqa.selenium.By;
import org.selion_framework.lib.util.SnLogHandler;
import org.slf4j.Logger;

import java.util.Arrays;

public sealed abstract class SnCssSelector extends SnSelector permits SnCssSelectorChild, SnCssSelectorDescendant, SnCssSelectorSibling, SnCssSelectorNextSibling, SnCssSelectorPage {
    private static final Logger LOG = SnLogHandler.logger(SnCssSelector.class);
    private final SnSelectorProperty[] selectorProperties;

    SnCssSelector(SnSelectorProperty... selectorProperties) {
        super();
        this.selectorProperties = selectorProperties;
    }

    SnCssSelector(SnCssSelector priorSelectorNode, SnSelectorProperty... selectorProperties) {
        super(priorSelectorNode);
        this.selectorProperties = selectorProperties;
    }

    @Override
    public String toString() {
        return (priorSelectorNode().map(Object::toString).orElse("")) + nodeText() + String.join("", Arrays.stream(selectorProperties).map(SnSelectorProperty::build).toList());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    protected By build() {
        return build("");
    }

    By build(String prefix) {
        final String s = prefix + toString();

        LOG.debug("Selector XPath: {}", s);
        return By.xpath(s);
    }

    public SnCssSelector descendant(SnSelectorProperty... selectorProperties) {
        return new SnCssSelectorDescendant(this, selectorProperties);
    }

    public SnCssSelector child(SnSelectorProperty... selectorProperties) {
        return new SnCssSelectorChild(this, selectorProperties);
    }

    public SnCssSelector sibling(SnSelectorProperty... selectorProperties) {
        return new SnCssSelectorSibling(this, selectorProperties);
    }

    public SnCssSelector nextSibling(SnSelectorProperty... selectorProperties) {
        return new SnCssSelectorNextSibling(this, selectorProperties);
    }

    public SnCssSelector page(SnSelectorProperty... selectorProperties) {
        return new SnCssSelectorPage(selectorProperties);
    }}
