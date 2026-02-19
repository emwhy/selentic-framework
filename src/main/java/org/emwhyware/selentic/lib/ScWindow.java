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
