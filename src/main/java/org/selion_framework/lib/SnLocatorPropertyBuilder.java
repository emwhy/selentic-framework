package org.selion_framework.lib;

public class SnLocatorPropertyBuilder {
    public SnLocatorProperty not(SnLocatorProperty locatorProperty) {
        return new SnLocatorNotProperty(locatorProperty);
    }

    public SnLocatorPropertyCondition attr(String attribute) {
        return new SnLocatorPropertyCondition("@" + attribute);
    }

    public SnLocatorCssClassesProperty cssClasses(String... cssClasses) {
        return new SnLocatorCssClassesProperty(cssClasses);
    }

    public SnLocatorPropertyCondition id() {
        return attr("id");
    }

    public SnLocatorPropertyCondition name() {
        return attr("name");
    }

    public SnLocatorPropertyCondition type() {
        return attr("type");
    }

    public SnLocatorPropertyCondition text() {
        return new SnLocatorPropertyCondition("text()");
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
