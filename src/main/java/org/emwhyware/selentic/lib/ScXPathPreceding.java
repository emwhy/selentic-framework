package org.emwhyware.selentic.lib;

public final class ScXPathPreceding extends ScXPath {
    ScXPathPreceding(String tag, ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathPreceding(ScXPath priorSelectorNode, String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    ScXPathPreceding(ScXpathPropertyType... selectorProperties) {
        super(selectorProperties);
    }

    ScXPathPreceding(ScXPath priorSelectorNode, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding::";
    }
}
