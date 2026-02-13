package org.selion_framework.lib;

public class SnSelectorPropertyBuilder {
    public SnSelectorProperty not(SnSelectorProperty selectorProperty) {
        return new SnSelectorNotProperty(selectorProperty);
    }

    public SnSelectorPropertyCondition attr(String attribute) {
        return new SnSelectorPropertyCondition("@" + attribute);
    }

    public SnSelectorCssClassesProperty cssClasses(String... cssClasses) {
        return new SnSelectorCssClassesProperty(cssClasses);
    }

    public SnSelectorPropertyCondition id() {
        return attr("id");
    }

    public SnSelectorPropertyCondition name() {
        return attr("name");
    }

    public SnSelectorPropertyCondition type() {
        return attr("type");
    }

    public SnSelectorPropertyCondition text() {
        return new SnSelectorPropertyCondition("text()");
    }
//
//    public SelectorIndexProperty indexFrom(int startIndex) {
//        return new SelectorIndexProperty();
//    }
//
//    public SelectorIndexProperty indexTo(int endIndex) {
//        return new SelectorIndexProperty();
//    }
//
//    public SelectorIndexProperty indexAt(int index) {
//        return new SelectorIndexProperty();
//    }
//
//    public SelectorAttributeProperty endsBefore(SelectorNode endsBeforeSelector) {
//        return new SelectorAttributeProperty();
//    }
}
