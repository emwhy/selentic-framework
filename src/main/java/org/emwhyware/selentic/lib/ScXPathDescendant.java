package org.emwhyware.selentic.lib;

public final class ScXPathDescendant extends ScXPath {
    ScXPathDescendant(String tag, ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathDescendant(ScXPath priorSelectorNode, String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/descendant::";
    }
}
