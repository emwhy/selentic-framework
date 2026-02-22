package org.emwhyware.selentic.lib.selector;

/**
 Represents the specific property type for XPath selectors. The interface ensures that {@link ScXPath} only takes
 properties that are available with XPath.
 <p>
 This interface acts as a marker or specialized type to identify selectors that use
 XPath expressions to navigate
 the XML/HTML structure of a web or mobile application. It extends
 {@link ScSelectorPropertyType} to provide a type-safe way to manage XPath-based
 element locators.
 

 @see ScXPath
 @see ScSelectorPropertyType
 @see ScCssSelectorPropertyType
 */
public interface ScXpathPropertyType extends ScSelectorPropertyType {
}
