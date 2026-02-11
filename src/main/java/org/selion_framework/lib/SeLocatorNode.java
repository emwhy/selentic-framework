package org.selion_framework.lib;

import org.openqa.selenium.By;
import org.selion_framework.lib.util.SeLogHandler;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Optional;

public sealed abstract class SeLocatorNode permits SeLocatorNodeParent, SeLocatorNodeDescendant, SeLocatorNodeChild, SeLocatorNodeSibling, SeLocatorNodePrecedingSibling, SeLocatorNodePage {
    private static final Logger LOG = SeLogHandler.logger(SeLocatorNode.class);
    private final String tag;
    private final Optional<SeLocatorNode> priorLocatorNode;
    private final SeLocatorProperty[] locatorProperties;

    SeLocatorNode(String tag, SeLocatorProperty... locatorProperties) {
        this.tag = tag;
        this.priorLocatorNode = Optional.empty();
        this.locatorProperties = locatorProperties;
    }

    SeLocatorNode(SeLocatorNode priorLocatorNode, String tag, SeLocatorProperty... locatorProperties) {
        this.tag = tag;
        this.priorLocatorNode = Optional.of(priorLocatorNode);
        this.locatorProperties = locatorProperties;
    }

    protected abstract String nodeText();

    @Override
    public String toString() {
        return (priorLocatorNode.map(SeLocatorNode::toString).orElse("")) + nodeText() + tag + String.join("", Arrays.stream(locatorProperties).map(SeLocatorProperty::build).toList());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    By build() {
        return build("");
    }

    By build(String prefix) {
        final String s = prefix + toString();

        LOG.debug("Locator XPath: {}", s);
        return By.xpath(s);
    }

    public SeLocatorNode descendant(String tag, SeLocatorProperty... locatorProperties) {
        return new SeLocatorNodeDescendant(this, tag, locatorProperties);
    }

    public SeLocatorNode child(String tag, SeLocatorProperty... locatorProperties) {
        return new SeLocatorNodeChild(this, tag, locatorProperties);
    }

    public SeLocatorNode sibling(String tag, SeLocatorProperty... locatorProperties) {
        return new SeLocatorNodeSibling(this, tag, locatorProperties);
    }

    public SeLocatorNode precedingSibling(String tag, SeLocatorProperty... locatorProperties) {
        return new SeLocatorNodePrecedingSibling(this, tag, locatorProperties);
    }

    public SeLocatorNode parent() {
        return new SeLocatorNodeParent(this);
    }

    public SeLocatorNode page(String tag, SeLocatorProperty... locatorProperties) {
        return new SeLocatorNodePage(tag, locatorProperties);
    }
}
