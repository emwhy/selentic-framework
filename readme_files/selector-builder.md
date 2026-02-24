[< Return to README](../README.md)
## Using Selector Builders
Selentic Framework contains selector builders to help writing selectors. It provides implementation to help writing 
selectors in fluent and consistent ways and lessen common mistakes,

Using the builder, both CSS selectors and XPath are written in very similar syntax. That makes utilizing both CSS Selectors and XPath in a same project much more fluent.

By using **_cssSelector** or **_xpath** property, accessible inside a page (extended from **ScPage** class) or a component class (extended from **ScComponent** class), you can build selectors as shown below. Selector
related methods are prefixed by underscore ("_") to keep them easy to find in method selection in an editor, and see in code. 

### Css Selector

Many commonly used CSS selectors are implemented in builder. Unlike when using a string to implement CSS selector, you can take full advantage of color coding and suggestion of many IDE.

The features are limited by CSS selector's capability (i.e., no backtracking in CSS selectors). 

But if text based implementation of CSS selectors is more desired, or the builder is missing feature for certain CSS selector features, **_cssSelector.raw("CSS selector text")** method can be used.

```java
    private static final ScCssSelector CSS_SELECTOR_ID_TEST_TEXT = _cssSelector.descendant(_id("outer-table-1"));
    private static final ScCssSelector CSS_SELECTOR_TAG_TEST_TEXTS = _cssSelector.descendant("h2");
    private static final ScCssSelector CSS_SELECTOR_CSS_CLASSES_TEST_TEXTS = _cssSelector.descendant(_cssClasses("status", "active"));
    private static final ScCssSelector CSS_SELECTOR_ATTR_START_WITH_TEST_TEXTS = _cssSelector.descendant(_attr("scope").startsWith("co"));
    private static final ScCssSelector CSS_SELECTOR_ATTR_ENDS_WITH_TEST_TEXTS = _cssSelector.descendant(_attr("scope").endsWith("ol"));
    private static final ScCssSelector CSS_SELECTOR_ATTR_CONTAINS_TEST_TEXTS = _cssSelector.descendant(_attr("scope").contains("ol"));
    private static final ScCssSelector CSS_SELECTOR_ATTR_WHOLE_WORD_TEST_TEXTS = _cssSelector.descendant(_attr("class").wholeWord("status"));
    private static final ScCssSelector CSS_SELECTOR_NTH_OF_TYPE_TEST_TEXT = _cssSelector.descendant("body").child("h2", _nthOfType(1));
    private static final ScCssSelector CSS_SELECTOR_NTH_LAST_OF_TYPE_TEST_TEXT = _cssSelector.descendant("body").child("h2", _nthLastOfType(1));
    private static final ScCssSelector CSS_SELECTOR_LAST_OF_TYPE_TEST_TEXT = _cssSelector.descendant("body").child(_attr("id").isPresent(), _lastOfType());
    private static final ScCssSelector CSS_SELECTOR_NTH_CHILD_TEST_TEXT = _cssSelector.descendant("body").child(_nthChild(0));
    private static final ScCssSelector CSS_SELECTOR_NTH_LAST_CHILD_TEST_TEXT = _cssSelector.descendant("body").child(_nthLastChild(1));
    private static final ScCssSelector CSS_SELECTOR_LAST_CHILD_TEST_TEXT = _cssSelector.descendant("body").child(_lastChild());
    private static final ScCssSelector CSS_SELECTOR_LAST_OF_TYPE_NESTED_TEST_TEXT = CSS_SELECTOR_LAST_OF_TYPE_TEST_TEXT.child("tbody").descendant(_nthChild(1));
    private static final ScCssSelector CSS_SELECTOR_SIBLING_TEST_TEXTS = _cssSelector.descendant(_id("main-r2-c1")).sibling("td", _not(_attr("class").isPresent()));
    private static final ScCssSelector CSS_SELECTOR_NEXT_SIBLING_TEST_TEXT = _cssSelector.descendant(_id("main-r2-c1")).nextSibling("td");
    private static final ScCssSelector CSS_SELECTOR_DESCENDANT_TEST_TEXTS = _cssSelector.descendant("th");
    private static final ScCssSelector CSS_SELECTOR_CHILD_TEST_TEXTS = _cssSelector.descendant(_id("outer-table-2")).child("tbody").child("tr").child("td");
    private static final ScCssSelector CSS_SELECTOR_NOT_TEST_TEXTS = _cssSelector.descendant("body").child(_not(_tag("h2")));
    private static final ScCssSelector CSS_SELECTOR_RAW_TEST_TEXTS = _cssSelector.raw("body > :not(table)");

```

### XPath

Many commonly used XPath are implemented in builder, but not everything is implemented in the builder.

But if text based implementation of XPath is more desired, or the builder is missing feature for certain XPath features, **__xpath.raw("XPath text")** method can be used.

```java
    private static final ScXPath XPATH_ID_TEST_TEXT = _xpath.descendant(_id().is("outer-table-1"));
    private static final ScXPath XPATH_TAG_TEST_TEXTS = _xpath.descendant("h2");
    private static final ScXPath XPATH_CSS_CLASSES_TEST_TEXTS = _xpath.descendant(_cssClasses("status", "active"));
    private static final ScXPath XPATH_ATTR_IS_TEST_TEXTS = _xpath.descendant(_attr("scope").is("col"));
    private static final ScXPath XPATH_ATTR_START_WITH_TEST_TEXTS = _xpath.descendant(_attr("scope").startsWith("co"));
    private static final ScXPath XPATH_ATTR_ENDS_WITH_TEST_TEXTS = _xpath.descendant(_attr("scope").endsWith("ol"));
    private static final ScXPath XPATH_ATTR_CONTAINS_TEST_TEXTS = _xpath.descendant(_attr("scope").contains("ol"));
    private static final ScXPath XPATH_ATTR_WHOLE_WORD_TEST_TEXTS = _xpath.descendant(_attr("class").wholeWord("status"));

    private static final ScXPath XPATH_INDEX_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-1")).child("tbody").child("tr", _indexFrom(3), _indexTo(6)).child("td", _indexAt(0));
    private static final ScXPath XPATH_FIRST_TEST_TEXT = _xpath.descendant(_id().is("outer-table-1")).child("tbody").child("tr", _first()).child("td", _indexAt(0));
    private static final ScXPath XPATH_LAST_TEST_TEXT = _xpath.descendant(_id().is("outer-table-1")).child("tbody").child("tr", _last()).child("td", _indexAt(0));

    private static final ScXPath XPATH_TEXT_TEST_IS_TEXTS = _xpath.descendant(_text().is("Active")).precedingSibling("td");
    private static final ScXPath XPATH_TEXT_TEST_STARTS_WITH_TEXTS = _xpath.descendant(_text().startsWith("Act")).precedingSibling("td");
    private static final ScXPath XPATH_TEXT_TEST_ENDS_WITH_TEXTS = _xpath.descendant(_text().endsWith("dby")).precedingSibling("td");
    private static final ScXPath XPATH_TEXT_TEST_CONTAINS_TEXTS = _xpath.descendant(_text().contains("arn")).precedingSibling("td");
    private static final ScXPath XPATH_TEXT_TEST_WHOLE_WORD_TEXTS = _xpath.descendant(_text().wholeWord("Delta"));

    private static final ScXPath XPATH_SIBLING_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-1")).child("tbody").child("tr", _indexAt(5)).sibling("tr").child("td", _indexAt(0));
    private static final ScXPath XPATH_PRECEDING_SIBLING_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-1")).child("tbody").child("tr", _indexAt(5)).precedingSibling("tr").child("td", _indexAt(0));
    private static final ScXPath XPATH_FOLLOWING_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-1")).child("tbody").child("tr", _indexAt(5)).following("tr").child("td", _indexAt(0));
    private static final ScXPath XPATH_PRECEDING_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-2")).child("tbody").child("tr", _indexAt(0)).preceding("tr").child("td", _indexAt(0));
    private static final ScXPath XPATH_DESCENDANT_TEST_TEXTS = _xpath.descendant("table", _id().is("outer-table-2")).descendant("td");
    private static final ScXPath XPATH_CHILD_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-2")).child("tbody").child("tr").child("td");
    private static final ScXPath XPATH_NOT_TEST_TEXTS = _xpath.descendant("body").child(_not(_id().isPresent()));
    private static final ScXPath XPATH_RAW_TEST_TEXTS = _xpath.raw("//body/h2");

    private static final ScXPath XPATH_BOUNDARY_TEST_TEXTS = _xpath.descendant("tr", _id().is("test-between-elements-title-row1")).following("tr").child("td", _indexOf(0), _boundary(_xpath.descendant("tr", _id().is("test-between-elements-title-row2"))));

```