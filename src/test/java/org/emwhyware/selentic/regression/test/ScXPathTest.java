package org.emwhyware.selentic.regression.test;

import org.emwhyware.selentic.lib.*;
import org.emwhyware.selentic.regression.page.ScXPathTestPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ScXPathTest extends ScBaseTest {
    private final ScWithPage<ScXPathTestPage> selectorTestPage = ScPage.with(ScXPathTestPage.class);

    @BeforeClass
    public void setup() {
        Selentic.open("file://" + System.getProperty("user.dir") + "/build/resources/test/test_file/selector-test.htm");
    }

    @AfterClass(alwaysRun = true)
    public void shutdown() {
        Selentic.quit();
    }

    @Test
    public void testXPathId() {
        selectorTestPage.inPage(p -> {
            final ScGenericComponent component = p.xPathIdTestText();

            Assert.assertTrue(component.isDisplayed());
            Assert.assertEquals(component.id().orElse(""), "outer-table-1");
        });
    }

    @Test
    public void testXPathTag() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathTagTestTexts();

            Assert.assertEquals(components.texts(), List.of("Main Data Grid", "Auxiliary Reference"));
        });
    }

    @Test
    public void testXPathXPathClasses() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathCssClassesTestTexts();

            Assert.assertEquals(components.texts(), List.of("Active", "Active", "Active", "Active", "Active"));
        });
    }

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

    @Test
    public void testXPathDescendent() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathDescendentTestTexts();

            Assert.assertEquals(components.texts(), List.of("System Logs", "Backup Status"));
        });
    }

    @Test
    public void testXPathChild() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathChildTestTexts();

            Assert.assertEquals(components.texts(), List.of("System Logs", "Backup Status"));
        });
    }

    @Test
    public void testXPathSibling() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathSiblingTestTexts();

            Assert.assertEquals(components.texts(), List.of("Septary Eta", "Octary Theta", "Nonary Iota", "Decary Kappa", "Unit Lambda", "Node Mu"));
        });
    }

    @Test
    public void testXPathPrecedingSibling() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathPrecedingSiblingTestTexts();

            Assert.assertEquals(components.texts(), List.of("Primary Alpha", "Secondary Beta", "Tertiary Gamma", "Quaternary Delta", "Pentary Epsilon"));
        });
    }

    @Test
    public void testXPathFollowing() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathFollowingTestTexts();

            Assert.assertEquals(components.texts(), List.of("Septary Eta", "Octary Theta", "Error Code", "Nonary Iota", "Decary Kappa", "Unit Lambda", "Node Mu", "Throughput", "System Logs"));
        });
    }

    @Test
    public void testXPathPreceding() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathPrecedingTestTexts();

            Assert.assertEquals(components.texts(), List.of("Primary Alpha", "Sub-Part A1", "Sub-Part A2", "Secondary Beta", "Tertiary Gamma", "Sensor 04", "Quaternary Delta", "Pentary Epsilon", "Voltage", "Hexary Zeta", "Septary Eta", "Octary Theta", "Error Code", "Nonary Iota", "Decary Kappa", "Unit Lambda", "Node Mu", "Throughput"));
        });
    }

    @Test
    public void testXPathNot() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathNotTestTexts();

            Assert.assertEquals(components.texts(), List.of("Main Data Grid", "Auxiliary Reference"));
        });
    }

    @Test
    public void testXPathRaw() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.xPathRawTestTexts();

            Assert.assertEquals(components.texts(), List.of("Main Data Grid", "Auxiliary Reference"));
        });
    }
}
