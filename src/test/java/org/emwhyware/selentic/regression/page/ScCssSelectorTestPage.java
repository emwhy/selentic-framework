package org.emwhyware.selentic.regression.page;

import org.emwhyware.selentic.lib.ScComponentCollection;
import org.emwhyware.selentic.lib.ScGenericComponent;
import org.emwhyware.selentic.lib.ScPage;
import org.emwhyware.selentic.lib.selector.ScCssSelector;

public class ScCssSelectorTestPage extends ScPage {
    private static final ScCssSelector CSS_SELECTOR_OUTER_TABLE2 = _cssSelector.descendant(_id("outer-table-2"));
    private static final ScCssSelector CSS_SELECTOR_ID_TEST_TEXT = _cssSelector.descendant(_id("outer-table-1"));
    private static final ScCssSelector CSS_SELECTOR_TAG_TEST_TEXTS = _cssSelector.descendant("h2");
    private static final ScCssSelector CSS_SELECTOR_CSS_CLASSES_TEST_TEXTS = _cssSelector.descendant(_cssClasses("status", "active"));
    private static final ScCssSelector CSS_SELECTOR_ATTR_START_WITH_TEST_TEXTS = _cssSelector.descendant(_attr("scope").startsWith("co"));
    private static final ScCssSelector CSS_SELECTOR_ATTR_ENDS_WITH_TEST_TEXTS = _cssSelector.descendant(_attr("scope").endsWith("ol"));
    private static final ScCssSelector CSS_SELECTOR_ATTR_CONTAINS_TEST_TEXTS = _cssSelector.descendant(_attr("scope").contains("ol"));
    private static final ScCssSelector CSS_SELECTOR_ATTR_WHOLE_WORD_TEST_TEXTS = _cssSelector.descendant(_attr("class").wholeWord("status"));
    private static final ScCssSelector CSS_SELECTOR_NTH_OF_TYPE_TEST_TEXT = _cssSelector.descendant("body").child("h2", _nthOfType(1));
    private static final ScCssSelector CSS_SELECTOR_NTH_LAST_OF_TYPE_TEST_TEXT = _cssSelector.descendant("body").child("h2", _nthLastOfType(1));
    private static final ScCssSelector CSS_SELECTOR_LAST_OF_TYPE_TEST_TEXT = _cssSelector.descendant("body").child("h2", _lastOfType());
    private static final ScCssSelector CSS_SELECTOR_NTH_CHILD_TEST_TEXT = _cssSelector.descendant("body").child(_nthChild(0));
    private static final ScCssSelector CSS_SELECTOR_NTH_LAST_CHILD_TEST_TEXT = _cssSelector.descendant("body").child(_nthLastChild(1));
    private static final ScCssSelector CSS_SELECTOR_LAST_CHILD_TEST_TEXT = _cssSelector.descendant("table", _id().is("test-between-elements-table")).child("tbody").child(_lastChild());
    private static final ScCssSelector CSS_SELECTOR_LAST_OF_TYPE_NESTED_TEST_TEXT = CSS_SELECTOR_OUTER_TABLE2.child("tbody").descendant(_nthChild(1));
    private static final ScCssSelector CSS_SELECTOR_SIBLING_TEST_TEXTS = _cssSelector.descendant(_id("main-r2-c1")).sibling("td", _not(_attr("class").isPresent()));
    private static final ScCssSelector CSS_SELECTOR_NEXT_SIBLING_TEST_TEXT = _cssSelector.descendant(_id("main-r2-c1")).nextSibling("td");
    private static final ScCssSelector CSS_SELECTOR_DESCENDANT_TEST_TEXTS = _cssSelector.descendant("th");
    private static final ScCssSelector CSS_SELECTOR_CHILD_TEST_TEXTS = CSS_SELECTOR_OUTER_TABLE2.child("tbody").child("tr").child("td");
    private static final ScCssSelector CSS_SELECTOR_NOT_TEST_TEXTS = _cssSelector.descendant("body").child(_not(_tag("h2")));
    private static final ScCssSelector CSS_SELECTOR_RAW_TEST_TEXTS = _cssSelector.raw("body > :not(table)");

    public ScGenericComponent cssSelectorIdTestText() {
        return $genericComponent(CSS_SELECTOR_ID_TEST_TEXT);
    }

    public ScComponentCollection<ScGenericComponent> cssSelectorTagTestTexts() {
        return $$components(CSS_SELECTOR_TAG_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> cssSelectorCssClassesTestTexts() {
        return $$components(CSS_SELECTOR_CSS_CLASSES_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> cssSelectorAttrStartWithTestTexts() {
        return $$components(CSS_SELECTOR_ATTR_START_WITH_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> cssSelectorAttrEndsWithTestTexts() {
        return $$components(CSS_SELECTOR_ATTR_ENDS_WITH_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> cssSelectorAttrContainsTestTexts() {
        return $$components(CSS_SELECTOR_ATTR_CONTAINS_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> cssSelectorAttrWholeWordTestTexts() {
        return $$components(CSS_SELECTOR_ATTR_WHOLE_WORD_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScGenericComponent cssSelectorNthOfTypeTestText() {
        return $genericComponent(CSS_SELECTOR_NTH_OF_TYPE_TEST_TEXT);
    }

    public ScGenericComponent cssSelectorNthLastOfTypeTestText() {
        return $genericComponent(CSS_SELECTOR_NTH_LAST_OF_TYPE_TEST_TEXT);
    }

    public ScGenericComponent cssSelectorLastOfTypeTestText() {
        return $genericComponent(CSS_SELECTOR_LAST_OF_TYPE_TEST_TEXT);
    }

    public ScGenericComponent cssSelectorNthChildTestText() {
        return $genericComponent(CSS_SELECTOR_NTH_CHILD_TEST_TEXT);
    }

    public ScGenericComponent cssSelectorNthLastChildTestText() {
        return $genericComponent(CSS_SELECTOR_NTH_LAST_CHILD_TEST_TEXT);
    }

    public ScGenericComponent cssSelectorLastChildTestText() {
        return $genericComponent(CSS_SELECTOR_LAST_CHILD_TEST_TEXT);
    }

    public ScGenericComponent cssSelectorLastOfTypeNestedTestText() {
        return $genericComponent(CSS_SELECTOR_LAST_OF_TYPE_NESTED_TEST_TEXT);
    }

    public ScComponentCollection<ScGenericComponent> cssSelectorSiblingTestTexts() {
        return $$components(CSS_SELECTOR_SIBLING_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScGenericComponent cssSelectorNextSiblingTestText() {
        return $genericComponent(CSS_SELECTOR_NEXT_SIBLING_TEST_TEXT);
    }

    public ScComponentCollection<ScGenericComponent> cssSelectorDescendentTestTexts() {
        return $$components(CSS_SELECTOR_DESCENDANT_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> cssSelectorChildTestTexts() {
        return $$components(CSS_SELECTOR_CHILD_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> cssSelectorNotTestTexts() {
        return $$components(CSS_SELECTOR_NOT_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> cssSelectorRawTestTexts() {
        return $$components(CSS_SELECTOR_RAW_TEST_TEXTS, ScGenericComponent.class);
    }

}
