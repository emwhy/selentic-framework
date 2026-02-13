package org.selion_framework.lib;

public class SnSelectorPropertyBuilder {
    public SnSelectorProperty not(SnSelectorProperty locatorProperty) {
        return new SnSelectorNotProperty(locatorProperty);
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
//    public LocatorIndexProperty indexFrom(int startIndex) {
//        return new LocatorIndexProperty();
//    }
//
//    public LocatorIndexProperty indexTo(int endIndex) {
//        return new LocatorIndexProperty();
//    }
//
//    public LocatorIndexProperty indexAt(int index) {
//        return new LocatorIndexProperty();
//    }
//
//    public LocatorAttributeProperty endsBefore(LocatorNode endsBeforeLocator) {
//        return new LocatorAttributeProperty();
//    }
}
