package org.selion_framework.lib;

/**
 Represents the specific property type for CSS selectors. The interface ensures that {@link SnCssSelector} only takes
 properties that are available with CSS selectors.

 <p>
 This interface serves as a marker or specialized type for identifying selectors that use
 CSS-based strategies to locate elements in a web application. It extends {@link SnSelectorPropertyType} to
 integrate with the framework's generalized selector property management.
 

 @see SnCssSelector
 @see SnSelectorPropertyType
 @see SnXpathPropertyType
 */
public interface SnCssSelectorPropertyType extends SnSelectorPropertyType {
}
