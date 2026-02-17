package org.emwhyware.selentic.lib;

public final class ScXPathPrecedingSibling extends ScXPath {
    ScXPathPrecedingSibling(String tag, ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathPrecedingSibling(ScXPath priorSelectorNode, String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding-sibling::";
    }
}
