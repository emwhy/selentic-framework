package org.selion_framework.lib;

public class SeLocatorPropertyBuilder {
    public SeLocatorProperty not(SeLocatorProperty locatorProperty) {
        return new SeLocatorNotProperty(locatorProperty);
    }

    public SeLocatorPropertyCondition attr(String attribute) {
        return new SeLocatorPropertyCondition("@" + attribute);
    }

    public SeLocatorCssClassesProperty cssClasses(String... cssClasses) {
        return new SeLocatorCssClassesProperty(cssClasses);
    }

    public SeLocatorPropertyCondition id() {
        return attr("id");
    }

    public SeLocatorPropertyCondition name() {
        return attr("name");
    }

    public SeLocatorPropertyCondition type() {
        return attr("type");
    }

    public SeLocatorPropertyCondition text() {
        return new SeLocatorPropertyCondition("text()");
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
