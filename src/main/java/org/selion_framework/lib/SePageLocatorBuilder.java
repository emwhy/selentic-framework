package org.selion_framework.lib;

public final class SePageLocatorBuilder extends SeLocatorBuilder {
    public SeLocatorNode descendant(String tag, SeLocatorProperty... locatorProperties) {
        this.setLocator(new SeLocatorNodePage(tag, locatorProperties));
        return this.locator();
    }
}
