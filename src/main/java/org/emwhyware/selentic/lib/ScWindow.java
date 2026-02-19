package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.exception.ScWaitTimeoutException;
import org.emwhyware.selentic.lib.exception.ScWindowException;
import org.emwhyware.selentic.lib.util.ScLogHandler;
import org.emwhyware.selentic.lib.util.ScWait;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * ScWindow handles inside additional browser window. It handles moving controls to a window, and closing it when done.
 * <p>
 * The implementation of handling additional windows in Selenium can get messy quickly with switching controls in and out.
 * This class keeps the implementation neat and clean while taking care of the window management.
 *
 * <pre>{@code
 *
 * testPage.inPage(p -> {
 *     // Open a new window.
 *     p.openExternalWindowLink().click();
 *
 *     // New window has the control inside predicate.
 *     p.inWindow(testExternalPage, p1 -> {
 *         // Open another new window. Multiple external windows can be nested.
 *         p1.openExternalWindowLink().click();
 *
 *         // Now the new window gets the control.
 *         p1.inWindow(testExternalPage, (p2, controller) -> {
 *
 *             // Controller allows temporary switching control to other windows
 *             // without closing them, then return the control back to the original
 *             // window.
 *             controller.inOtherWindow(testPage, 0, p3 -> {
 *                 // Ensure that the focused window is the root one.
 *                 Assert.assertEquals(p3.testTableRows().entry("SanityTest 1").serialNumberText.text(), "#TDD987");
 *                 Assert.assertEquals(p3.testTableRows().entry("SanityTest 2").serialNumberText.text(), "#AEV974");
 *                 Assert.assertEquals(p3.testTableRows().entry("SanityTest 3").serialNumberText.text(), "#CCA106");
 *             });
 *             // At the end of the predicate, the control is automatically returned to the calling window.
 *
 *             // Assertion on the external window after the control is returned from
 *             // "the other window".
 *             Assert.assertEquals(p2.testExternalTextbox().text(), "external textbox text");
 *
 *             Assert.assertEquals(p2.testExternalTableRows().size(), 3);
 *
 *             Assert.assertEquals(p2.testExternalTableRows().at(0).text(), "External SanityTest 1");
 *             Assert.assertEquals(p2.testExternalTableRows().at(1).text(), "External SanityTest 2");
 *             Assert.assertEquals(p2.testExternalTableRows().at(2).text(), "External SanityTest 3");
 *
 *             Assert.assertEquals(p2.testExternalTableRows().entry("External SanityTest 1").serialNumberText.text(), "#EX-TDD987");
 *             Assert.assertEquals(p2.testExternalTableRows().entry("External SanityTest 2").serialNumberText.text(), "#EX-AEV974");
 *             Assert.assertEquals(p2.testExternalTableRows().entry("External SanityTest 3").serialNumberText.text(), "#EX-CCA106");
 *
 *             // Close the current window.
 *             p2.closeCurrentWindowButton().click();
 *         });
 *         // Gives the control back to the opening window.
 *
 *         Assert.assertEquals(p1.testExternalTextbox().text(), "external textbox text");
 *
 *         Assert.assertEquals(p1.testExternalTableRows().size(), 3);
 *
 *         Assert.assertEquals(p1.testExternalTableRows().at(0).text(), "External SanityTest 1");
 *         Assert.assertEquals(p1.testExternalTableRows().at(1).text(), "External SanityTest 2");
 *         Assert.assertEquals(p1.testExternalTableRows().at(2).text(), "External SanityTest 3");
 *
 *         Assert.assertEquals(p1.testExternalTableRows().entry("External SanityTest 1").serialNumberText.text(), "#EX-TDD987");
 *         Assert.assertEquals(p1.testExternalTableRows().entry("External SanityTest 2").serialNumberText.text(), "#EX-AEV974");
 *         Assert.assertEquals(p1.testExternalTableRows().entry("External SanityTest 3").serialNumberText.text(), "#EX-CCA106");
 *     });
 *     // The original window gets the control back.
 *
 * });
 *
 * }</pre>
 */
public final class ScWindow {
    private static final Logger LOG = ScLogHandler.logger(ScWindow.class);

    ScWindow() {
    }

    /**
     * Switch the control to the top window, and perform the actions in predicate.
     * @param predicate
     */
    public <T extends ScPage> void inWindow(ScWithPage<T> withPage, ScWindowAction<T> predicate) {
        inWindow(withPage, predicate, null);
    }

    /**
     * Switch the control to the top window, and perform the actions in predicate.
     * "controller" is provided as parameter.
     * @param predicate
     */
    public <T extends ScPage> void inWindow(ScWithPage<T> withPage, ScWindowActionWithController<T> predicate) {
        inWindow(withPage, null, predicate);
    }

    /**
     * Switch the control to the top window, and perform the actions in predicate.
     * @param predicate
     * @param controllerPredicate
     */
    private <T extends ScPage> void inWindow(ScWithPage<T> withPage, ScWindowAction<T> predicate, ScWindowActionWithController<T> controllerPredicate) {
        final WebDriver driver = Selentic.driver();
        final String mainWindowHandle = driver.getWindowHandle();
        final List<String> windowHandles = getWindowHandles(driver);
        final String newWindowHandle = switchToTopWindow(driver, windowHandles);
        int beforeCloseAttemptCount;

        try {
            if (predicate != null) {
                withPage.inPage(p -> predicate.inWindow(p));
            } else if (controllerPredicate != null) {
                withPage.inPage(p -> controllerPredicate.inWindow(p, new ScWindowController(driver, newWindowHandle)));
            } else {
                // This should never happen.
                throw new ScWindowException("No predicate defined.");
            }
        } catch (Throwable th) {
            driver.switchTo().window(mainWindowHandle);
            ScWait.waitUntil(() -> driver.getWindowHandle().equals(mainWindowHandle));
            throw new ScWindowException("Error while in external window.", th);
        }
        beforeCloseAttemptCount = windowHandles.size();

        // Make sure that the actual window count matches expected count.
        if (beforeCloseAttemptCount == driver.getWindowHandles().size()) {
            ScWait.waitUntil(() -> {
                for (final String handle : driver.getWindowHandles()) {
                    if (handle.equals(newWindowHandle)) {
                        driver.switchTo().window(newWindowHandle);
                        driver.close();
                        LOG.debug("Closed window [{}]", newWindowHandle);
                        windowHandles.remove(newWindowHandle);
                        break;
                    }
                }

                return beforeCloseAttemptCount == windowHandles.size() + 1;
            });

            if (driver.getWindowHandles().size() != windowHandles.size()) {
                String exceptionText = "Unexpected window count: previous window handle = " + newWindowHandle;

                exceptionText += " actual = " + driver.getWindowHandles().size() + " [" + String.join(", ", driver.getWindowHandles()) + "]";
                exceptionText += " expected = " + windowHandles.size() + " [" + String.join(", ", windowHandles) + "]";
                throw new ScWindowException(exceptionText);
            }
        }

        // Switch back window control to the previous window.
        try {
            driver.switchTo().window(mainWindowHandle);
            ScWait.waitUntil(() -> driver.getWindowHandle().equals(mainWindowHandle));
            LOG.debug("Return to window :'{}' [{}]", driver.getTitle(), mainWindowHandle);
        } catch (NoSuchWindowException ex) {
            LOG.warn("Encountered NoSuchWindowException: count = {} [{}]", driver.getWindowHandles().size(), String.join(", ", driver.getWindowHandles()));
            LOG.warn("Exception", ex);
        }
    }

    /**
     * Switch to the top window.
     * @param driver
     * @param windowHandles
     * @return
     */
    private String switchToTopWindow(WebDriver driver, List<String> windowHandles) {
        final String topHandle = windowHandles.getLast();
        final String currentHandle = driver.getWindowHandle();

        if (topHandle.equals(currentHandle)) {
            throw new ScWindowException("No window is opened for switching");
        }
        // Switching to last opened window
        driver.switchTo().window(topHandle);
        LOG.debug("Switched to window :'{}' [{}]", driver.getTitle(), topHandle);
        return topHandle;
    }

    /**
     * Get window handles.
     * @param driver
     * @return
     */
    private List<String> getWindowHandles(WebDriver driver) {
        String currentHandle = driver.getWindowHandle();
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());

        // Wait until an additional window has been added.
        try {
            return ScWait.waitUntilNonNull(() -> {
                // Parent window index will be 0 initially
                // Adding + 2 because comparing size with index
                if (driver.getWindowHandles().size() == windowHandles.indexOf(currentHandle) + 2) {
                    for (final String handle : driver.getWindowHandles()) {
                        if (!windowHandles.contains(handle)) {
                            windowHandles.add(handle);
                        }
                    }
                    return windowHandles;
                }
                return null;
            });
        } catch (ScWaitTimeoutException ex) {
            throw new ScWindowException("New window has not opened", ex);
        }
    }

    public final class ScWindowController {
        private final WebDriver webDriver;
        private final String currentHandle;

        private ScWindowController(WebDriver webDriver, String currentHandle) {
            this.webDriver = webDriver;
            this.currentHandle = currentHandle;
        }

        /**
         * Temporary give control to other window without closing any window.
         * @param index
         * @param predicate
         */
        public <T extends ScPage> void inOtherWindow(ScWithPage<T> withPage, int index, ScWindowAction<T> predicate) {
            final List<String> handles = webDriver.getWindowHandles().stream().toList();

            try {
                webDriver.switchTo().window(handles.get(index));
                Selentic.executeScript("window.focus()");
                LOG.debug("Temporary switch to window: {} '{}' [{}]", index, webDriver.getTitle(), handles.get(index));

                withPage.inPage(p -> predicate.inWindow(p));

            } finally {
                webDriver.switchTo().window(currentHandle);
                ScWait.waitUntil(() -> webDriver.getWindowHandle().equals(currentHandle));
                Selentic.executeScript("window.focus()");
                LOG.debug("Return to window :'{}' [{}]", webDriver.getTitle(), currentHandle);
            }
        }

        /**
         * Get current window count.
         * @return
         */
        public int windowCount() {
            return webDriver.getWindowHandles().size();
        }
    }

    public interface ScWindowAction<T extends ScPage> {
        void inWindow(T page);
    }

    public interface ScWindowActionWithController<T extends ScPage> {
        void inWindow(T page, ScWindowController controller);
    }
}
