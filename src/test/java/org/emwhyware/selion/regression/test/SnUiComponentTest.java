package org.emwhyware.selion.regression.test;

import org.emwhyware.selion.lib.Selion;
import org.emwhyware.selion.lib.SnButton;
import org.emwhyware.selion.lib.SnPage;
import org.emwhyware.selion.lib.SnWithPage;
import org.emwhyware.selion.lib.exception.*;
import org.emwhyware.selion.lib.util.SnDownloadCsvFileParser;
import org.emwhyware.selion.lib.util.SnLogHandler;
import org.emwhyware.selion.lib.util.SnWait;
import org.emwhyware.selion.regression.component.SnAnimatedBox;
import org.emwhyware.selion.regression.component.SnLongListEntryComponent;
import org.emwhyware.selion.regression.component.SnTestTableRow;
import org.emwhyware.selion.regression.page.SnDragAndDropTestPage;
import org.emwhyware.selion.regression.page.SnExternalPage;
import org.emwhyware.selion.regression.page.SnUiComponentPage;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

public class SnUiComponentTest {
    private static final Logger LOG = SnLogHandler.logger(SnUiComponentTest.class);

    private final SnWithPage<SnUiComponentPage> testPage = SnPage.with(SnUiComponentPage.class);
    private final SnWithPage<SnExternalPage> testExternalPage = SnPage.with(SnExternalPage.class);
    private final SnWithPage<SnDragAndDropTestPage> dragAndDropTestPage = SnPage.with(SnDragAndDropTestPage.class);

    @BeforeClass 
    public void setup() {
        Selion.open("file://" + System.getProperty("user.dir") + "/build/resources/test/test_file/test.htm");
    }

    @AfterMethod(alwaysRun = true)
    public void reload() {
        testPage.inPage(SnPage::reload);
    }

    @AfterClass(alwaysRun = true)
    public void shutdown() {
        Selion.quit();
    }

    @Test
    public void testTextbox() {
        testPage.inPage(p -> {
            Assert.assertEquals(p.testTextbox.text(), "textbox text");

            p.testTextbox.enterText("this is a test");

            Assert.assertEquals(p.testTextbox.text(), "this is a test");
        });
    }

    @Test
    public void testTextarea() {
        testPage.inPage(p -> {
            Assert.assertEquals(p.testTextarea.text(), "textarea text");

            p.testTextarea.enterText("test text");

            Assert.assertEquals(p.testTextarea.text(), "test text");
        });
    }

    @Test
    public void testDateTextbox() {
        LocalDate futureDate = LocalDate.now().plusDays(8);

        testPage.inPage(p -> {
            Assert.assertEquals(p.testDateTextbox.text(), "");

            p.testDateTextbox.enterDate(futureDate);

            Assert.assertEquals(p.testDateTextbox.text(), futureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            Assert.assertEquals(p.testDateTextbox.parse(), futureDate);
        });
    }

    @Test
    public void testDropdown() {
        testPage.inPage(p -> {
            final List<String> options = p.testDropdown.optionTexts();

            for (int i = 1; i <= 40; i++) {
                Assert.assertEquals(options.get(i-1), "TEST " + i);
            }

            Assert.assertEquals(p.testDropdown.selectedText(), "TEST 1");

            p.testDropdown.select("TEST 35");

            Assert.assertEquals(p.testDropdown.selectedText(), "TEST 35");

            p.testDropdown.select(Pattern.compile(".*23$"));

            Assert.assertEquals(p.testDropdown.selectedText(), "TEST 23");
        });

    }

    @Test
    public void testMultiSelect() {
        testPage.inPage(p -> {
            final List<String> options = p.testMultiSelect.optionTexts();

            for (int i = 1; i <= 40; i++) {
                Assert.assertEquals(options.get(i-1), "TEST " + i);
            }

            Assert.assertTrue(p.testMultiSelect.selectedTexts().isEmpty());

            p.testMultiSelect.select("TEST 31");
            p.testMultiSelect.select("TEST 21");
            p.testMultiSelect.select("TEST 11");
            p.testMultiSelect.select("TEST 9");

            Assert.assertListContainsObject(p.testMultiSelect.selectedTexts(), "TEST 11", "");
            Assert.assertListContainsObject(p.testMultiSelect.selectedTexts(), "TEST 21", "");
            Assert.assertListContainsObject(p.testMultiSelect.selectedTexts(), "TEST 9", "");
            Assert.assertListContainsObject(p.testMultiSelect.selectedTexts(), "TEST 31", "");

            p.testMultiSelect.deselect("TEST 11");
            p.testMultiSelect.deselect("TEST 21");

            Assert.assertListContainsObject(p.testMultiSelect.selectedTexts(), "TEST 9", "");
            Assert.assertListContainsObject(p.testMultiSelect.selectedTexts(), "TEST 31", "");
            Assert.assertListNotContainsObject(p.testMultiSelect.selectedTexts(), "TEST 11", "");
            Assert.assertListNotContainsObject(p.testMultiSelect.selectedTexts(), "TEST 21", "");

            p.testMultiSelect.clear();

            assertTrue(p.testMultiSelect.selectedTexts().isEmpty());

            p.testMultiSelect.select(Pattern.compile("^TEST 4.*"));

            Assert.assertListContainsObject(p.testMultiSelect.selectedTexts(), "TEST 4", "");
            Assert.assertListContainsObject(p.testMultiSelect.selectedTexts(), "TEST 40", "");

            p.testMultiSelect.deselect(Pattern.compile(".*4$"));

            Assert.assertListContainsObject(p.testMultiSelect.selectedTexts(), "TEST 40", "");
            Assert.assertListNotContainsObject(p.testMultiSelect.selectedTexts(), "TEST 4", "");
        });
    }

    @Test
    public void testCheckbox() {
        testPage.inPage(p -> {
            Assert.assertTrue(p.testCheckbox.isSelected());

            p.testCheckbox.deselect();

            Assert.assertFalse(p.testCheckbox.isSelected());

            p.testCheckbox.select();

            Assert.assertTrue(p.testCheckbox.isSelected());

            Assert.assertEquals(p.testCheckbox.text(), "Test CheckBox");
        });
    }

    @Test
    public void testRadioButtons() {
        testPage.inPage(p -> {
            final List<String> texts = p.testRadioButtons.texts();

            Assert.assertListContainsObject(texts, "Test Radio 1", "");
            Assert.assertListContainsObject(texts, "Test Radio 2", "");
            Assert.assertListContainsObject(texts, "Test Radio 3", "");
            Assert.assertListContainsObject(texts, "Test Radio 4", "");
            Assert.assertListContainsObject(texts, "Test Radio 5", "");

            Assert.assertEquals(p.testRadioButtons.selectedText(), "Test Radio 2");

            p.testRadioButtons.select("Test Radio 5");

            Assert.assertEquals(p.testRadioButtons.selectedText(), "Test Radio 5");
        });
    }

    @Test
    public void testInputButton() {
        testPage.inPage(p -> {
            Assert.assertEquals(p.testInputButton.text(), "SanityTest Input Button");

            p.testInputButton.click();

            Assert.assertEquals(p.testInputButtonIndicatorText.text(), "Clicked!");
        });
    }

    @Test
    public void testButton() {
        testPage.inPage(p -> {
            Assert.assertEquals(p.testButton.text(), "SanityTest Button");

            p.testButton.click();

            Assert.assertEquals(p.testButtonIndicatorText.text(), "Clicked!");
        });
    }

    @Test
    public void testTableRows() {
        testPage.inPage(p -> {
            final List<SnTestTableRow> filteredRow = p.testTableRows.filter(r -> r.productTypeText.text().equals("Type 1"));

            Assert.assertEquals(p.testTableRows.size(), 3);

            Assert.assertEquals(p.testTableRows.at(0).text(), "SanityTest 1");
            Assert.assertEquals(p.testTableRows.at(1).text(), "SanityTest 2");
            Assert.assertEquals(p.testTableRows.at(2).text(), "SanityTest 3");

            Assert.assertEquals(p.testTableRows.entry("SanityTest 1").serialNumberText.text(), "#TDD987");
            Assert.assertEquals(p.testTableRows.entry("SanityTest 2").serialNumberText.text(), "#AEV974");
            Assert.assertEquals(p.testTableRows.entry("SanityTest 3").serialNumberText.text(), "#CCA106");

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
        testPage.inPage(p -> {
            p.inSanityTestInnerFrame(frameContent -> {
                Assert.assertEquals(frameContent.testExternalTextbox.text(), "external textbox text");

                Assert.assertEquals(frameContent.testExternalTableRows.size(), 3);

                Assert.assertEquals(frameContent.testExternalTableRows.at(0).text(), "External SanityTest 1");
                Assert.assertEquals(frameContent.testExternalTableRows.at(1).text(), "External SanityTest 2");
                Assert.assertEquals(frameContent.testExternalTableRows.at(2).text(), "External SanityTest 3");

                Assert.assertEquals(frameContent.testExternalTableRows.entry("External SanityTest 1").serialNumberText.text(), "#EX-TDD987");
                Assert.assertEquals(frameContent.testExternalTableRows.entry("External SanityTest 2").serialNumberText.text(), "#EX-AEV974");
                Assert.assertEquals(frameContent.testExternalTableRows.entry("External SanityTest 3").serialNumberText.text(), "#EX-CCA106");
            });
        });
    }

    @Test
    public void testDialog() {
        testPage.inPage(p -> {
            p.openSanityTestDialogButton.click();
            p.inSanityTestDialog(dialog -> {
                Assert.assertEquals(dialog.textbox.text(), "test dialog text");

                dialog.textbox.enterText("Test text in dialog");

                Assert.assertEquals(dialog.textbox.text(), "Test text in dialog");

                dialog.closeButton.click();
            });
        });

    }

    @Test
    public void testModalDialog() {
        testPage.inPage(p -> {
            p.openSanityTestModalDialogButton.click();
            p.inSanityTestModalDialog(dialog -> {
                Assert.assertEquals(dialog.textbox.text(), "test modal dialog text");

                dialog.textbox.enterText("Test test in modal dialog");

                Assert.assertEquals(dialog.textbox.text(), "Test test in modal dialog");

                dialog.closeButton.click();
            });
        });

    }

    @Test
    public void testExternalWindows() {
        testPage.inPage(p -> {

            // Ensure that calling "inWindow()" without having a window open would cause
            // exception.
            try {
                p.inWindow(testExternalPage, p1 -> {
                    fail("Should have thrown an exception");
                });
                fail("Should have thrown an exception");
            } catch (SnWindowException ex) {
                Assert.assertEquals(ex.getMessage(), "New window has not opened");
            }

            // Open a new window.
            p.openExternalWindowLink.click();
            p.inWindow(testExternalPage, p1 -> {
                // Open another new window.
                p1.openExternalWindowLink.click();
                p1.inWindow(testExternalPage, (p2, controller) -> {

                    // Controller allows temporary switching control to other windows
                    // without closing them, then return the control back to the original
                    // window.
                    controller.inOtherWindow(testPage, 0, p3 -> {
                        // Ensure that the focused window is the root one.
                        Assert.assertEquals(p3.testTableRows.entry("SanityTest 1").serialNumberText.text(), "#TDD987");
                        Assert.assertEquals(p3.testTableRows.entry("SanityTest 2").serialNumberText.text(), "#AEV974");
                        Assert.assertEquals(p3.testTableRows.entry("SanityTest 3").serialNumberText.text(), "#CCA106");

                        Assert.assertEquals(controller.windowCount(), 3);
                    });
                    Assert.assertEquals(controller.windowCount(), 3);

                    // Assertion on the external window after the control is returned from
                    // "the other window".
                    Assert.assertEquals(p2.testExternalTextbox.text(), "external textbox text");

                    Assert.assertEquals(p2.testExternalTableRows.size(), 3);

                    Assert.assertEquals(p2.testExternalTableRows.at(0).text(), "External SanityTest 1");
                    Assert.assertEquals(p2.testExternalTableRows.at(1).text(), "External SanityTest 2");
                    Assert.assertEquals(p2.testExternalTableRows.at(2).text(), "External SanityTest 3");

                    Assert.assertEquals(p2.testExternalTableRows.entry("External SanityTest 1").serialNumberText.text(), "#EX-TDD987");
                    Assert.assertEquals(p2.testExternalTableRows.entry("External SanityTest 2").serialNumberText.text(), "#EX-AEV974");
                    Assert.assertEquals(p2.testExternalTableRows.entry("External SanityTest 3").serialNumberText.text(), "#EX-CCA106");

                    // Close the current window.
                    p2.closeCurrentWindowButton.click();
                });

                // Ensure that "inWindow" predicate ended properly despite that the window was
                // closed before its end.
                Assert.assertEquals(Selion.driver().getWindowHandles().size(), 2);

                Assert.assertEquals(p1.testExternalTextbox.text(), "external textbox text");

                Assert.assertEquals(p1.testExternalTableRows.size(), 3);

                Assert.assertEquals(p1.testExternalTableRows.at(0).text(), "External SanityTest 1");
                Assert.assertEquals(p1.testExternalTableRows.at(1).text(), "External SanityTest 2");
                Assert.assertEquals(p1.testExternalTableRows.at(2).text(), "External SanityTest 3");

                Assert.assertEquals(p1.testExternalTableRows.entry("External SanityTest 1").serialNumberText.text(), "#EX-TDD987");
                Assert.assertEquals(p1.testExternalTableRows.entry("External SanityTest 2").serialNumberText.text(), "#EX-AEV974");
                Assert.assertEquals(p1.testExternalTableRows.entry("External SanityTest 3").serialNumberText.text(), "#EX-CCA106");
            });

        });

        // Ensure that at the end of "inWindow", the external window is closed if it wasn't closed before.
        Assert.assertEquals(Selion.driver().getWindowHandles().size(), 1);
    }

    @Test
    public void testLongComponentList() {
        testPage.inPage(p -> {
            int i = 0;

            Assert.assertEquals(p.longComponentEntries.size(), 500);

            for (SnLongListEntryComponent entry : p.longComponentEntries) {
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
        testPage.inPage(p -> {
            final SnDownloadCsvFileParser csvParser = p.testCsvDownloadLink.download().parse(SnDownloadCsvFileParser.class);

            Assert.assertEquals(csvParser.baseName(), "test");
            Assert.assertEquals(csvParser.extension(), "csv");
            Assert.assertEquals(csvParser.contentText(), "\"1\", \"test\", \"This is a test content.\"");
            Assert.assertEquals(csvParser.csvContents().getFirst().getFirst(), "1");
            Assert.assertEquals(csvParser.csvContents().getFirst().get(1), "test");
            Assert.assertEquals(csvParser.csvContents().getFirst().get(2), "This is a test content.");
        });
    }

    @Test
    public void testOwnText() {
        testPage.inPage(p -> {
            Assert.assertEquals(p.testOwnText.exposedOwnText(), "This is the own text.");
        });
    }

    @Test
    public void testWaitObject() {
        testPage.inPage(p -> {
            // These methods use SnWait within them.
            Assert.assertFalse(p.testNonExistingLink.exists());
            Assert.assertFalse(p.testNonExistingLink.isDisplayed());

            try {
                p.testNonExistingLink.text();
            } catch (SnElementNotFoundException ex) {
                // Expected.
            }

            try {
                p.testNonExistingLink.click();
            } catch (SnElementNotFoundException ex) {
                // Expected.
            }

            try {
                SnWait.waitUntil(10, () -> false);
            } catch (SnWaitTimeoutException ex) {
                // Expected.
            }

            try {
                SnWait.waitUntil(10, () -> false, SnSanityTestException::new);
            } catch (SnSanityTestException ex) {
                // Expected.
            }

            try {
                SnWait.waitUntil(10, () -> false, ex -> null);
                Assert.fail("Unexpected exception were thrown.");
            } catch (SnWaitTimeoutException ex) {
            }
        });
    }

    @Test
    public void testWaitForAnimation() {
        testPage.inPage(p -> {
            testAnimation(p.animatedBox, p.animateMoveButton);
            testAnimation(p.animatedBox, p.animateRotateButton);
            testAnimation(p.animatedBox, p.animateOpacityButton);
            testAnimation(p.animatedBox, p.animateSizeButton);

            try {
                testAnimation(p.animatedBox, p.animateLongButton);
                fail("Should not get here because it should throw exception.");
            } catch (SnComponentAnimatingException ex) {

            }
        });
    }

    @Test
    public void testAlert() {
        testPage.inPage(p -> {
            p.showAlertButton.click();
            p.inAlert(a -> {
                Assert.assertEquals(a.getText(), "Test Alert");
                a.accept();
            });

            p.showConfirmButton.click();
            p.inAlert(a -> {
                Assert.assertEquals(a.getText(), "Test Confirm");
                a.dismiss();
            });

            p.showPromptButton.click();
            p.inAlert(a -> {
                Assert.assertEquals(a.getText(), "Test Prompt");

                a.sendKeys("This is a new text");
                a.accept();
            });
        });
    }

    @Test
    public void testDragAndDrop() {
        testPage.inPage(p -> {

            p.openDragAndDropPageLink.click();
            p.inWindow(dragAndDropTestPage, p1 -> {
                Assert.assertTrue(p1.dropZone1.draggedItem.isDisplayed());
                Assert.assertFalse(p1.dropZone2.draggedItem.isDisplayed());

                p1.draggedItem.drag().to(p1.dropZone2);

                Assert.assertFalse(p1.dropZone1.draggedItem.isDisplayed());
                Assert.assertTrue(p1.dropZone2.draggedItem.isDisplayed());

                p1.draggedItem.drag().to(p1.dropZone1);

                Assert.assertTrue(p1.dropZone1.draggedItem.isDisplayed());
                Assert.assertFalse(p1.dropZone2.draggedItem.isDisplayed());
            });
        });
    }

    private void testAnimation(SnAnimatedBox animatedBox, SnButton button) {
        long startTimestamp, endTimestamp;

        button.click();
        startTimestamp = System.currentTimeMillis();
        animatedBox.exposedWaitForAnimation();
        endTimestamp = System.currentTimeMillis();

        LOG.debug("Waited Time: {}", endTimestamp - startTimestamp);
        Assert.assertTrue(endTimestamp - startTimestamp > 1900);
        Assert.assertTrue(endTimestamp - startTimestamp < 2500);
        SnWait.sleep(500);
    }

    public class SnSanityTestException extends RuntimeException {
        SnSanityTestException(Throwable ex) {
            super("Sanity Test Failure", ex);
        }
    }
}
