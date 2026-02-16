package org.emwhyware.selion.lib;

public interface SnSelectorPropertyType {
    String build(Types type);

    enum Types {
        CssSelector, XPath
    }
}
