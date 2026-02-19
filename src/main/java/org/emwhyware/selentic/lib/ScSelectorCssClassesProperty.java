package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.exception.ScSelectorException;

import java.util.Arrays;

public final class ScSelectorCssClassesProperty extends ScSelectorProperty implements ScXpathPropertyType, ScCssSelectorPropertyType {
    private final String[] cssClasses;

    ScSelectorCssClassesProperty(String... cssClasses) {
        this.cssClasses = cssClasses;

        if (Arrays.stream(this.cssClasses).anyMatch(c -> c.trim().contains(" "))) {
            throw new ScSelectorException("One or more CSS classes contain a space character. This can yield unexpected results.");
        }
    }

    @Override
    public String build(Types type) {
        final StringBuilder selector = new StringBuilder();

        if (type == Types.XPath) {
            for (final String cssClass : this.cssClasses) {
                selector.append("[");
                if (negated()) {
                    selector.append("not(");
                }
                // This would be more accurate than typical contains(@class,'className').
                selector.append("contains(concat(' ', normalize-space(@class), ' '), ' ").append(cssClass.trim()).append(" ')");
                if (negated()) {
                    selector.append(")");
                }
                selector.append("]");
            }
        } else if (type == Types.CssSelector) {
            if (negated()) {
                selector.append(":not(");
            }
            for (final String cssClass : this.cssClasses) {
                selector.append(".").append(cssClass.trim());
            }
            if (negated()) {
                selector.append(")");
            }
        } else {
            return "";
        }

        return selector.toString();
    }
}