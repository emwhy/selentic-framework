package org.selion_framework.lib;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selion_framework.lib.exception.SnUnexpectedPageException;
import org.selion_framework.lib.util.SnWait;

import java.time.Duration;

/**
 * {@code SnPage} is the concrete base class for all page classes in the Selion Framework.
 * All page classes must extend from {@code SnPage} when defined.
 *
 * <p>
 * This class extends {@link SnAbstractPage} and provides common page functionality including:
 * <ul>
 *   <li>Access to the page title element</li>
 *   <li>Page reload functionality with automatic wait for page load</li>
 *   <li>Window management and control</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Page Creation Pattern:</strong> {@code SnPage} provides a method for creating
 * and initializing page instances using the {@code with(Class)} factory method. This allows
 * for clean, readable page creation in test code.
 * </p>
 *
 * <p>
 * <strong>Typical Usage:</strong>
 * <pre>{@code
 * public class HomePage extends SnPage {
 *     private static final SnCssSelector SEARCH_TEXTBOX = _cssSelector.descendant(_id("search"));
 *     private static final SnCssSelector SEARCH_BUTTON = _cssSelector.descendant(_id("search-btn"));
 *     private static final SnCssSelector HEADER_TEXT = _cssSelector.descendant(_className("header"));
 *
 *     public final SnTextbox searchTextbox = $textbox(SEARCH_BOX);
 *     public final SnButton searchButton = $button(SEARCH_BUTTON);
 *     public final SnGenericComponent headerText = $genericComponent(HEADER);
 *
 *     @Override
 *     protected void waitForDisplayed() {
 *         waitForComponent(headerText);
 *         waitForComponent(searchTextbox);
 *     }
 * }
 *
 * // In test code
 * final SnWithPage<HomePage> homePage = SnPage.with(HomePage.class);
 *
 * homePage.inPage(p -> {
 *      p.searchTextbox.enterText("Selenium");
 *      p.searchButton.click();
 * });
 * }</pre>
 * </p>
 *
 * <p>
 * <strong>Key Features:</strong>
 * <ul>
 *   <li><strong>Fluent Page Initialization:</strong> Use {@code with(Class)} to create page instances
 *       with automatic page load waiting through the {@link SnWithPage} fluent builder.</li>
 *   <li><strong>Page Title Access:</strong> Conveniently access the page's HTML title element
 *       through {@link #pageTitle()}.</li>
 *   <li><strong>Page Reload:</strong> Reload the current page and wait for it to fully load
 *       using {@link #reload()}.</li>
 *   <li><strong>Window Management:</strong> Control browser windows and perform window-specific
 *       operations through {@link #inWindow(SnWithPage, SnWindow.SnWindowAction)} and
 *       {@link #inWindow(SnWithPage, SnWindow.SnWindowActionWithController)}.</li>
 * </ul>
 * </p>
 *
 * @see SnAbstractPage
 * @see SnWithPage
 * @see SnWindow
 * @see SnComponent
 */
public abstract class SnPage extends SnAbstractPage {
    private static final SnXPath PAGE_TITLE = _xpath.descendant("title");

    /**
     * Creates a fluent builder for initializing and loading a page of the specified type.
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * // Create and initialize page.
     * final SnWithPage<HomePage> homePage = SnPage.with(HomePage.class);
     * }</pre>
     * </p>
     *
     * @param <T> the page type to create
     * @param pageType the class of the page to create; must extend from {@code SnPage}
     * @return a {@link SnWithPage} fluent builder for the specified page type
     *
     * @see SnWithPage
     */
    public static <T extends SnPage> SnWithPage<T> with(Class<T> pageType) {
        return new SnWithPage<>(pageType);
    }

    /**
     * Protected constructor for {@code SnPage}.
     *
     * <p>
     * Page instances should not be created directly through the constructor.
     * Instead, use the {@link #with(Class)} factory method to create and initialize page instances.
     * </p>
     */
    protected SnPage() {
    }

    /**
     * Returns a component representing the HTML page title element.
     *
     * <p>
     * This method provides convenient access to the {@code <title>} element of the HTML page.
     * The title element is typically used to verify that the correct page has loaded or to
     * validate page-specific information.
     * </p>
     *
     * <p>
     * The returned component is a {@link SnGenericComponent} that wraps the title element
     * and allows access to its properties like text content, attributes, etc.
     * </p>
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * final SnWithPage<HomePage> homePage = SnPage.with(HomePage.class);
     *
     * homePage.inPage(p -> {
     *      // Get the page title text
     *      String titleText = p.pageTitle().text();
     *      System.out.println("Page title: " + titleText);
     *
     *      // Verify the page title
     *      assert titleText.contains("Home") : "Expected 'Home' in page title";
     * });
     * }</pre>
     * </p>
     *
     * @return a {@link SnGenericComponent} representing the page title element
     */
    public SnGenericComponent pageTitle() {
        return $component(PAGE_TITLE, SnGenericComponent.class);
    }

    /**
     * Reloads the current page and waits for it to fully load.
     *
     * <p>
     * This method performs a browser navigation refresh on the current page and then calls
     * {@link #waitForPage()} to ensure the page is fully loaded before returning.
     * </p>
     *
     * <p>
     * The reload operation:
     * <ul>
     *   <li>Refreshes the current page using the browser's navigation API</li>
     *   <li>Waits for the document ready state to be "complete"</li>
     *   <li>Waits for page-specific components to be displayed (as defined in {@link #waitForDisplayed()})</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Use Cases:</strong>
     * <ul>
     *   <li>Testing page refresh behavior</li>
     *   <li>Clearing page state and reloading with fresh data</li>
     *   <li>Verifying that pages load correctly from scratch</li>
     *   <li>Recovering from transient errors by reloading the page</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     *  final SnWithPage<HomePage> homePage = SnPage.with(HomePage.class);
     *
     *  homePage.inPage(p -> {
     *      // Perform some actions
     *      p.searchBox.enterText("test");
     *
     *      // Reload the page to clear the search
     *      p.reload();
     *
     *      // Verify the page is back to its initial state
     *      assert p.searchBox.text().isEmpty() : "Search box should be empty after reload";
     * });
     * }</pre>
     * </p>
     *
     * @throws SnUnexpectedPageException If unexpected page is loaded.
     *
     * @see #waitForPage()
     */
    public void reload() {
        Selion.driver().navigate().refresh();
        this.waitForPage();
    }

    /**
     * Performs an action within the context of a new browser window.
     *
     * <p>
     * This method allows executing test code within a different browser window without parameters.
     * The framework automatically handles window switching and cleanup, ensuring that the
     * original window is restored after the action completes.
     * </p>
     *
     * <p>
     * This overload is used when the window action does not require any parameters or
     * return values. It is typically used for window operations that don't need to interact
     * with specific window properties.
     * </p>
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     *  final SnWithPage<HomePage> homePage = SnPage.with(HomePage.class);
     *  final SnWithPage<ExternalPage> exPage = SnPage.with(ExternalPage.class);
     *
     *  homePage.inPage(p -> {
     *      // Click a link that opens a new window
     *      p.externalLink.click();
     *
     *      // Perform actions in the new window
     *      p.inWindow(exPage, p1 -> {
     *          String currentUrl = Selion.driver().getCurrentUrl();
     *          System.out.println("New window URL: " + currentUrl);
     *
     *          // Verify content in the new window
     *          assert currentUrl.contains("external") : "Expected external site URL";
     *
     *          // Closing the window happens automatically.
     *      });
     * });
     *
     * // Control is automatically returned to the original window
     * }</pre>
     * </p>
     *
     * @param predicate a {@link SnWindow.SnWindowAction} functional interface containing
     *                  the action to perform within the window context
     *
     * @see SnWindow
     * @see SnWindow.SnWindowAction
     */
    public <T extends SnPage> void inWindow(SnWithPage<T> withPage, SnWindow.SnWindowAction<T> predicate) {
        new SnWindow().inWindow(withPage, predicate);
    }

    /**
     * Performs an action within the context of a new browser window with controller access.
     *
     * <p>
     * This method allows executing test code within a different browser window with access to
     * a {@link SnWindow.SnWindowActionWithController} that provides methods for controlling window behavior.
     * The framework automatically handles window switching and cleanup, ensuring that the
     * original window is restored after the action completes.
     * </p>
     *
     * <p>
     * This overload is used when the window action needs to access the controller to manage
     * window-specific operations such as switching between windows, closing windows, or
     * accessing window properties.
     * </p>
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     *  final SnWithPage<HomePage> homePage = SnPage.with(HomePage.class);
     *  final SnWithPage<ExternalPage> exPage = SnPage.with(ExternalPage.class);
     *
     *  homePage.inPage(p -> {
     *      // Click a link that opens a new window
     *      p.externalLink.click();
     *
     *      // Perform actions in the new window with controller access. Controller gives access to switch to previous
     *      // windows without closing.
     *      p.inWindow(exPage, (p1, controller) -> {
     *          String currentUrl = Selion.driver().getCurrentUrl();
     *          System.out.println("New window URL: " + currentUrl);
     *
     *          // Verify content in the new window
     *          assert currentUrl.contains("external") : "Expected external site URL";
     *
     *          // Closing the window happens automatically.
     *      });
     * });
     *
     * // Control is automatically returned to the original window
     * }</pre>
     * </p>
     *
     * @param predicate a {@link SnWindow.SnWindowActionWithController} functional interface containing
     *                  the action to perform within the window context with access to window control methods
     *
     * @see SnWindow
     * @see SnWindow.SnWindowActionWithController
     */
    public <T extends SnPage> void inWindow(SnWithPage<T> withPage, SnWindow.SnWindowActionWithController<T> predicate) {
        new SnWindow().inWindow(withPage, predicate);
    }

    /**
     * Performs an action within the context of a browser alert dialog.
     *
     * <p>
     * This method allows executing test code that interacts with JavaScript alert, confirm, or
     * prompt dialogs. The framework automatically switches to the alert and provides it to the
     * provided action for interaction.
     * </p>
     *
     * <p>
     * The alert object provides methods to:
     * <ul>
     *   <li>Get the alert text via {@link Alert#getText()}</li>
     *   <li>Accept (click OK) the alert via {@link Alert#accept()}</li>
     *   <li>Dismiss (click Cancel) the alert via {@link Alert#dismiss()}</li>
     *   <li>Send text to a prompt dialog via {@link Alert#sendKeys(String)}</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     *  final SnWithPage<HomePage> homePage = SnPage.with(HomePage.class);
     *
     *  homePage.inPage(p -> {
     *      // Click button that triggers an alert
     *      p.deleteButton.click();
     *
     *      // Interact with the alert dialog
     *      p.inAlert(alert -> {
     *          String alertText = alert.getText();
     *          System.out.println("Alert message: " + alertText);
     *
     *          // Verify the alert message
     *          assert alertText.contains("Are you sure?") : "Expected confirmation message";
     *
     *          // Accept the alert
     *          alert.accept();
     *      });
     *   });
     * // Continue with test after alert is handled
     * }</pre>
     * </p>
     *
     * @param action a {@link SnAlertAction} functional interface containing the action to perform
     *               with the alert dialog
     *
     * @see SnAlertAction
     * @see Alert
     */
    public void inAlert(SnAlertAction action) {
        final WebDriverWait wait = new WebDriverWait(Selion.driver(), Duration.ofSeconds(1));
        final Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        action.inAlert(alert);
        SnWait.sleep(500);
    }

    /**
     * Functional interface for performing actions on browser alert dialogs.
     *
     * <p>
     * This interface is used with the {@link #inAlert(SnAlertAction)} method to handle
     * JavaScript alert, confirm, and prompt dialogs in a functional programming style.
     * </p>
     *
     * <p>
     * <strong>Typical Implementation:</strong>
     * <pre>{@code
     * // Using lambda expression
     * page.inAlert(alert -> {
     *     String text = alert.getText();
     *     alert.accept();
     * });
     * }</pre>
     * </p>
     *
     * @see #inAlert(SnAlertAction)
     * @see Alert
     */
    public interface SnAlertAction {
        /**
         * Performs an action with the provided alert dialog.
         *
         * <p>
         * This method is called by the framework with an {@link Alert} object representing
         * the current browser alert. The implementation should perform the desired interactions
         * with the alert such as reading its text, accepting it, dismissing it, or sending keys.
         * </p>
         *
         * @param alert the {@link Alert} object representing the browser alert dialog
         * @see Alert#getText()
         * @see Alert#accept()
         * @see Alert#dismiss()
         * @see Alert#sendKeys(String)
         */
        void inAlert(Alert alert);
    }
}