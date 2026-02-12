package org.selion_framework.lib;

import org.openqa.selenium.By;
import org.selion_framework.lib.util.SnLogHandler;
import org.slf4j.Logger;

import java.util.Arrays;

public sealed abstract class SnXPathSelector extends SnSelectorNode permits SnXPathSelectorParent, SnXPathSelectorDescendant, SnXPathSelectorChild, SnXPathSelectorSibling, SnXPathSelectorPrecedingSibling, SnXPathSelectorPage {
    private static final Logger LOG = SnLogHandler.logger(SnXPathSelector.class);
    private final String tag;
    private final SnLocatorProperty[] locatorProperties;

    SnXPathSelector(String tag, SnLocatorProperty... locatorProperties) {
        super();
        this.tag = tag;
        this.locatorProperties = locatorProperties;
    }

    SnXPathSelector(SnXPathSelector priorSelectorNode, String tag, SnLocatorProperty... locatorProperties) {
        super(priorSelectorNode);
        this.tag = tag;
        this.locatorProperties = locatorProperties;
    }

    protected abstract String nodeText();

    @Override
    public String toString() {
        return (priorSelectorNode().map(Object::toString).orElse("")) + nodeText() + tag + String.join("", Arrays.stream(locatorProperties).map(SnLocatorProperty::build).toList());
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

    public SnXPathSelector descendant(String tag, SnLocatorProperty... locatorProperties) {
        return new SnXPathSelectorDescendant(this, tag, locatorProperties);
    }

    public SnXPathSelector child(String tag, SnLocatorProperty... locatorProperties) {
        return new SnXPathSelectorChild(this, tag, locatorProperties);
    }

    public SnXPathSelector sibling(String tag, SnLocatorProperty... locatorProperties) {
        return new SnXPathSelectorSibling(this, tag, locatorProperties);
    }

    public SnXPathSelector precedingSibling(String tag, SnLocatorProperty... locatorProperties) {
        return new SnXPathSelectorPrecedingSibling(this, tag, locatorProperties);
    }

    public SnXPathSelector parent() {
        return new SnXPathSelectorParent(this);
    }

    public SnXPathSelector page(String tag, SnLocatorProperty... locatorProperties) {
        return new SnXPathSelectorPage(tag, locatorProperties);
    }
}
