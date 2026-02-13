package org.selion_framework.lib;

import org.openqa.selenium.By;
import org.selion_framework.lib.util.SnLogHandler;
import org.slf4j.Logger;

import java.util.Arrays;

public sealed abstract class SnXPath extends SnSelector permits SnXPathParent, SnXPathDescendant, SnXPathChild, SnXPathSibling, SnXPathPrecedingSibling, SnXPathPage, SnXPathRaw {
    private static final Logger LOG = SnLogHandler.logger(SnXPath.class);
    private final String tag;
    private final SnXpathPropertyType[] selectorProperties;

    SnXPath(String tag, SnXpathPropertyType... selectorProperties) {
        super();
        this.tag = tag;
        this.selectorProperties = selectorProperties;
    }

    SnXPath(SnXPath priorSelectorNode, String tag, SnXpathPropertyType... selectorProperties) {
        super(priorSelectorNode);
        this.tag = tag;
        this.selectorProperties = selectorProperties;
    }

    @Override
    public String toString() {
        return (priorSelectorNode().map(Object::toString).orElse("")) + nodeText() + tag + String.join("", Arrays.stream(selectorProperties).map(p -> p.build(SnSelectorPropertyType.Types.XPath)).toList());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    protected By build() {
        return build("");
    }

    protected By build(String prefix) {
        final String s = prefix + toString();

        LOG.debug("Selector XPath: {}", s);
        return By.xpath(s);
    }

    public SnXPath descendant(String tag, SnXpathPropertyType... selectorProperties) {
        return new SnXPathDescendant(this, tag, selectorProperties);
    }

    public SnXPath child(String tag, SnXpathPropertyType... selectorProperties) {
        return new SnXPathChild(this, tag, selectorProperties);
    }

    public SnXPath sibling(String tag, SnXpathPropertyType... selectorProperties) {
        return new SnXPathSibling(this, tag, selectorProperties);
    }

    public SnXPath precedingSibling(String tag, SnXpathPropertyType... selectorProperties) {
        return new SnXPathPrecedingSibling(this, tag, selectorProperties);
    }

    public SnXPath parent() {
        return new SnXPathParent(this);
    }

    public SnXPath page(String tag, SnXpathPropertyType... selectorProperties) {
        return new SnXPathPage(tag, selectorProperties);
    }
}
