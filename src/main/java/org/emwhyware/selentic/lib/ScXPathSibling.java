package org.emwhyware.selentic.lib;

public final class ScXPathSibling extends ScXPath {
    ScXPathSibling(String tag, ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathSibling(ScXPath priorSelectorNode, String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    ScXPathSibling(ScXpathPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    ScXPathSibling(ScXPath priorSelectorNode, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/following-sibling::";
    }
}
