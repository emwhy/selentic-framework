package org.selion_framework.lib;

public sealed class SeLocatorBuilder permits SeComponentLocatorBuilder, SePageLocatorBuilder {
    private SeLocatorNode locatorNode;

    protected SeLocatorNode locator() {
        return this.locatorNode;
    }

    protected void setLocator(SeLocatorNode locatorNode) {
        this.locatorNode = locatorNode;
    }
}
