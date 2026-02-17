package org.emwhyware.selentic.lib;

public final class ScXPathPreceding extends ScXPath {
    ScXPathPreceding(String tag, ScXpathPropertyType... selectorProperties) {
        super(tag, selectorProperties);
    }

    ScXPathPreceding(ScXPath priorSelectorNode, String tag, ScXpathPropertyType... selectorProperties) {
        super(priorSelectorNode, tag, selectorProperties);
    }

    @Override
    protected String nodeText() {
        return "/preceding::";
    }
}
