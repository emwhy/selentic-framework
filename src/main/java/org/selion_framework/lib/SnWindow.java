package org.selion_framework.lib;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.selion_framework.lib.exception.SnWaitTimeoutException;
import org.selion_framework.lib.exception.SnWindowException;
import org.selion_framework.lib.util.SnLogHandler;
import org.selion_framework.lib.util.SnWait;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public final class SnWindow {
    private static final Logger LOG = SnLogHandler.logger(SnWindow.class);

    SnWindow() {
    }

    /**
     * Switch the control to the top window, and perform the actions in predicate.
     * @param predicate
     */
    public <T extends SnPage> void inWindow(SnWithPage<T> withPage, SnWindowAction<T> predicate) {
        inWindow(withPage, predicate, null);
    }

    /**
     * Switch the control to the top window, and perform the actions in predicate.
     * "controller" is provided as parameter.
     * @param predicate
     */
    public <T extends SnPage> void inWindow(SnWithPage<T> withPage, SnWindowActionWithController<T> predicate) {
        inWindow(withPage, null, predicate);
    }

    /**
     * Switch the control to the top window, and perform the actions in predicate.
     * @param predicate
     * @param controllerPredicate
     */
    private <T extends SnPage> void inWindow(SnWithPage<T> withPage, SnWindowAction<T> predicate, SnWindowActionWithController<T> controllerPredicate) {
        final WebDriver driver = Selion.driver();
        final String mainWindowHandle = driver.getWindowHandle();
        final List<String> windowHandles = getWindowHandles(driver);
        final String newWindowHandle = switchToTopWindow(driver, windowHandles);
        int beforeCloseAttemptCount;

        try {
            if (predicate != null) {
                withPage.inPage(p -> predicate.inWindow(p));
            } else if (controllerPredicate != null) {
                withPage.inPage(p -> controllerPredicate.inWindow(p, new SnWindowController(driver, newWindowHandle)));
            } else {
                // This should never happen.
                throw new SnWindowException("No predicate defined.");
            }
        } catch (Throwable th) {
            driver.switchTo().window(mainWindowHandle);
            SnWait.waitUntil(() -> driver.getWindowHandle().equals(mainWindowHandle));
            throw new SnWindowException("Error while in external window.", th);
        }
        beforeCloseAttemptCount = windowHandles.size();

        // Make sure that the actual window count matches expected count.
        if (beforeCloseAttemptCount == driver.getWindowHandles().size()) {
            SnWait.waitUntil(() -> {
                for (String handle : driver.getWindowHandles()) {
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
                throw new SnWindowException(exceptionText);
            }
        }

        // Switch back window control to the previous window.
        try {
            driver.switchTo().window(mainWindowHandle);
            SnWait.waitUntil(() -> driver.getWindowHandle().equals(mainWindowHandle));
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
            throw new SnWindowException("No window is opened for switching");
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
            return SnWait.waitUntilNonNull(() -> {
                // Parent window index will be 0 initially
                // Adding + 2 because comparing size with index
                if (driver.getWindowHandles().size() == windowHandles.indexOf(currentHandle) + 2) {
                    for (String handle : driver.getWindowHandles()) {
                        if (!windowHandles.contains(handle)) {
                            windowHandles.add(handle);
                        }
                    }
                    return windowHandles;
                }
                return null;
            });
        } catch (SnWaitTimeoutException ex) {
            throw new SnWindowException("New window has not opened", ex);
        }
    }

    public final class SnWindowController {
        private final WebDriver webDriver;
        private final String currentHandle;

        private SnWindowController(WebDriver webDriver, String currentHandle) {
            this.webDriver = webDriver;
            this.currentHandle = currentHandle;
        }

        /**
         * Temporary give control to other window without closing any window.
         * @param index
         * @param predicate
         */
        public <T extends SnPage> void inOtherWindow(SnWithPage<T> withPage, int index, SnWindowAction<T> predicate) {
            final List<String> handles = webDriver.getWindowHandles().stream().toList();

            try {
                webDriver.switchTo().window(handles.get(index));
                Selion.executeScript("window.focus()");
                LOG.debug("Temporary switch to window: {} '{}' [{}]", index, webDriver.getTitle(), handles.get(index));

                withPage.inPage(p -> predicate.inWindow(p));

            } finally {
                webDriver.switchTo().window(currentHandle);
                SnWait.waitUntil(() -> webDriver.getWindowHandle().equals(currentHandle));
                Selion.executeScript("window.focus()");
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

    public interface SnWindowAction<T extends SnPage> {
        void inWindow(T page);
    }

    public interface SnWindowActionWithController<T extends SnPage> {
        void inWindow(T page, SnWindowController controller);
    }
}
