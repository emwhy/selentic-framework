package org.selion_framework.lib;

public final class SeComponentLocatorBuilder extends SeLocatorBuilder {
    public SeLocatorNode descendant(String tag, SeLocatorProperty... locatorProperties) {
        this.setLocator(new SeLocatorNodeDescendant(tag, locatorProperties));
        return this.locator();
    }

    public SeLocatorNode child(String tag, SeLocatorProperty... locatorProperties) {
        this.setLocator(new SeLocatorNodeChild(tag, locatorProperties));
        return this.locator();
    }

    public SeLocatorNode sibling(String tag, SeLocatorProperty... locatorProperties) {
        this.setLocator(new SeLocatorNodeSibling(tag, locatorProperties));
        return this.locator();
    }

    public SeLocatorNode precedingSibling(String tag, SeLocatorProperty... locatorProperties) {
        this.setLocator(new SeLocatorNodePrecedingSibling(tag, locatorProperties));
        return this.locator();
    }

    public SeLocatorNode page(String tag, SeLocatorProperty... locatorProperties) {
        this.setLocator(new SeLocatorNodePage(tag, locatorProperties));
        return this.locator();
    }

    public SeLocatorNode parent() {
        this.setLocator(new SeLocatorNodeParent());
        return this.locator();
    }
}
