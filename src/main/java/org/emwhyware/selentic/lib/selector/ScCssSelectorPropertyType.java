package org.emwhyware.selentic.lib.selector;

/**
 Represents the specific property type for CSS selectors. The interface ensures that {@link ScCssSelector} only takes
 properties that are available with CSS selectors.

 <p>
 This interface serves as a marker or specialized type for identifying selectors that use
 CSS-based strategies to locate elements in a web application. It extends {@link ScSelectorPropertyType} to
 integrate with the framework's generalized selector property management.
 

 @see ScCssSelector
 @see ScSelectorPropertyType
 @see ScXpathPropertyType
 */
public interface ScCssSelectorPropertyType extends ScSelectorPropertyType {
}
