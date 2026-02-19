package org.emwhyware.selentic.regression.test;

import org.emwhyware.selentic.lib.*;
import org.emwhyware.selentic.regression.page.ScCssSelectorTestPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ScCssSelectorTest {
    private final ScWithPage<ScCssSelectorTestPage> selectorTestPage = ScPage.with(ScCssSelectorTestPage.class);

    @BeforeClass
    public void setup() {
        Selentic.open("file://" + System.getProperty("user.dir") + "/build/resources/test/test_file/selector-test.htm");
    }

    @AfterClass(alwaysRun = true)
    public void shutdown() {
        Selentic.quit();
    }

    @Test
    public void testCssSelectorId() {
        selectorTestPage.inPage(p -> {
            final ScGenericComponent component = p.cssSelectorIdTestText();

            Assert.assertTrue(component.isDisplayed());
            Assert.assertEquals(component.id().orElse(null), "outer-table-1");
        });
    }

    @Test
    public void testCssSelectorTag() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorTagTestTexts();

            Assert.assertEquals(components.texts(), List.of("Main Data Grid", "Auxiliary Reference"));
        });
    }

    @Test
    public void testCssSelectorCssClasses() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorCssClassesTestTexts();

            Assert.assertEquals(components.texts(), List.of("Active", "Active", "Active", "Active", "Active"));
        });
    }

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

    @Test
    public void testCssSelectorNthOfType() {
        selectorTestPage.inPage(p -> {
            final ScGenericComponent componentNthOfType = p.cssSelectorNthOfTypeTestText();
            final ScGenericComponent componentNthLastOfType = p.cssSelectorNthLastOfTypeTestText();
            final ScGenericComponent componentLastOfType = p.cssSelectorLastOfTypeTestText();
            final ScGenericComponent componentLastOfTypeNested = p.cssSelectorLastOfTypeNestedTestText();

            Assert.assertEquals(componentNthOfType.text(), "Auxiliary Reference");
            Assert.assertEquals(componentNthLastOfType.text(), "Main Data Grid");
            Assert.assertEquals(componentLastOfType.text(), "System Logs Backup Status");
            Assert.assertEquals(componentLastOfTypeNested.text(), "Backup Status");
        });
    }

    @Test
    public void testCssSelectorNthChild() {
        selectorTestPage.inPage(p -> {
            final ScGenericComponent componentNthChild = p.cssSelectorNthChildTestText();
            final ScGenericComponent componentNthLastChild = p.cssSelectorNthLastChildTestText();
            final ScGenericComponent componentLastChild = p.cssSelectorLastChildTestText();

            Assert.assertEquals(componentNthChild.text(), "Main Data Grid");
            Assert.assertEquals(componentNthLastChild.text(), "Auxiliary Reference");
            Assert.assertEquals(componentLastChild.text(), "System Logs Backup Status");
        });
    }

    @Test
    public void testCssSelectorDescendent() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorDescendentTestTexts();

            Assert.assertEquals(components.texts(), List.of("CATEGORY", "STATUS", "DETAILS (NESTED)"));
        });
    }

    @Test
    public void testCssSelectorChild() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorChildTestTexts();

            Assert.assertEquals(components.texts(), List.of("System Logs", "Backup Status"));
        });
    }

    @Test
    public void testCssSelectorSibling() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorSiblingTestTexts();

            Assert.assertEquals(components.texts(), List.of("Standard Entry"));
        });
    }

    @Test
    public void testCssSelectorNextSibling() {
        selectorTestPage.inPage(p -> {
            final ScGenericComponent component = p.cssSelectorNextSiblingTestText();

            Assert.assertEquals(component.text(), "Pending");
        });
    }

    @Test
    public void testCssSelectorNot() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorNotTestTexts();

            Assert.assertEquals(components.stream().map(c -> c.id().orElse("")).toList(), List.of("outer-table-1", "outer-table-2"));
        });
    }

    @Test
    public void testCssSelectorRaw() {
        selectorTestPage.inPage(p -> {
            final ScComponentCollection<ScGenericComponent> components = p.cssSelectorRawTestTexts();

            Assert.assertEquals(components.texts(), List.of("Main Data Grid", "Auxiliary Reference"));
        });
    }
}
