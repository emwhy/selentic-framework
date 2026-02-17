package org.emwhyware.selentic.lib;

public final class SnSelectorCssClassesProperty extends SnSelectorProperty implements SnXpathPropertyType, SnCssSelectorPropertyType {
    private final String[] cssClasses;

    SnSelectorCssClassesProperty(String... cssClasses) {
        this.cssClasses = cssClasses;
    }

    @Override
    public String build(Types type) {
        final StringBuilder selector = new StringBuilder();

        if (type == Types.XPath) {
            for (String cssClass : this.cssClasses) {
                selector.append("[");
                if (negated()) {
                    selector.append("not(");
                }
                selector.append("contains(@class,'").append(cssClass).append("')");
                if (negated()) {
                    selector.append(")");
                }
                selector.append("]");
            }
        } else if (type == Types.CssSelector) {
            if (negated()) {
                selector.append(":not(");
            }
            for (String cssClass : this.cssClasses) {
                selector.append(".").append(cssClass);
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