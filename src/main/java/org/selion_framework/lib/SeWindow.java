package org.selion_framework.lib;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.selion_framework.lib.exception.SeWaitTimeoutException;
import org.selion_framework.lib.exception.SeWindowException;
import org.selion_framework.lib.util.SeLogHandler;
import org.selion_framework.lib.util.SeWait;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public final class SeWindow {
    private static final Logger LOG = SeLogHandler.logger(SeWindow.class);

    SeWindow() {
    }

    /**
     * Switch the control to the top window, and perform the actions in predicate.
     * @param predicate
     */
    public void inWindow(WindowActionEmpty predicate) {
        inWindow(predicate, null);
    }

    /**
     * Switch the control to the top window, and perform the actions in predicate.
     * "controller" is provided as parameter.
     * @param predicate
     */
    public void inWindow(WindowActionController predicate) {
        inWindow(null, predicate);
    }

    /**
     * Switch the control to the top window, and perform the actions in predicate.
     * @param emptyPredicate
     * @param controllerPredicate
     */
    private void inWindow(WindowActionEmpty emptyPredicate, WindowActionController controllerPredicate) {
        final WebDriver driver = Selion.driver();
        final String mainWindowHandle = driver.getWindowHandle();
        final List<String> windowHandles = getWindowHandles(driver);
        final String newWindowHandle = switchToTopWindow(driver, windowHandles);
        int beforeCloseAttemptCount;

        if (emptyPredicate != null) {
            emptyPredicate.inWindow();
        } else if (controllerPredicate != null) {
            controllerPredicate.inWindow(new WindowController(driver, newWindowHandle));
        } else {
            // This should never happen.
            throw new SeWindowException("No predicate defined.");
        }

        beforeCloseAttemptCount = windowHandles.size();

        // Make sure that the actual window count matches expected count.
        if (beforeCloseAttemptCount == driver.getWindowHandles().size()) {
            SeWait.waitUntil(() -> {
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
                throw new SeWindowException(exceptionText);
            }
        }

        // Switch back window control to the previous window.
        try {
            driver.switchTo().window(mainWindowHandle);
            SeWait.waitUntil(() -> driver.getWindowHandle().equals(mainWindowHandle));
            LOG.debug("Return to window :'{}' [{}]", driver.getTitle(), mainWindowHandle);
        } catch (NoSuchWindowException ex) {
            LOG.warn("Encountered NoSuchWindowException: count = {} [{}]", driver.getWindowHandles().size(), String.join(", ", driver.getWindowHandles()));
            LOG.warn("Exception", ex);
        }
    }

    /**
     * Close all of currently opened windows except for the default one.
     */
    public static void closeOtherWindows() {
        try {
            final WebDriver driver = Selion.driver();
            final List<String> handles = new ArrayList<>(driver.getWindowHandles());

            LOG.debug("Detected {} windows", handles.size());
            for (int i = handles.size() - 1; i > 0; i--) {
                String handle = handles.get(i);

                driver.switchTo().window(handle);
                driver.close();
                LOG.debug("Closed window: [{}]", handle);
            }
            driver.switchTo().window(handles.getFirst());

            if (driver.getWindowHandles().size() > 1) {
                throw new SeWindowException("Failed to close all but one window. Detected windows count: " + driver.getWindowHandles().size());
            }
        } catch (IllegalStateException ex) {}
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
            throw new SeWindowException("No window is opened for switching");
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
            return SeWait.waitUntilNonNull(() -> {
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
        } catch (SeWaitTimeoutException ex) {
            throw new SeWindowException("New window has not opened", ex);
        }
    }

    public final class WindowController {
        private final WebDriver webDriver;
        private final String currentHandle;

        private WindowController(WebDriver webDriver, String currentHandle) {
            this.webDriver = webDriver;
            this.currentHandle = currentHandle;
        }

        /**
         * Temporary give control to other window without closing any window.
         * @param index
         * @param predicate
         */
        public void inOtherWindow(int index, WindowActionEmpty predicate) {
            final List<String> handles = webDriver.getWindowHandles().stream().toList();

            try {
                webDriver.switchTo().window(handles.get(index));
                Selion.executeScript("window.focus()");
                LOG.debug("Temporary switch to window: {} '{}' [{}]", index, webDriver.getTitle(), handles.get(index));

                predicate.inWindow();

            } finally {
                webDriver.switchTo().window(currentHandle);
                SeWait.waitUntil(() -> webDriver.getWindowHandle().equals(currentHandle));
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

    public interface WindowActionEmpty {
        void inWindow();
    }

    public interface WindowActionController {
        void inWindow(WindowController controller);
    }
}
