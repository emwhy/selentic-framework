package org.emwhyware.selentic.lib;

public final class SnXPathPage extends SnXPath {
    SnXPathPage(String tag, SnXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
