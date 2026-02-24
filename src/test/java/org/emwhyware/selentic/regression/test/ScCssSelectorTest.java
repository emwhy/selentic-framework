package org.emwhyware.selentic.regression.test;

import org.emwhyware.selentic.lib.*;
import org.emwhyware.selentic.regression.page.ScCssSelectorTestPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Regression test suite for validating CSS Selector support within the Selentic framework.
 * This class ensures that various CSS locator strategies (IDs, classes, attributes, pseudo-classes,
 * and combinators) correctly resolve.
 */
public class ScCssSelectorTest extends ScBaseTest {

    /** Page object wrapper for the CSS selector test page. */
    private final ScWithPage<ScCssSelectorTestPage> selectorTestPage = ScPage.with(ScCssSelectorTestPage.class);

    /**
     * Initializes the test environment by navigating to the local HTML test file.
     */
    @BeforeClass
    public void setup() {
        Selentic.open("file://" + System.getProperty("user.dir") + "/build/resources/test/test_file/selector-test.htm");
    }

    /**
     * Ensures the browser session is terminated after all tests in this class have executed.
     */
    @AfterClass(alwaysRun = true)
    public void shutdown() {
        Selentic.quit();
    }

    /**
     * Verifies locating an element using a CSS ID selector (#id).
     */
    @Test
    public void testCssSelectorId() {
        selectorTestPage.inPage(p -> {
            final ScGenericComponent component = p.cssSelectorIdTestText();

            Assert.assertTrue(component.isDisplayed());
            Assert.assertEquals(component.id().orElse(""), "outer-table-1");
        });
    }

    /**
     * Verifies locating multiple elements using a CSS Tag selector.
     */
    @Test
    public void testCssSelectorTag() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorTagTestTexts();

            Assert.assertEquals(components.texts(), List.of("Main Data Grid", "Auxiliary Reference", "Between Elements"));
        });
    }

    /**
     * Verifies locating elements using CSS Class selectors (.class).
     */
    @Test
    public void testCssSelectorCssClasses() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorCssClassesTestTexts();

            Assert.assertEquals(components.texts(), List.of("Active", "Active", "Active", "Active", "Active"));
        });
    }

    /**
     * Verifies various attribute-based selectors:
     * <ul>
     *     <li>Starts with: [attr^='val']</li>
     *     <li>Contains: [attr*='val']</li>
     *     <li>Ends with: [attr$='val']</li>
     *     <li>Whole word: [attr~='val']</li>
     * </ul>
     */
    @Test
    public void testCssSelectorAttr() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> componentsStartWith = p.cssSelectorAttrStartWithTestTexts();
            final ScComponentCollection<ScGenericComponent> componentsContains = p.cssSelectorAttrContainsTestTexts();
            final ScComponentCollection<ScGenericComponent> componentsEndsWith = p.cssSelectorAttrEndsWithTestTexts();
            final ScComponentCollection<ScGenericComponent> componentsWholeWords = p.cssSelectorAttrWholeWordTestTexts();

            Assert.assertEquals(componentsStartWith.texts(), List.of("CATEGORY", "STATUS", "DETAILS (NESTED)"));
            Assert.assertEquals(componentsEndsWith.texts(), List.of("CATEGORY", "STATUS", "DETAILS (NESTED)"));
            Assert.assertEquals(componentsContains.texts(), List.of("CATEGORY", "STATUS", "DETAILS (NESTED)"));
            Assert.assertEquals(componentsWholeWords.texts(), List.of("Active", "Pending", "Active", "Warning", "Active", "Critical", "Active", "Active"));
        });
    }

    /**
     * Verifies "of-type" pseudo-classes: :nth-of-type, :nth-last-of-type, and :last-of-type.
     */
    @Test
    public void testCssSelectorNthOfType() {
        selectorTestPage.inPage(p -> {
            final ScGenericComponent componentNthOfType = p.cssSelectorNthOfTypeTestText();
            final ScGenericComponent componentNthLastOfType = p.cssSelectorNthLastOfTypeTestText();
            final ScGenericComponent componentLastOfType = p.cssSelectorLastOfTypeTestText();
            final ScGenericComponent componentLastOfTypeNested = p.cssSelectorLastOfTypeNestedTestText();

            Assert.assertEquals(componentNthOfType.text(), "Auxiliary Reference");
            Assert.assertEquals(componentNthLastOfType.text(), "Auxiliary Reference");
            Assert.assertEquals(componentLastOfType.text(), "Between Elements");
            Assert.assertEquals(componentLastOfTypeNested.text(), "Backup Status");
        });
    }

    /**
     * Verifies "child" pseudo-classes: :nth-child, :nth-last-child, and :last-child.
     */
    @Test
    public void testCssSelectorNthChild() {
        selectorTestPage.inPage(p -> {
            final ScGenericComponent componentNthChild = p.cssSelectorNthChildTestText();
            final ScGenericComponent componentNthLastChild = p.cssSelectorNthLastChildTestText();
            final ScGenericComponent componentLastChild = p.cssSelectorLastChildTestText();

            Assert.assertEquals(componentNthChild.text(), "Main Data Grid");
            Assert.assertEquals(componentNthLastChild.text(), "Between Elements");
            Assert.assertEquals(componentLastChild.text(), "End");
        });
    }

    /**
     * Verifies the descendant combinator (space) for selecting nested elements at any depth.
     */
    @Test
    public void testCssSelectorDescendent() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorDescendentTestTexts();

            Assert.assertEquals(components.texts(), List.of("CATEGORY", "STATUS", "DETAILS (NESTED)"));
        });
    }

    /**
     * Verifies the child combinator (>) for selecting direct children only.
     */
    @Test
    public void testCssSelectorChild() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorChildTestTexts();

            Assert.assertEquals(components.texts(), List.of("System Logs", "Backup Status"));
        });
    }

    /**
     * Verifies the general sibling combinator (~) for selecting siblings following an element.
     */
    @Test
    public void testCssSelectorSibling() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorSiblingTestTexts();

            Assert.assertEquals(components.texts(), List.of("Standard Entry"));
        });
    }

    /**
     * Verifies the adjacent sibling combinator (+) for selecting the immediate next sibling.
     */
    @Test
    public void testCssSelectorNextSibling() {
        selectorTestPage.inPage(p -> {
            final ScGenericComponent component = p.cssSelectorNextSiblingTestText();

            Assert.assertEquals(component.text(), "Pending");
        });
    }

    /**
     * Verifies the negation pseudo-class (:not) to exclude specific elements from a selection.
     */
    @Test
    public void testCssSelectorNot() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorNotTestTexts();

            Assert.assertEquals(components.stream().map(c -> c.id().orElse("")).toList(), List.of("outer-table-1", "outer-table-2", "test-between-elements-table"));
        });
    }

    /**
     * Verifies the handling of raw CSS selector strings passed directly to the locator engine.
     */
    @Test
    public void testCssSelectorRaw() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorRawTestTexts();

            Assert.assertEquals(components.texts(), List.of("Main Data Grid", "Auxiliary Reference", "Between Elements"));
        });
    }
}
