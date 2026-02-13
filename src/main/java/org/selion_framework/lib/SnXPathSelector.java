package org.selion_framework.lib;

import org.openqa.selenium.By;
import org.selion_framework.lib.util.SnLogHandler;
import org.slf4j.Logger;

import java.util.Arrays;

public sealed abstract class SnXPathSelector extends SnSelector permits SnXPathSelectorParent, SnXPathSelectorDescendant, SnXPathSelectorChild, SnXPathSelectorSibling, SnXPathSelectorPrecedingSibling, SnXPathSelectorPage {
    private static final Logger LOG = SnLogHandler.logger(SnXPathSelector.class);
    private final String tag;
    private final SnSelectorProperty[] selectorProperties;

    SnXPathSelector(String tag, SnSelectorProperty... selectorProperties) {
        super();
        this.tag = tag;
        this.selectorProperties = selectorProperties;
    }

    SnXPathSelector(SnXPathSelector priorSelectorNode, String tag, SnSelectorProperty... selectorProperties) {
        super(priorSelectorNode);
        this.tag = tag;
        this.selectorProperties = selectorProperties;
    }

    @Override
    public String toString() {
        return (priorSelectorNode().map(Object::toString).orElse("")) + nodeText() + tag + String.join("", Arrays.stream(selectorProperties).map(SnSelectorProperty::build).toList());
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

    public SnXPathSelector descendant(String tag, SnSelectorProperty... selectorProperties) {
        return new SnXPathSelectorDescendant(this, tag, selectorProperties);
    }

    public SnXPathSelector child(String tag, SnSelectorProperty... selectorProperties) {
        return new SnXPathSelectorChild(this, tag, selectorProperties);
    }

    public SnXPathSelector sibling(String tag, SnSelectorProperty... selectorProperties) {
        return new SnXPathSelectorSibling(this, tag, selectorProperties);
    }

    public SnXPathSelector precedingSibling(String tag, SnSelectorProperty... selectorProperties) {
        return new SnXPathSelectorPrecedingSibling(this, tag, selectorProperties);
    }

    public SnXPathSelector parent() {
        return new SnXPathSelectorParent(this);
    }

    public SnXPathSelector page(String tag, SnSelectorProperty... selectorProperties) {
        return new SnXPathSelectorPage(tag, selectorProperties);
    }
}
