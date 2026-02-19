package org.emwhyware.selentic.lib;

public final class ScXPathChild extends ScXPath {
    ScXPathChild(String tag, ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathChild(ScXPath priorSelectorNode, String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    ScXPathChild(ScXpathPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    ScXPathChild(ScXPath priorSelectorNode, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/child::";
    }
}
