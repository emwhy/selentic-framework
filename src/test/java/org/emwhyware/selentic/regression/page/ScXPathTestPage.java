package org.emwhyware.selentic.regression.page;

import org.emwhyware.selentic.lib.ScComponentCollection;
import org.emwhyware.selentic.lib.ScXPath;
import org.emwhyware.selentic.lib.ScGenericComponent;
import org.emwhyware.selentic.lib.ScPage;

public class ScXPathTestPage extends ScPage {
    private static final ScXPath XPATH_ID_TEST_TEXT = _xpath.descendant(_id().is("outer-table-1"));
    private static final ScXPath XPATH_TAG_TEST_TEXTS = _xpath.descendant("h2");
    private static final ScXPath XPATH_CSS_CLASSES_TEST_TEXTS = _xpath.descendant(_cssClasses("status", "active"));
    private static final ScXPath XPATH_ATTR_IS_TEST_TEXTS = _xpath.descendant(_attr("scope").is("col"));
    private static final ScXPath XPATH_ATTR_START_WITH_TEST_TEXTS = _xpath.descendant(_attr("scope").startsWith("co"));
    private static final ScXPath XPATH_ATTR_ENDS_WITH_TEST_TEXTS = _xpath.descendant(_attr("scope").endsWith("ol"));
    private static final ScXPath XPATH_ATTR_CONTAINS_TEST_TEXTS = _xpath.descendant(_attr("scope").contains("ol"));
    private static final ScXPath XPATH_ATTR_WHOLE_WORD_TEST_TEXTS = _xpath.descendant(_attr("class").wholeWord("status"));
    private static final ScXPath XPATH_INDEX_TEST_TEXTS = _xpath.descendant(_id().is("outer-table-1")).child("tbody").child("tr", _indexFrom(3), _indexTo(6)).child("td", _indexAt(0));

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

    public ScGenericComponent xPathIdTestText() {
        return $genericComponent(XPATH_ID_TEST_TEXT);
    }

    public ScComponentCollection<ScGenericComponent> xPathTagTestTexts() {
        return $$components(XPATH_TAG_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathCssClassesTestTexts() {
        return $$components(XPATH_CSS_CLASSES_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathAttrTestIsTexts() {
        return $$components(XPATH_ATTR_IS_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathAttrStartWithTestTexts() {
        return $$components(XPATH_ATTR_START_WITH_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathAttrEndsWithTestTexts() {
        return $$components(XPATH_ATTR_ENDS_WITH_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathAttrContainsTestTexts() {
        return $$components(XPATH_ATTR_CONTAINS_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathAttrWholeWordTestTexts() {
        return $$components(XPATH_ATTR_WHOLE_WORD_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathIndexTestTexts() {
        return $$components(XPATH_INDEX_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathTextTestIsTexts() {
        return $$components(XPATH_TEXT_TEST_IS_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathTextTestStartsWithTexts() {
        return $$components(XPATH_TEXT_TEST_STARTS_WITH_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathTextTestEndsWithTexts() {
        return $$components(XPATH_TEXT_TEST_ENDS_WITH_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathTextTestContainsTexts() {
        return $$components(XPATH_TEXT_TEST_CONTAINS_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathTextTestWholeWordTexts() {
        return $$components(XPATH_TEXT_TEST_WHOLE_WORD_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathSiblingTestTexts() {
        return $$components(XPATH_SIBLING_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathPrecedingSiblingTestTexts() {
        return $$components(XPATH_PRECEDING_SIBLING_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathFollowingTestTexts() {
        return $$components(XPATH_FOLLOWING_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathPrecedingTestTexts() {
        return $$components(XPATH_PRECEDING_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathDescendentTestTexts() {
        return $$components(XPATH_DESCENDANT_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathChildTestTexts() {
        return $$components(XPATH_CHILD_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathNotTestTexts() {
        return $$components(XPATH_NOT_TEST_TEXTS, ScGenericComponent.class);
    }

    public ScComponentCollection<ScGenericComponent> xPathRawTestTexts() {
        return $$components(XPATH_RAW_TEST_TEXTS, ScGenericComponent.class);
    }

}
