package org.selion_framework.lib;

public class SeLocatorCssClassesProperty extends SeLocatorProperty {
    private final String[] cssClasses;

    SeLocatorCssClassesProperty(String... cssClasses) {
        this.cssClasses = cssClasses;
    }

    @Override
    protected String build() {
        final StringBuilder xpath = new StringBuilder();

        for (String cssClass : this.cssClasses) {
            xpath.append("[");
            if (isNegated()) {
                xpath.append("not(");
            }
            xpath.append("contains(@class,'").append(cssClass).append("')");
            if (isNegated()) {
                xpath.append(")");
            }
            xpath.append("]");
        }

        return xpath.toString();
    }
}