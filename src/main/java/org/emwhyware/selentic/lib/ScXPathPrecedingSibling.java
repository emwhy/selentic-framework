package org.emwhyware.selentic.lib;

public final class ScXPathPrecedingSibling extends ScXPath {
    ScXPathPrecedingSibling(ScXPath priorSelectorNode, String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    ScXPathPrecedingSibling(ScXPath priorSelectorNode, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding-sibling::";
    }
}
