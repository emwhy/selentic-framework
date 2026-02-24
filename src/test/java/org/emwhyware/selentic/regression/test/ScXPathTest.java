package org.emwhyware.selentic.regression.test;

import org.emwhyware.selentic.lib.*;
import org.emwhyware.selentic.regression.component.ScXPathSelectorTestTable;
import org.emwhyware.selentic.regression.page.ScXPathTestPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Regression test suite for validating XPath locator support within the Selentic framework.
 * This class ensures that complex XPath expressions, including axes (sibling, preceding, following)
 * and string functions, correctly resolve.
 */
public class ScXPathTest extends ScBaseTest {

    /** Page object wrapper for the XPath selector test page. */
    private final ScWithPage<ScXPathTestPage> selectorTestPage = ScPage.with(ScXPathTestPage.class);

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
     * Verifies locating an element using an ID attribute via XPath (//*[@id='...']).
     */
    @Test
    public void testXPathId() {
        selectorTestPage.inPage(p -> {
            final ScGenericComponent component = p.xPathIdTestText();

            Assert.assertTrue(component.isDisplayed());
            Assert.assertEquals(component.id().orElse(""), "outer-table-1");
        });
    }

    /**
     * Verifies locating multiple elements using a Tag name via XPath.
     */
    @Test
    public void testXPathTag() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathTagTestTexts();

            Assert.assertEquals(components.texts(), List.of("Main Data Grid", "Auxiliary Reference", "Between Elements"));
        });
    }

    /**
     * Verifies locating elements with CSS classes in XPath.
     */
    @Test
    public void testXPathXPathClasses() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathCssClassesTestTexts();

            Assert.assertEquals(components.texts(), List.of("Active", "Active", "Active", "Active", "Active"));
        });
    }

    /**
     * Verifies various attribute matching strategies in XPath:
     * <ul>
     *     <li>Exact match: [@attr='val']</li>
     *     <li>Starts with: starts-with(@attr, 'val')</li>
     *     <li>Contains: contains(@attr, 'val')</li>
     *     <li>Ends with: substring(@attr, string-length(@attr) - ...)</li>
     * </ul>
     */
    @Test
    public void testXPathAttr() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> componentsIs = p.xPathAttrTestIsTexts();
            final ScComponentCollection<ScGenericComponent> componentsStartWith = p.xPathAttrStartWithTestTexts();
            final ScComponentCollection<ScGenericComponent> componentsContains = p.xPathAttrContainsTestTexts();
            final ScComponentCollection<ScGenericComponent> componentsEndsWith = p.xPathAttrEndsWithTestTexts();
            final ScComponentCollection<ScGenericComponent> componentsWholeWords = p.xPathAttrWholeWordTestTexts();

            Assert.assertEquals(componentsIs.texts(), List.of("CATEGORY", "STATUS", "DETAILS (NESTED)"));
            Assert.assertEquals(componentsStartWith.texts(), List.of("CATEGORY", "STATUS", "DETAILS (NESTED)"));
            Assert.assertEquals(componentsEndsWith.texts(), List.of("CATEGORY", "STATUS", "DETAILS (NESTED)"));
            Assert.assertEquals(componentsContains.texts(), List.of("CATEGORY", "STATUS", "DETAILS (NESTED)"));
            Assert.assertEquals(componentsWholeWords.texts(), List.of("Active", "Pending", "Active", "Warning", "Active", "Critical", "Active", "Active"));
        });
    }

    /**
     * Verifies positional indexing in XPath, including [n], position(), and last() functions.
     */
    @Test
    public void testXPathIndex() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathIndexTestTexts();
            final ScGenericComponent componentFirst = p.xPathFirstText();
            final ScGenericComponent componentLast = p.xPathLastText();

            Assert.assertEquals(components.texts(), List.of("Quaternary Delta", "Pentary Epsilon", "Hexary Zeta", "Septary Eta", "Octary Theta", "Nonary Iota", "Decary Kappa"));
            Assert.assertEquals(componentFirst.text(), "Primary Alpha");
            Assert.assertEquals(componentLast.text(), "Node Mu");
        });
    }

    /**
     * Verifies locating elements based on their inner text using:
     * <ul>
     *     <li>text()='val'</li>
     *     <li>contains(text(), 'val')</li>
     *     <li>starts-with(text(), 'val')</li>
     * </ul>
     */
    @Test
    public void testXPathText() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> componentsIs = p.xPathTextTestIsTexts();
            final ScComponentCollection<ScGenericComponent> componentsStartWith = p.xPathTextTestStartsWithTexts();
            final ScComponentCollection<ScGenericComponent> componentsContains = p.xPathTextTestContainsTexts();
            final ScComponentCollection<ScGenericComponent> componentsEndsWith = p.xPathTextTestEndsWithTexts();
            final ScComponentCollection<ScGenericComponent> componentsWholeWords = p.xPathTextTestWholeWordTexts();

            Assert.assertEquals(componentsIs.texts(), List.of("Primary Alpha", "Quaternary Delta", "Septary Eta", "Nonary Iota", "Node Mu"));
            Assert.assertEquals(componentsStartWith.texts(), List.of("Primary Alpha", "Quaternary Delta", "Septary Eta", "Nonary Iota", "Node Mu"));
            Assert.assertEquals(componentsEndsWith.texts(), List.of("Tertiary Gamma", "Hexary Zeta"));
            Assert.assertEquals(componentsContains.texts(), List.of("Pentary Epsilon"));
            Assert.assertEquals(componentsWholeWords.texts(), List.of("Quaternary Delta"));
        });
    }

    /**
     * Verifies the descendant axis (// or /descendent::) for finding nested elements.
     */
    @Test
    public void testXPathDescendent() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathDescendentTestTexts();

            Assert.assertEquals(components.texts(), List.of("System Logs", "Backup Status"));
        });
    }

    /**
     * Verifies the child axis (/ or /child::) for finding direct child elements.
     */
    @Test
    public void testXPathChild() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathChildTestTexts();

            Assert.assertEquals(components.texts(), List.of("System Logs", "Backup Status"));
        });
    }

    /**
     * Verifies the following-sibling (/following-sibling::) axis.
     */
    @Test
    public void testXPathSibling() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathSiblingTestTexts();

            Assert.assertEquals(components.texts(), List.of("Septary Eta", "Octary Theta", "Nonary Iota", "Decary Kappa", "Unit Lambda", "Node Mu"));
        });
    }

    /**
     * Verifies the preceding-sibling (/preceding-sibling::) axis.
     */
    @Test
    public void testXPathPrecedingSibling() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathPrecedingSiblingTestTexts();

            Assert.assertEquals(components.texts(), List.of("Primary Alpha", "Secondary Beta", "Tertiary Gamma", "Quaternary Delta", "Pentary Epsilon"));
        });
    }

    /**
     * Verifies the following (/following::) axis.
     */
    @Test
    public void testXPathFollowing() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathFollowingTestTexts();

            Assert.assertEquals(components.texts(), List.of("Septary Eta", "Octary Theta", "Error Code", "Nonary Iota", "Decary Kappa", "Unit Lambda", "Node Mu", "Throughput", "System Logs", "Title 1", "C1-1", "C1-2", "C1-3", "C1-4", "C1-5", "Title 2", "C2-1", "C2-2", "C2-3", "C2-4", "C2-5", "Title 3", "C3-1", "C3-2", "C3-3", "C3-4", "C3-5", "End"));
        });
    }

    /**
     * Verifies the preceding (/preceding::) axis.
     */
    @Test
    public void testXPathPreceding() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathPrecedingTestTexts();

            Assert.assertEquals(components.texts(), List.of("Primary Alpha", "Sub-Part A1", "Sub-Part A2", "Secondary Beta", "Tertiary Gamma", "Sensor 04", "Quaternary Delta", "Pentary Epsilon", "Voltage", "Hexary Zeta", "Septary Eta", "Octary Theta", "Error Code", "Nonary Iota", "Decary Kappa", "Unit Lambda", "Node Mu", "Throughput"));
        });
    }

    @Test
    public void testXPathLimitedBy() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathLimitedByTestTexts();
            final ScXPathSelectorTestTable table = p.xPathSelectorTable();

            Assert.assertEquals(components.texts(), List.of("C1-1", "C1-2", "C1-3", "C1-4", "C1-5"));
            Assert.assertEquals(table.xPathLimitedByTestTexts().texts(), List.of("C1-3", "C1-4", "C1-5"));
        });

    }

    /**
     * Verifies the use of the not() function to exclude specific nodes.
     */
    @Test
    public void testXPathNot() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathNotTestTexts();

            Assert.assertEquals(components.texts(), List.of("Main Data Grid", "Auxiliary Reference", "Between Elements"));
        });
    }

    /**
     * Verifies that raw XPath strings are correctly processed by the underlying engine.
     */
    @Test
    public void testXPathRaw() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathRawTestTexts();

            Assert.assertEquals(components.texts(), List.of("Main Data Grid", "Auxiliary Reference", "Between Elements"));
        });
    }
}
