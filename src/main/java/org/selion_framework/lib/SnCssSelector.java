package org.selion_framework.lib;

import org.openqa.selenium.By;
import org.selion_framework.lib.util.SnLogHandler;
import org.slf4j.Logger;

import java.util.Arrays;

public sealed abstract class SnCssSelector extends SnSelector permits SnCssSelectorChild, SnCssSelectorDescendant, SnCssSelectorSibling, SnCssSelectorNextSibling, SnCssSelectorPage {
    private static final Logger LOG = SnLogHandler.logger(SnCssSelector.class);
    private final SnCssSelectorPropertyType[] selectorProperties;

    SnCssSelector(SnCssSelectorPropertyType... selectorProperties) {
        super();
        this.selectorProperties = selectorProperties;
    }

    SnCssSelector(SnCssSelector priorSelectorNode, SnCssSelectorPropertyType... selectorProperties) {
        super(priorSelectorNode);
        this.selectorProperties = selectorProperties;
    }

    @Override
    public String toString() {
        return (priorSelectorNode().map(Object::toString).orElse("")) + nodeText() + String.join("", Arrays.stream(selectorProperties).map(p -> p.build(SnSelectorPropertyType.Types.CssSelector)).toList());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    protected By build() {
        final String s = toString();

        LOG.debug("Selector CSS Selector: {}", s);
        return By.cssSelector(s);
    }

    public SnCssSelector descendant(SnCssSelectorPropertyType... selectorProperties) {
        return new SnCssSelectorDescendant(this, selectorProperties);
    }

    public SnCssSelector child(SnCssSelectorPropertyType... selectorProperties) {
        return new SnCssSelectorChild(this, selectorProperties);
    }

    public SnCssSelector sibling(SnCssSelectorPropertyType... selectorProperties) {
        return new SnCssSelectorSibling(this, selectorProperties);
    }

    public SnCssSelector nextSibling(SnCssSelectorPropertyType... selectorProperties) {
        return new SnCssSelectorNextSibling(this, selectorProperties);
    }

    public SnCssSelector page(SnCssSelectorPropertyType... selectorProperties) {
        return new SnCssSelectorPage(selectorProperties);
    }}
