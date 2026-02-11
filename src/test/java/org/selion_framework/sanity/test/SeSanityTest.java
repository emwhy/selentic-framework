package org.selion_framework.sanity.test;

import org.selion_framework.lib.SePage;
import org.selion_framework.lib.SeWithPage;
import org.selion_framework.lib.Selion;
import org.selion_framework.lib.exception.SeWindowException;
import org.selion_framework.lib.util.SeDownloadCsvFileParser;
import org.selion_framework.sanity.component.SeSanityTestLongListEntryComponent;
import org.selion_framework.sanity.component.SeSanityTestTableRow;
import org.selion_framework.sanity.page.SeSanityTestExternalPage;
import org.selion_framework.sanity.page.SeSanityTestPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

public class SeSanityTest {
    private final SeWithPage<SeSanityTestPage> sanitytestPage = SePage.with(SeSanityTestPage.class);
    private final SeWithPage<SeSanityTestExternalPage> sanitytestExternalPage = SePage.with(SeSanityTestExternalPage.class);

    @BeforeClass 
    public void setup() {
        Selion.open("file://" + System.getProperty("user.dir") + "/build/resources/test/sanitytest_file/sanitytest.htm");
    }

    @AfterMethod(alwaysRun = true)
    public void reload() {
        sanitytestPage.inPage(SePage::reload);
    }

    @AfterClass(alwaysRun = true)
    public void shutdown() {
        Selion.quit();
    }

    @Test
    public void testTextbox() {
        sanitytestPage.inPage(p -> {
            Assert.assertEquals(p.sanitytestTextbox.text(), "textbox text");

            p.sanitytestTextbox.enterText("this is a test");

            Assert.assertEquals(p.sanitytestTextbox.text(), "this is a test");
        });
    }

    @Test
    public void testTextarea() {
        sanitytestPage.inPage(p -> {
            Assert.assertEquals(p.sanitytestTextarea.text(), "textarea text");

            p.sanitytestTextarea.enterText("test text");

            Assert.assertEquals(p.sanitytestTextarea.text(), "test text");
        });
    }

    @Test
    public void testDateTextbox() {
        LocalDate futureDate = LocalDate.now().plusDays(8);

        sanitytestPage.inPage(p -> {
            Assert.assertEquals(p.sanitytestDateTextbox.text(), "");

            p.sanitytestDateTextbox.enterDate(futureDate);

            Assert.assertEquals(p.sanitytestDateTextbox.text(), futureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            Assert.assertEquals(p.sanitytestDateTextbox.parse(), futureDate);
        });
    }

    @Test
    public void testDropdown() {
        sanitytestPage.inPage(p -> {
            final List<String> options = p.sanitytestDropdown.optionTexts();

            for (int i = 1; i <= 40; i++) {
                Assert.assertEquals(options.get(i-1), "TEST " + i);
            }

            Assert.assertEquals(p.sanitytestDropdown.selectedText(), "TEST 1");

            p.sanitytestDropdown.select("TEST 35");

            Assert.assertEquals(p.sanitytestDropdown.selectedText(), "TEST 35");

            p.sanitytestDropdown.selectEndsWith("23");

            Assert.assertEquals(p.sanitytestDropdown.selectedText(), "TEST 23");
        });

    }

    @Test
    public void testMultiSelect() {
        sanitytestPage.inPage(p -> {
            final List<String> options = p.sanitytestMultiSelect.optionTexts();

            for (int i = 1; i <= 40; i++) {
                Assert.assertEquals(options.get(i-1), "TEST " + i);
            }

            Assert.assertTrue(p.sanitytestMultiSelect.selectedTexts().isEmpty());

            p.sanitytestMultiSelect.select("TEST 31");
            p.sanitytestMultiSelect.select("TEST 21");
            p.sanitytestMultiSelect.select("TEST 11");
            p.sanitytestMultiSelect.select("TEST 9");

            Assert.assertListContainsObject(p.sanitytestMultiSelect.selectedTexts(), "TEST 11", "");
            Assert.assertListContainsObject(p.sanitytestMultiSelect.selectedTexts(), "TEST 21", "");
            Assert.assertListContainsObject(p.sanitytestMultiSelect.selectedTexts(), "TEST 9", "");
            Assert.assertListContainsObject(p.sanitytestMultiSelect.selectedTexts(), "TEST 31", "");

            p.sanitytestMultiSelect.deselect("TEST 11");
            p.sanitytestMultiSelect.deselect("TEST 21");

            Assert.assertListContainsObject(p.sanitytestMultiSelect.selectedTexts(), "TEST 9", "");
            Assert.assertListContainsObject(p.sanitytestMultiSelect.selectedTexts(), "TEST 31", "");
            Assert.assertListNotContainsObject(p.sanitytestMultiSelect.selectedTexts(), "TEST 11", "");
            Assert.assertListNotContainsObject(p.sanitytestMultiSelect.selectedTexts(), "TEST 21", "");

            p.sanitytestMultiSelect.clear();

            assertTrue(p.sanitytestMultiSelect.selectedTexts().isEmpty());

            p.sanitytestMultiSelect.selectStartWith("TEST 4");

            Assert.assertListContainsObject(p.sanitytestMultiSelect.selectedTexts(), "TEST 4", "");
            Assert.assertListContainsObject(p.sanitytestMultiSelect.selectedTexts(), "TEST 40", "");

            p.sanitytestMultiSelect.deselectEndWith("4");

            Assert.assertListContainsObject(p.sanitytestMultiSelect.selectedTexts(), "TEST 40", "");
            Assert.assertListNotContainsObject(p.sanitytestMultiSelect.selectedTexts(), "TEST 4", "");
        });
    }

    @Test
    public void testCheckbox() {
        sanitytestPage.inPage(p -> {
            Assert.assertTrue(p.sanitytestCheckbox.isSelected());

            p.sanitytestCheckbox.deselect();

            Assert.assertFalse(p.sanitytestCheckbox.isSelected());

            p.sanitytestCheckbox.select();

            Assert.assertTrue(p.sanitytestCheckbox.isSelected());

            Assert.assertEquals(p.sanitytestCheckbox.text(), "Test CheckBox");
        });
    }

    @Test
    public void testRadioButtons() {
        sanitytestPage.inPage(p -> {
            final List<String> texts = p.sanitytestRadioButtons.texts();

            Assert.assertListContainsObject(texts, "Test Radio 1", "");
            Assert.assertListContainsObject(texts, "Test Radio 2", "");
            Assert.assertListContainsObject(texts, "Test Radio 3", "");
            Assert.assertListContainsObject(texts, "Test Radio 4", "");
            Assert.assertListContainsObject(texts, "Test Radio 5", "");

            Assert.assertEquals(p.sanitytestRadioButtons.selectedText(), "Test Radio 2");

            p.sanitytestRadioButtons.select("Test Radio 5");

            Assert.assertEquals(p.sanitytestRadioButtons.selectedText(), "Test Radio 5");
        });
    }

    @Test
    public void testInputButton() {
        sanitytestPage.inPage(p -> {
            Assert.assertEquals(p.sanitytestInputButton.text(), "SanityTest Input Button");

            p.sanitytestInputButton.click();

            Assert.assertEquals(p.sanitytestInputButtonIndicatorText.text(), "Clicked!");
        });
    }

    @Test
    public void testButton() {
        sanitytestPage.inPage(p -> {
            Assert.assertEquals(p.sanitytestButton.text(), "SanityTest Button");

            p.sanitytestButton.click();

            Assert.assertEquals(p.sanitytestButtonIndicatorText.text(), "Clicked!");
        });
    }

    @Test
    public void testTableRows() {
        sanitytestPage.inPage(p -> {
            final List<SeSanityTestTableRow> filteredRow = p.sanitytestTableRows.filter(r -> r.productTypeText.text().equals("Type 1"));

            Assert.assertEquals(p.sanitytestTableRows.size(), 3);

            Assert.assertEquals(p.sanitytestTableRows.at(0).text(), "SanityTest 1");
            Assert.assertEquals(p.sanitytestTableRows.at(1).text(), "SanityTest 2");
            Assert.assertEquals(p.sanitytestTableRows.at(2).text(), "SanityTest 3");

            Assert.assertEquals(p.sanitytestTableRows.entry("SanityTest 1").serialNumberText.text(), "#TDD987");
            Assert.assertEquals(p.sanitytestTableRows.entry("SanityTest 2").serialNumberText.text(), "#AEV974");
            Assert.assertEquals(p.sanitytestTableRows.entry("SanityTest 3").serialNumberText.text(), "#CCA106");

            Assert.assertEquals(filteredRow.size(), 2);
            Assert.assertEquals(filteredRow.get(0).productNameText.text(), "SanityTest 1");
            Assert.assertEquals(filteredRow.get(1).productNameText.text(), "SanityTest 3");
            Assert.assertEquals(filteredRow.get(0).builtDateText.text(), "1/4/2024");
            Assert.assertEquals(filteredRow.get(1).builtDateText.text(), "10/5/2011");
            Assert.assertEquals(filteredRow.get(0).priceAmountText.text(), "$99.99");
            Assert.assertEquals(filteredRow.get(1).priceAmountText.text(), "$1,002.89");
        });
    }

    @Test
    public void testFrame() {
        sanitytestPage.inPage(p -> {
            p.inSanityTestInnerFrame(frameContent -> {
                Assert.assertEquals(frameContent.sanitytestExternalTextbox.text(), "external textbox text");

                Assert.assertEquals(frameContent.sanitytestExternalTableRows.size(), 3);

                Assert.assertEquals(frameContent.sanitytestExternalTableRows.at(0).text(), "External SanityTest 1");
                Assert.assertEquals(frameContent.sanitytestExternalTableRows.at(1).text(), "External SanityTest 2");
                Assert.assertEquals(frameContent.sanitytestExternalTableRows.at(2).text(), "External SanityTest 3");

                Assert.assertEquals(frameContent.sanitytestExternalTableRows.entry("External SanityTest 1").serialNumberText.text(), "#EX-TDD987");
                Assert.assertEquals(frameContent.sanitytestExternalTableRows.entry("External SanityTest 2").serialNumberText.text(), "#EX-AEV974");
                Assert.assertEquals(frameContent.sanitytestExternalTableRows.entry("External SanityTest 3").serialNumberText.text(), "#EX-CCA106");
            });
        });
    }

    @Test
    public void testDialog() {
        sanitytestPage.inPage(p -> {
            p.openSanityTestDialogButton.click();
            p.inSanityTestDialog(dialog -> {
                Assert.assertEquals(dialog.textbox.text(), "sanitytest dialog text");

                dialog.textbox.enterText("Test text in dialog");

                Assert.assertEquals(dialog.textbox.text(), "Test text in dialog");

                dialog.closeButton.click();
            });
        });

    }

    @Test
    public void testModalDialog() {
        sanitytestPage.inPage(p -> {
            p.openSanityTestModalDialogButton.click();
            p.inSanityTestModalDialog(dialog -> {
                Assert.assertEquals(dialog.textbox.text(), "sanitytest modal dialog text");

                dialog.textbox.enterText("Test test in modal dialog");

                Assert.assertEquals(dialog.textbox.text(), "Test test in modal dialog");

                dialog.closeButton.click();
            });
        });

    }

    @Test
    public void testExternalWindows() {
        sanitytestPage.inPage(p -> {

            // Ensure that calling "inWindow()" without having a window open would cause
            // exception.
            try {
                p.inWindow(() -> {
                    fail("Should have thrown an exception");
                });
                fail("Should have thrown an exception");
            } catch (SeWindowException ex) {
                Assert.assertEquals(ex.getMessage(), "New window has not opened");
            }

            // Open a new window.
            p.openExternalWindowLink.click();
            p.inWindow(() -> {
                sanitytestExternalPage.inPage(externalPage -> {

                    // Open another new window.
                    externalPage.openExternalWindowLink.click();
                    externalPage.inWindow(controller -> {

                        // Controller allows temporary switching control to other windows
                        // without closing them, then return the control back to the original
                        // window.
                        controller.inOtherWindow(0, () -> {
                            // Ensure that the focused window is the root one.
                            Assert.assertEquals(p.sanitytestTableRows.entry("SanityTest 1").serialNumberText.text(), "#TDD987");
                            Assert.assertEquals(p.sanitytestTableRows.entry("SanityTest 2").serialNumberText.text(), "#AEV974");
                            Assert.assertEquals(p.sanitytestTableRows.entry("SanityTest 3").serialNumberText.text(), "#CCA106");

                            Assert.assertEquals(controller.windowCount(), 3);
                        });
                        Assert.assertEquals(controller.windowCount(), 3);

                        // Assertion on the external window after the control is returned from
                        // "the other window".
                        Assert.assertEquals(externalPage.sanitytestExternalTextbox.text(), "external textbox text");

                        Assert.assertEquals(externalPage.sanitytestExternalTableRows.size(), 3);

                        Assert.assertEquals(externalPage.sanitytestExternalTableRows.at(0).text(), "External SanityTest 1");
                        Assert.assertEquals(externalPage.sanitytestExternalTableRows.at(1).text(), "External SanityTest 2");
                        Assert.assertEquals(externalPage.sanitytestExternalTableRows.at(2).text(), "External SanityTest 3");

                        Assert.assertEquals(externalPage.sanitytestExternalTableRows.entry("External SanityTest 1").serialNumberText.text(), "#EX-TDD987");
                        Assert.assertEquals(externalPage.sanitytestExternalTableRows.entry("External SanityTest 2").serialNumberText.text(), "#EX-AEV974");
                        Assert.assertEquals(externalPage.sanitytestExternalTableRows.entry("External SanityTest 3").serialNumberText.text(), "#EX-CCA106");

                        // Close the current window.
                        externalPage.closeCurrentWindowButton.click();
                    });

                    // Ensure that "inWindow" predicate ended properly despite that the window was
                    // closed before its end.
                    Assert.assertEquals(Selion.driver().getWindowHandles().size(), 2);

                    Assert.assertEquals(externalPage.sanitytestExternalTextbox.text(), "external textbox text");

                    Assert.assertEquals(externalPage.sanitytestExternalTableRows.size(), 3);

                    Assert.assertEquals(externalPage.sanitytestExternalTableRows.at(0).text(), "External SanityTest 1");
                    Assert.assertEquals(externalPage.sanitytestExternalTableRows.at(1).text(), "External SanityTest 2");
                    Assert.assertEquals(externalPage.sanitytestExternalTableRows.at(2).text(), "External SanityTest 3");

                    Assert.assertEquals(externalPage.sanitytestExternalTableRows.entry("External SanityTest 1").serialNumberText.text(), "#EX-TDD987");
                    Assert.assertEquals(externalPage.sanitytestExternalTableRows.entry("External SanityTest 2").serialNumberText.text(), "#EX-AEV974");
                    Assert.assertEquals(externalPage.sanitytestExternalTableRows.entry("External SanityTest 3").serialNumberText.text(), "#EX-CCA106");
                });
            });

        });

        // Ensure that at the end of "inWindow", the external window is closed if it wasn't closed before.
        Assert.assertEquals(Selion.driver().getWindowHandles().size(), 1);
    }

    @Test
    public void testLongComponentList() {
        sanitytestPage.inPage(p -> {
            int i = 0;

            Assert.assertEquals(p.longComponentEntries.size(), 500);

            for (SeSanityTestLongListEntryComponent entry : p.longComponentEntries) {
                i++;

                Assert.assertEquals(entry.titleText.text(), "Long List Entry " + i);
                if (i > 100) {
                    break;
                }
            }

            p.longComponentEntries.at(487).checkbox.select();
            p.longComponentEntries.at(301).textbox.enterText("This is a test");

            Assert.assertTrue(p.longComponentEntries.entry("Long List Entry 488").checkbox.isSelected());
            Assert.assertEquals(p.longComponentEntries.entry("Long List Entry 302").textbox.text(), "This is a test");
        });

    }

    @Test
    public void testCsvDownload() {
        sanitytestPage.inPage(p -> {
            final SeDownloadCsvFileParser csvParser = p.sanitytestCsvDownloadLink.download().parse(SeDownloadCsvFileParser.class);

            Assert.assertEquals(csvParser.baseName(), "sanitytest");
            Assert.assertEquals(csvParser.extension(), "csv");
            Assert.assertEquals(csvParser.contentText(), "\"1\", \"test\", \"This is a test content.\"");
            Assert.assertEquals(csvParser.csvContents().getFirst().getFirst(), "1");
            Assert.assertEquals(csvParser.csvContents().getFirst().get(1), "test");
            Assert.assertEquals(csvParser.csvContents().getFirst().get(2), "This is a test content.");
        });
    }

}
