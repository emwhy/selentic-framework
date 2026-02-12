package org.selion_framework.lib;

public final class SnLocatorCssClassesProperty extends SnLocatorProperty {
    private final String[] cssClasses;

    SnLocatorCssClassesProperty(String... cssClasses) {
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