package org.selion_framework.example.test;

import org.selion_framework.example.component.SeExampleLongListEntryComponent;
import org.selion_framework.example.component.SeExampleTableRow;
import org.selion_framework.example.page.SeExampleExternalPage;
import org.selion_framework.example.page.SeExamplePage;
import org.selion_framework.lib.SePage;
import org.selion_framework.lib.SeWithPage;
import org.selion_framework.lib.Selion;
import org.selion_framework.lib.exception.SeWindowException;
import org.selion_framework.lib.util.SeDownloadCsvFileParser;
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

public class SeExampleTest {
    private final SeWithPage<SeExamplePage> examplePage = SePage.with(SeExamplePage.class);
    private final SeWithPage<SeExampleExternalPage> exampleExternalPage = SePage.with(SeExampleExternalPage.class);

    @BeforeClass
    public void setup() {
        Selion.open("file://" + System.getProperty("user.dir") + "/build/resources/main/example_test_file/example.htm");
    }

    @AfterMethod(alwaysRun = true)
    public void reload() {
        examplePage.inPage(SePage::reload);
    }

    @AfterClass(alwaysRun = true)
    public void shutdown() {
        Selion.quit();
    }

    @Test
    public void testTextbox() {
        examplePage.inPage(p -> {
            Assert.assertEquals(p.exampleTextbox.text(), "textbox text");

            p.exampleTextbox.enterText("this is a test");

            Assert.assertEquals(p.exampleTextbox.text(), "this is a test");
        });
    }

    @Test
    public void testTextarea() {
        examplePage.inPage(p -> {
            Assert.assertEquals(p.exampleTextarea.text(), "textarea text");

            p.exampleTextarea.enterText("test text");

            Assert.assertEquals(p.exampleTextarea.text(), "test text");
        });
    }

    @Test
    public void testDateTextbox() {
        LocalDate futureDate = LocalDate.now().plusDays(8);

        examplePage.inPage(p -> {
            Assert.assertEquals(p.exampleDateTextbox.text(), "");

            p.exampleDateTextbox.enterDate(futureDate);

            Assert.assertEquals(p.exampleDateTextbox.text(), futureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            Assert.assertEquals(p.exampleDateTextbox.parse(), futureDate);
        });
    }

    @Test
    public void testDropdown() {
        examplePage.inPage(p -> {
            final List<String> options = p.exampleDropdown.optionTexts();

            for (int i = 1; i <= 40; i++) {
                Assert.assertEquals(options.get(i-1), "TEST " + i);
            }

            Assert.assertEquals(p.exampleDropdown.selectedText(), "TEST 1");

            p.exampleDropdown.select("TEST 35");

            Assert.assertEquals(p.exampleDropdown.selectedText(), "TEST 35");

            p.exampleDropdown.selectEndsWith("23");

            Assert.assertEquals(p.exampleDropdown.selectedText(), "TEST 23");
        });

    }

    @Test
    public void testMultiSelect() {
        examplePage.inPage(p -> {
            final List<String> options = p.exampleMultiSelect.optionTexts();

            for (int i = 1; i <= 40; i++) {
                Assert.assertEquals(options.get(i-1), "TEST " + i);
            }

            Assert.assertTrue(p.exampleMultiSelect.selectedTexts().isEmpty());

            p.exampleMultiSelect.select("TEST 31");
            p.exampleMultiSelect.select("TEST 21");
            p.exampleMultiSelect.select("TEST 11");
            p.exampleMultiSelect.select("TEST 9");

            Assert.assertListContainsObject(p.exampleMultiSelect.selectedTexts(), "TEST 11", "");
            Assert.assertListContainsObject(p.exampleMultiSelect.selectedTexts(), "TEST 21", "");
            Assert.assertListContainsObject(p.exampleMultiSelect.selectedTexts(), "TEST 9", "");
            Assert.assertListContainsObject(p.exampleMultiSelect.selectedTexts(), "TEST 31", "");

            p.exampleMultiSelect.deselect("TEST 11");
            p.exampleMultiSelect.deselect("TEST 21");

            Assert.assertListContainsObject(p.exampleMultiSelect.selectedTexts(), "TEST 9", "");
            Assert.assertListContainsObject(p.exampleMultiSelect.selectedTexts(), "TEST 31", "");
            Assert.assertListNotContainsObject(p.exampleMultiSelect.selectedTexts(), "TEST 11", "");
            Assert.assertListNotContainsObject(p.exampleMultiSelect.selectedTexts(), "TEST 21", "");

            p.exampleMultiSelect.clear();

            assertTrue(p.exampleMultiSelect.selectedTexts().isEmpty());

            p.exampleMultiSelect.selectStartWith("TEST 4");

            Assert.assertListContainsObject(p.exampleMultiSelect.selectedTexts(), "TEST 4", "");
            Assert.assertListContainsObject(p.exampleMultiSelect.selectedTexts(), "TEST 40", "");

            p.exampleMultiSelect.deselectEndWith("4");

            Assert.assertListContainsObject(p.exampleMultiSelect.selectedTexts(), "TEST 40", "");
            Assert.assertListNotContainsObject(p.exampleMultiSelect.selectedTexts(), "TEST 4", "");
        });
    }

    @Test
    public void testCheckbox() {
        examplePage.inPage(p -> {
            Assert.assertTrue(p.exampleCheckbox.isSelected());

            p.exampleCheckbox.deselect();

            Assert.assertFalse(p.exampleCheckbox.isSelected());

            p.exampleCheckbox.select();

            Assert.assertTrue(p.exampleCheckbox.isSelected());

            Assert.assertEquals(p.exampleCheckbox.text(), "Test CheckBox");
        });
    }

    @Test
    public void testRadioButtons() {
        examplePage.inPage(p -> {
            final List<String> texts = p.exampleRadioButtons.texts();

            Assert.assertListContainsObject(texts, "Test Radio 1", "");
            Assert.assertListContainsObject(texts, "Test Radio 2", "");
            Assert.assertListContainsObject(texts, "Test Radio 3", "");
            Assert.assertListContainsObject(texts, "Test Radio 4", "");
            Assert.assertListContainsObject(texts, "Test Radio 5", "");

            Assert.assertEquals(p.exampleRadioButtons.selectedText(), "Test Radio 2");

            p.exampleRadioButtons.select("Test Radio 5");

            Assert.assertEquals(p.exampleRadioButtons.selectedText(), "Test Radio 5");
        });
    }

    @Test
    public void testInputButton() {
        examplePage.inPage(p -> {
            Assert.assertEquals(p.exampleInputButton.text(), "Example Input Button");

            p.exampleInputButton.click();

            Assert.assertEquals(p.exampleInputButtonIndicatorText.text(), "Clicked!");
        });
    }

    @Test
    public void testButton() {
        examplePage.inPage(p -> {
            Assert.assertEquals(p.exampleButton.text(), "Example Button");

            p.exampleButton.click();

            Assert.assertEquals(p.exampleButtonIndicatorText.text(), "Clicked!");
        });
    }

    @Test
    public void testTableRows() {
        examplePage.inPage(p -> {
            final List<SeExampleTableRow> filteredRow = p.exampleTableRows.filter(r -> r.productTypeText.text().equals("Type 1"));

            Assert.assertEquals(p.exampleTableRows.size(), 3);

            Assert.assertEquals(p.exampleTableRows.at(0).text(), "Example 1");
            Assert.assertEquals(p.exampleTableRows.at(1).text(), "Example 2");
            Assert.assertEquals(p.exampleTableRows.at(2).text(), "Example 3");

            Assert.assertEquals(p.exampleTableRows.entry("Example 1").serialNumberText.text(), "#TDD987");
            Assert.assertEquals(p.exampleTableRows.entry("Example 2").serialNumberText.text(), "#AEV974");
            Assert.assertEquals(p.exampleTableRows.entry("Example 3").serialNumberText.text(), "#CCA106");

            Assert.assertEquals(filteredRow.size(), 2);
            Assert.assertEquals(filteredRow.get(0).productNameText.text(), "Example 1");
            Assert.assertEquals(filteredRow.get(1).productNameText.text(), "Example 3");
            Assert.assertEquals(filteredRow.get(0).builtDateText.text(), "1/4/2024");
            Assert.assertEquals(filteredRow.get(1).builtDateText.text(), "10/5/2011");
            Assert.assertEquals(filteredRow.get(0).priceAmountText.text(), "$99.99");
            Assert.assertEquals(filteredRow.get(1).priceAmountText.text(), "$1,002.89");
        });
    }

    @Test
    public void testFrame() {
        examplePage.inPage(p -> {
            p.inExampleInnerFrame(frameContent -> {
                Assert.assertEquals(frameContent.exampleExternalTextbox.text(), "external textbox text");

                Assert.assertEquals(frameContent.exampleExternalTableRows.size(), 3);

                Assert.assertEquals(frameContent.exampleExternalTableRows.at(0).text(), "External Example 1");
                Assert.assertEquals(frameContent.exampleExternalTableRows.at(1).text(), "External Example 2");
                Assert.assertEquals(frameContent.exampleExternalTableRows.at(2).text(), "External Example 3");

                Assert.assertEquals(frameContent.exampleExternalTableRows.entry("External Example 1").serialNumberText.text(), "#EX-TDD987");
                Assert.assertEquals(frameContent.exampleExternalTableRows.entry("External Example 2").serialNumberText.text(), "#EX-AEV974");
                Assert.assertEquals(frameContent.exampleExternalTableRows.entry("External Example 3").serialNumberText.text(), "#EX-CCA106");
            });
        });
    }

    @Test
    public void testDialog() {
        examplePage.inPage(p -> {
            p.openExampleDialogButton.click();
            p.inExampleDialog(dialog -> {
                Assert.assertEquals(dialog.textbox.text(), "example dialog text");

                dialog.textbox.enterText("Test text in dialog");

                Assert.assertEquals(dialog.textbox.text(), "Test text in dialog");

                dialog.closeButton.click();
            });
        });

    }

    @Test
    public void testModalDialog() {
        examplePage.inPage(p -> {
            p.openExampleModalDialogButton.click();
            p.inExampleModalDialog(dialog -> {
                Assert.assertEquals(dialog.textbox.text(), "example modal dialog text");

                dialog.textbox.enterText("Test test in modal dialog");

                Assert.assertEquals(dialog.textbox.text(), "Test test in modal dialog");

                dialog.closeButton.click();
            });
        });

    }

    @Test
    public void testExternalWindows() {
        examplePage.inPage(p -> {

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
                exampleExternalPage.inPage(externalPage -> {

                    // Open another new window.
                    externalPage.openExternalWindowLink.click();
                    externalPage.inWindow(controller -> {

                        // Controller allows temporary switching control to other windows
                        // without closing them, then return the control back to the original
                        // window.
                        controller.inOtherWindow(0, () -> {
                            // Ensure that the focused window is the root one.
                            Assert.assertEquals(p.exampleTableRows.entry("Example 1").serialNumberText.text(), "#TDD987");
                            Assert.assertEquals(p.exampleTableRows.entry("Example 2").serialNumberText.text(), "#AEV974");
                            Assert.assertEquals(p.exampleTableRows.entry("Example 3").serialNumberText.text(), "#CCA106");

                            Assert.assertEquals(controller.windowCount(), 3);
                        });
                        Assert.assertEquals(controller.windowCount(), 3);

                        // Assertion on the external window after the control is returned from
                        // "the other window".
                        Assert.assertEquals(externalPage.exampleExternalTextbox.text(), "external textbox text");

                        Assert.assertEquals(externalPage.exampleExternalTableRows.size(), 3);

                        Assert.assertEquals(externalPage.exampleExternalTableRows.at(0).text(), "External Example 1");
                        Assert.assertEquals(externalPage.exampleExternalTableRows.at(1).text(), "External Example 2");
                        Assert.assertEquals(externalPage.exampleExternalTableRows.at(2).text(), "External Example 3");

                        Assert.assertEquals(externalPage.exampleExternalTableRows.entry("External Example 1").serialNumberText.text(), "#EX-TDD987");
                        Assert.assertEquals(externalPage.exampleExternalTableRows.entry("External Example 2").serialNumberText.text(), "#EX-AEV974");
                        Assert.assertEquals(externalPage.exampleExternalTableRows.entry("External Example 3").serialNumberText.text(), "#EX-CCA106");

                        // Close the current window.
                        externalPage.closeCurrentWindowButton.click();
                    });

                    // Ensure that "inWindow" predicate ended properly despite that the window was
                    // closed before its end.
                    Assert.assertEquals(Selion.driver().getWindowHandles().size(), 2);

                    Assert.assertEquals(externalPage.exampleExternalTextbox.text(), "external textbox text");

                    Assert.assertEquals(externalPage.exampleExternalTableRows.size(), 3);

                    Assert.assertEquals(externalPage.exampleExternalTableRows.at(0).text(), "External Example 1");
                    Assert.assertEquals(externalPage.exampleExternalTableRows.at(1).text(), "External Example 2");
                    Assert.assertEquals(externalPage.exampleExternalTableRows.at(2).text(), "External Example 3");

                    Assert.assertEquals(externalPage.exampleExternalTableRows.entry("External Example 1").serialNumberText.text(), "#EX-TDD987");
                    Assert.assertEquals(externalPage.exampleExternalTableRows.entry("External Example 2").serialNumberText.text(), "#EX-AEV974");
                    Assert.assertEquals(externalPage.exampleExternalTableRows.entry("External Example 3").serialNumberText.text(), "#EX-CCA106");
                });
            });

        });

        // Ensure that at the end of "inWindow", the external window is closed if it wasn't closed before.
        Assert.assertEquals(Selion.driver().getWindowHandles().size(), 1);
    }

    @Test
    public void testLongComponentList() {
        examplePage.inPage(p -> {
            int i = 0;

            Assert.assertEquals(p.longComponentEntries.size(), 500);

            for (SeExampleLongListEntryComponent entry : p.longComponentEntries) {
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
        examplePage.inPage(p -> {
            final SeDownloadCsvFileParser csvParser = p.exampleCsvDownloadLink.download().parse(SeDownloadCsvFileParser.class);

            Assert.assertEquals(csvParser.baseName(), "example");
            Assert.assertEquals(csvParser.extension(), "csv");
            Assert.assertEquals(csvParser.contentText(), "\"1\", \"test\", \"This is a test content.\"");
            Assert.assertEquals(csvParser.csvContents().getFirst().getFirst(), "1");
            Assert.assertEquals(csvParser.csvContents().getFirst().get(1), "test");
            Assert.assertEquals(csvParser.csvContents().getFirst().get(2), "This is a test content.");
        });
    }

}
