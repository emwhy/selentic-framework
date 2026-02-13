package org.selion_framework.lib;

public interface SnSelectorPropertyType {
    String build(Types type);

    enum Types {
        CssSelector, XPath
    }
}
