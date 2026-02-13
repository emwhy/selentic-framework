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

        LOG.debug("Locator XPath: {}", s);
        return By.xpath(s);
    }

    public SnCssSelector descendant(SnSelectorProperty... locatorProperties) {
        return new SnCssSelectorDescendant(this, locatorProperties);
    }

    public SnCssSelector child(SnSelectorProperty... locatorProperties) {
        return new SnCssSelectorChild(this, locatorProperties);
    }

    public SnCssSelector sibling(SnSelectorProperty... locatorProperties) {
        return new SnCssSelectorSibling(this, locatorProperties);
    }

    public SnCssSelector nextSibling(SnSelectorProperty... locatorProperties) {
        return new SnCssSelectorNextSibling(this, locatorProperties);
    }

    public SnCssSelector page(SnSelectorProperty... locatorProperties) {
        return new SnCssSelectorPage(locatorProperties);
    }}
