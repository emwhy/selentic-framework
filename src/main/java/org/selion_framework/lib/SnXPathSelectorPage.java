package org.selion_framework.lib;

public final class SnXPathSelectorPage extends SnXPathSelector {
    SnXPathSelectorPage(String tag, SnLocatorProperty... locatorProperties) {
        super(tag, locatorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
