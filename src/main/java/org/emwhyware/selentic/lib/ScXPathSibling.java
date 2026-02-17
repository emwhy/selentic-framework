package org.emwhyware.selentic.lib;

public final class ScXPathSibling extends ScXPath {
    ScXPathSibling(String tag, ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathSibling(ScXPath priorSelectorNode, String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/following-sibling::";
    }
}
