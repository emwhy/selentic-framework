package org.emwhyware.selentic.lib;

public interface SnSelectorPropertyType {
    String build(Types type);

    enum Types {
        CssSelector, XPath
    }
}
