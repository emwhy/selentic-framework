package org.emwhyware.selentic.lib;

public final class ScXPathPage extends ScXPath {
    ScXPathPage(String tag, ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
