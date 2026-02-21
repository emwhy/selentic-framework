package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.exception.ScUnexpectedPageException;
import org.emwhyware.selentic.lib.selector.ScXPath;
import org.emwhyware.selentic.lib.util.ScWait;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * {@code ScPage} is the concrete base class for all page classes in the Selentic Framework.
 * All page classes must extend from {@code ScPage} when defined.
 *
 * <p>
 * This class extends {@link ScAbstractPage} and provides common page functionality including:
 * <ul>
 *   <li>Access to the page title element</li>
 *   <li>Page reload functionality with automatic wait for page load</li>
 *   <li>Window management and control</li>
 * </ul>
 *
 *
 * <p>
 * <strong>Page Creation Pattern:</strong> {@code ScPage} provides a method for creating
 * and initializing page instances using the {@code with(Class)} factory method. This allows
 * for clean, readable page creation in test code.
 * 
 *
 * <p>
 * <strong>Typical Usage:</strong>
 * <pre>{@code
 * public class HomePage extends ScPage {
 *     private static final ScCssSelector SEARCH_TEXTBOX = _cssSelector.descendant(_id("search"));
 *     private static final ScCssSelector SEARCH_BUTTON = _cssSelector.descendant(_id("search-btn"));
 *     private static final ScCssSelector HEADER_TEXT = _cssSelector.descendant(_className("header"));
 *
 *     public final ScTextbox searchTextbox = $textbox(SEARCH_BOX);
 *     public final ScButton searchButton = $button(SEARCH_BUTTON);
 *     public final ScGenericComponent headerText = $genericComponent(HEADER);
 *
 *     @Override
 *     protected void waitForDisplayed() {
 *         waitForComponent(headerText);
 *         waitForComponent(searchTextbox);
 *     }
 * }
 *
 * // In test code
 * final ScWithPage<HomePage> homePage = ScPage.with(HomePage.class);
 *
 * homePage.inPage(p -> {
 *      p.searchTextbox.enterText("Selenium");
 *      p.searchButton.click();
 * });
 * }</pre>
 *
 *
 * <p>
 * <strong>Key Features:</strong>
 * <ul>
 *   <li><strong>Fluent Page Initialization:</strong> Use {@code with(Class)} to create page instances
 *       with automatic page load waiting through the {@link ScWithPage} fluent builder.</li>
 *   <li><strong>Page Title Access:</strong> Conveniently access the page's HTML title element
 *       through {@link #pageTitle()}.</li>
 *   <li><strong>Page Reload:</strong> Reload the current page and wait for it to fully load
 *       using {@link #reload()}.</li>
 *   <li><strong>Window Management:</strong> Control browser windows and perform window-specific
 *       operations through {@link #inWindow(ScWithPage, ScWindow.ScWindowAction)} and
 *       {@link #inWindow(ScWithPage, ScWindow.ScWindowActionWithController)}.</li>
 * </ul>
 *
 *
 * @see ScAbstractPage
 * @see ScWithPage
 * @see ScWindow
 * @see ScComponent
 */
public abstract class ScPage extends ScAbstractPage {
    private static final ScXPath PAGE_TITLE = _xpath.descendant("title");

    /**
     * Creates a fluent builder for initializing and loading a page of the specified type.
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * // Create and initialize page.
     * final ScWithPage<HomePage> homePage = ScPage.with(HomePage.class);
     * }</pre>
     * 
     *
     * @param <T> the page type to create
     * @param pageType the class of the page to create; must extend from {@code ScPage}
     * @return a {@link ScWithPage} fluent builder for the specified page type
     *
     * @see ScWithPage
     */
    public static <T extends ScPage> ScWithPage<T> with(@NonNull Class<T> pageType) {
        return new ScWithPage<>(pageType);
    }

    /**
     * Protected constructor for {@code ScPage}.
     *
     * <p>
     * Page instances should not be created directly through the constructor.
     * Instead, use the {@link #with(Class)} factory method to create and initialize page instances.
     * 
     */
    protected ScPage() {
    }

    /**
     * Returns a component representing the HTML page title element.
     *
     * <p>
     * This method provides convenient access to the {@code <title>} element of the HTML page.
     * The title element is typically used to verify that the correct page has loaded or to
     * validate page-specific information.
     * 
     *
     * <p>
     * The returned component is a {@link ScGenericComponent} that wraps the title element
     * and allows access to its properties like text content, attributes, etc.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     * final ScWithPage<HomePage> homePage = ScPage.with(HomePage.class);
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
     * 
     *
     * @return a {@link ScGenericComponent} representing the page title element
     */
    public ScGenericComponent pageTitle() {
        return $component(PAGE_TITLE, ScGenericComponent.class);
    }

    /**
     * Reloads the current page and waits for it to fully load.
     *
     * <p>
     * This method performs a browser navigation refresh on the current page and then calls
     * {@link #waitForPage()} to ensure the page is fully loaded before returning.
     * 
     *
     * <p>
     * The reload operation:
     * <ul>
     *   <li>Refreshes the current page using the browser's navigation API</li>
     *   <li>Waits for the document ready state to be "complete"</li>
     *   <li>Waits for page-specific components to be displayed (as defined in {@link #waitForDisplayedPage()})</li>
     * </ul>
     * 
     *
     * <p>
     * <strong>Use Cases:</strong>
     * <ul>
     *   <li>Testing page refresh behavior</li>
     *   <li>Clearing page state and reloading with fresh data</li>
     *   <li>Verifying that pages load correctly from scratch</li>
     *   <li>Recovering from transient errors by reloading the page</li>
     * </ul>
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     *  final ScWithPage<HomePage> homePage = ScPage.with(HomePage.class);
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
     * 
     *
     * @throws ScUnexpectedPageException If unexpected page is loaded.
     *
     * @see #waitForPage()
     */
    public void reload() {
        Selentic.driver().navigate().refresh();
        this.waitForPage();
    }

    /**
     * Performs an action within the context of a new browser window.
     *
     * <p>
     * This method allows executing test code within a different browser window without parameters.
     * The framework automatically handles window switching and cleanup, ensuring that the
     * original window is restored after the action completes.
     * 
     *
     * <p>
     * This overload is used when the window action does not require any parameters or
     * return values. It is typically used for window operations that don't need to interact
     * with specific window properties.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     *  final ScWithPage<HomePage> homePage = ScPage.with(HomePage.class);
     *  final ScWithPage<ExternalPage> exPage = ScPage.with(ExternalPage.class);
     *
     *  homePage.inPage(p -> {
     *      // Click a link that opens a new window
     *      p.externalLink.click();
     *
     *      // Perform actions in the new window
     *      p.inWindow(exPage, p1 -> {
     *          String currentUrl = Selentic.driver().getCurrentUrl();
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
     * 
     *
     * @param predicate a {@link ScWindow.ScWindowAction} functional interface containing
     *                  the action to perform within the window context
     *
     * @see ScWindow
     * @see ScWindow.ScWindowAction
     */
    public <T extends ScPage> void inWindow(@NonNull ScWithPage<T> withPage, ScWindow.ScWindowAction<T> predicate) {
        new ScWindow().inWindow(withPage, predicate);
    }

    /**
     * Performs an action within the context of a new browser window with controller access.
     *
     * <p>
     * This method allows executing test code within a different browser window with access to
     * a {@link ScWindow.ScWindowActionWithController} that provides methods for controlling window behavior.
     * The framework automatically handles window switching and cleanup, ensuring that the
     * original window is restored after the action completes.
     * 
     *
     * <p>
     * This overload is used when the window action needs to access the controller to manage
     * window-specific operations such as switching between windows, closing windows, or
     * accessing window properties.
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     *  final ScWithPage<HomePage> homePage = ScPage.with(HomePage.class);
     *  final ScWithPage<ExternalPage> exPage = ScPage.with(ExternalPage.class);
     *
     *  homePage.inPage(p -> {
     *      // Click a link that opens a new window
     *      p.externalLink.click();
     *
     *      // Perform actions in the new window with controller access. Controller gives access to switch to previous
     *      // windows without closing.
     *      p.inWindow(exPage, (p1, controller) -> {
     *          String currentUrl = Selentic.driver().getCurrentUrl();
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
     * 
     *
     * @param predicate a {@link ScWindow.ScWindowActionWithController} functional interface containing
     *                  the action to perform within the window context with access to window control methods
     *
     * @see ScWindow
     * @see ScWindow.ScWindowActionWithController
     */
    public <T extends ScPage> void inWindow(@NonNull ScWithPage<T> withPage, ScWindow.ScWindowActionWithController<T> predicate) {
        new ScWindow().inWindow(withPage, predicate);
    }

    /**
     * Performs an action within the context of a browser alert dialog.
     *
     * <p>
     * This method allows executing test code that interacts with JavaScript alert, confirm, or
     * prompt dialogs. The framework automatically switches to the alert and provides it to the
     * provided action for interaction.
     * 
     *
     * <p>
     * The alert object provides methods to:
     * <ul>
     *   <li>Get the alert text via {@link Alert#getText()}</li>
     *   <li>Accept (click OK) the alert via {@link Alert#accept()}</li>
     *   <li>Dismiss (click Cancel) the alert via {@link Alert#dismiss()}</li>
     *   <li>Send text to a prompt dialog via {@link Alert#sendKeys(String)}</li>
     * </ul>
     * 
     *
     * <p>
     * <strong>Usage Example:</strong>
     * <pre>{@code
     *  final ScWithPage<HomePage> homePage = ScPage.with(HomePage.class);
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
     * 
     *
     * @param action a {@link ScAlertAction} functional interface containing the action to perform
     *               with the alert dialog
     *
     * @see ScAlertAction
     * @see Alert
     */
    public void inAlert(@NonNull ScAlertAction action) {
        final WebDriverWait wait = new WebDriverWait(Selentic.driver(), Duration.ofSeconds(1));
        final Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        if (alert == null) {
            throw new IllegalStateException("Alert is not present.");
        }
        action.inAlert(alert);
        ScWait.sleep(500);
    }

    /**
     * Functional interface for performing actions on browser alert dialogs.
     *
     * <p>
     * This interface is used with the {@link #inAlert(ScAlertAction)} method to handle
     * JavaScript alert, confirm, and prompt dialogs in a functional programming style.
     * 
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
     *
     *
     * @see #inAlert(ScAlertAction)
     * @see Alert
     */
    public interface ScAlertAction {
        /**
         * Performs an action with the provided alert dialog.
         *
         * <p>
         * This method is called by the framework with an {@link Alert} object representing
         * the current browser alert. The implementation should perform the desired interactions
         * with the alert such as reading its text, accepting it, dismissing it, or sending keys.
         * 
         *
         * @param alert the {@link Alert} object representing the browser alert dialog
         * @see Alert#getText()
         * @see Alert#accept()
         * @see Alert#dismiss()
         * @see Alert#sendKeys(String)
         */
        void inAlert(@NonNull Alert alert);
    }
}