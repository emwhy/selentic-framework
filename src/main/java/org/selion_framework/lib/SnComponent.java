package org.selion_framework.lib;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.selion_framework.lib.exception.SnElementNotFoundException;
import org.selion_framework.lib.exception.SnInvalidHtmlException;
import org.selion_framework.lib.exception.SnWaitTimeoutException;
import org.selion_framework.lib.util.SnWait;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *     SnComponent is the base class for all component classes. All component class must extend from SnComponent or its
 *     subclass when defined.
 * </p>
 * <p>
 *
 * The basic class structure look like this.
 *
 * <pre>{@code
 * // Must extend from SnComponent.
 * public class SnSanityTestLongListEntryComponent extends SnComponent {
 *     // Selectors are defined at the top.
 *     private static final SnCssSelector TITLE_TEXT = _cssSelector.descendant(_cssClasses("title"));
 *     private static final SnCssSelector CHECKBOX = _cssSelector.descendant(_type().is("checkbox"));
 *     private static final SnCssSelector TEXTBOX = _cssSelector.descendant(_type().is("text"));
 *
 *     // Requires this override. This defines what web element properties are required (tag, ID, CSS class, attributes, etc)
 *     // are required to be this component. Trying to assign a different web element to this component class would
 *     // throw exception.
 *     // This is also useful in searching for existing component, ensuring that there is no duplicates.
 *     @Override
 *     protected void rules(SnComponentRule rule) {
 *         rule.tag().is("div");
 *         rule.cssClasses().has("long-component-list-entry");
 *     }
 *
 *     // "key()" value is important when used in SnComponentCollection, as it would allow finding the particular
 *     // instance of the component from list quickly.
 *     @Override
 *     public String key() {
 *         return titleText.key();
 *     }
 *
 *     // A list of components within this component.
 *     public final SnGenericComponent titleText = $genericComponent(TITLE_TEXT);
 *     public final SnCheckbox checkbox = $component(CHECKBOX, SnCheckbox.class);
 *     public final SnTextbox textbox = $component(TEXTBOX, SnTextbox.class);
 * }
 * }</pre>
 * </p>
 *
 *
 */
public abstract class SnComponent extends SnAbstractComponent {
    private Optional<SnSelector> selector = Optional.empty();
    private Optional<SnComponent> $callerComponent = Optional.empty();
    private SnAbstractPage $ownerPage;
    private WebElement webElement;
    final static protected SnComponentXPathBuilder _xpath = new SnComponentXPathBuilder();
    final static protected SnComponentCssSelectorBuilder _cssSelector = new SnComponentCssSelectorBuilder();
    private boolean ruleVerified = false;

    /**
     * The components are not directly instantiated through calling the constructor. Use $component(selector, type) to
     * create the instance of a component.
     */
    protected SnComponent() {
    }

    final void setSelector(SnSelector selector) {
        this.selector = Optional.of(selector);
    }

    final void setOwnerPage(SnAbstractPage $page) {
        this.$ownerPage = $page;
    }

    /**
     * The page class that contained this instance of component.
     * @return
     */
    final protected SnAbstractPage ownerPage() {
        return $ownerPage;
    }

    final void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    final void setCallerComponent(Optional<SnComponent> $component) {
        this.$callerComponent = $component;
    }

    private WebElement webElement() {
        if (this.webElement != null) {
            // Check if the stored web element is still valid. If not, set it to null, so it
            // can be reinitialized.
            try {
                this.webElement.getTagName();
            } catch (NoSuchElementException | StaleElementReferenceException ex) {
                this.webElement = null;
            }
        }
        if (this.webElement == null) {
            if (this.selector.isPresent() && (this.$callerComponent.isEmpty() || this.selector.get() instanceof SnXPathPage)) {
                this.webElement = Selion.driver().findElement(selector.get().build());
            } else if (this.selector.isPresent()) {
                this.webElement = selector.get() instanceof SnXPath ? this.$callerComponent.get().existing().findElement(((SnXPath) selector.get()).build(".")) : this.$callerComponent.get().existing().findElement(selector.get().build());
            } else {
                throw new SnElementNotFoundException("Selector is not present.");
            }
        }

        return this.webElement;
    }

    /**
     * Set property rules for the component to be referred with a web element. This is important in ensuring that
     * intended web element type gets referred to by this component, and for help ensuring that no duplicate
     * component classes are implemented, allowing searching by web element properties like class and attributes.
     * @param rule
     */
    protected abstract void rules(SnComponentRule rule);

    private void verifyRules(WebElement element) {
        if (!this.ruleVerified) {
            final SnComponentRule componentRule = new SnComponentRule(element);

            this.rules(componentRule);
            componentRule.verifyRules(this.getClass());
            this.ruleVerified = true;
        }
    }

    /**
     * Returns a instance of WebElement after ensuring that it exists in DOM.
     * @return
     */
    protected final WebElement existing() {
        try {
            WebElement element;

            SnWait.waitUntil(this::exists);
            element = webElement();
            this.verifyRules(element);
            return element;
        } catch (SnWaitTimeoutException ex) {
            throw new SnElementNotFoundException("Element does not exist.", ex);
        }
    }

    /**
     * Returns an instance of WebElement after ensuring that the web element is displayed.
     * @return
     */
    protected final WebElement displayed() {
        try {
            this.waitForDisplayed();
            return webElement();
        } catch (SnWaitTimeoutException ex) {
            throw new SnElementNotFoundException("Element is not displayed.", ex);
        }
    }

    /**
     * Returns an instance of WebElement after scrolling to the web element, often used before interacting with it.
     * @return
     */
    protected final WebElement scrolled() {
        return this.scrolled(scrollOptions());
    }

    /**
     * Returns an instance of WebElement after scrolling to the web element, often used before interacting with it.
     * @param options Specify scroll option.
     * @return
     */
    protected final WebElement scrolled(SnScrollOptions options) {
        final WebElement e = displayed();

        Selion.executeScript("arguments[0].scrollIntoView(arguments[1])", this, options.toString());
        return e;
    }

    /**
     * Returns true if the component exists in DOM by default.
     * @return
     */
    public final boolean exists() {
        try {
            this.webElement().getTagName();

            return true;
        } catch (NoSuchElementException | StaleElementReferenceException ex) {
            return false;
        }
    }

    /**
     * Returns true if the component is displayed. This method can be overridden as needed, as sometimes, the status
     * of "isDisplayed" may depend on another properties. This method is used in {@link #waitForDisplayed()} and
     * {@link #displayed()}, so it would affect their waiting behavior.
     * @return
     */
    public boolean isDisplayed() {
        return webElement().isDisplayed();
    }

    /**
     * Waits for a component to be displayed, as defined by {@link #isDisplayed()}.
     */
    protected void waitForDisplayed() {
        SnWait.waitUntil(this::isDisplayed);
    }

    /**
     * Scroll options can be passed to {@link #scrolled(SnScrollOptions)} to change scrolling behavior.
     * @return
     */
    protected SnScrollOptions scrollOptions() {
        return new SnScrollOptions();
    }

    /**
     * Clicks this component. This is protected, so it can be used within the extended component class as needed. If
     * click needs to be public, extend from {@link SnClickableComponent} instead.
     */
    protected void click() {
        this.scrolled().click();
    }

    /**
     * Double-clicks this component. This is protected, so it can be used within the extended component class as needed. If
     * click needs to be public, extend from {@link SnClickableComponent} instead.
     */
    protected void doubleClick() {
        final Actions actions = new Actions(Selion.driver());

        actions.doubleClick(this.scrolled()).perform();
    }

    /**
     * Clicks this component. This is protected, so it can be used within the extended component class as needed. If
     * click needs to be public, extend from {@link SnClickableComponent} instead.
     * @param x
     * @param y
     */
    protected void clickAt(int x, int y) {
        final Actions actions = new Actions(Selion.driver());

        actions.moveToElement(this.scrolled(), x, y).click().perform();
    }

    /**
     * Double-clicks this component. This is protected, so it can be used within the extended component class as needed. If
     * click needs to be public, extend from {@link SnClickableComponent} instead.
     * @param x
     * @param y
     */
    protected void doubleClickAt(int x, int y) {
        final Actions actions = new Actions(Selion.driver());

        actions.moveToElement(this.scrolled(), x, y).doubleClick().perform();
    }

    /**
     * Move focus to this component.
     */
    protected void focus() {
        new Actions(Selion.driver())
                .moveToElement(this.scrolled())
                .perform();
    }

    /**
     * Returns the text from this component. By default, it returns the same value as {@link #key()}. It should be
     * overridden to provide proper text value as needed.
     * @return
     */
    public String text() {
        return this.key();
    }

    /**
     * <p>
     *     By default, it returns getText() text. It should be overridden as needed.
     * </p>
     * <p>
     *     In particular, the string returned by key() is used as a key value in {@link SnComponentCollection}, so overriding
     *     it to return proper string value would make searching within the list simpler.
     * </p>
     * @return
     */
    public String key() {
        return this.webElement().getText().trim();
    }

    /**
     * Returns id of the component, if it exists.
     * @return
     */
    public final Optional<String> id() {
        String id;

        return (id = this.webElement().getAttribute("id")) == null ? Optional.empty() : Optional.of(id);
    }

    /**
     * Returns inner HTML text.
     * @return
     */
    public final String innerHtml() {
        return this.webElement().getAttribute("innerHTML");
    }

    /**
     * Returns own text from this node only. It strips out all texts in sub nodes.
     * @return
     */
    public final String ownText() {
        return SeOwnText.removeHtml(this.innerHtml());
    }

    /**
     * Returns inner text.
     * @return
     */
    public final String innerText() {
        return this.webElement().getAttribute("innerText");
    }

    /**
     * Returns tag.
     * @return
     */
    public String tag() {
        return this.webElement().getTagName();
    }

    /**
     * Returns specified attribute, if one exists.
     * @param name
     * @return
     */
    public Optional<String> attr(String name) {
        String a;

        return (a = this.webElement().getDomAttribute(name)) == null ? Optional.empty() : Optional.of(a);
    }

    /**
     * A static class that computes "own text".
     */
    private static class SeOwnText {
        private static final Set<String> VOIDED_TAGS = Set.of("br", "img", "hr", "input", "meta", "link", "source", "area", "base", "col", "embed", "param", "track", "wbr");
        private static final Pattern TAG_PATTERN = Pattern.compile("<(\\w+)[^>]*?>([^<^>]*?)</\\s*?\\1\\s*?>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        private static final Pattern ANY_TAG = Pattern.compile("<(\\w+)[^>]*?>|</\\s*?\\w+\\s*?>|<\\w+\\s*?/>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);


        private static String removeHtml(String originalHtmlText) {
            Matcher matcher;
            String resultText = originalHtmlText.replaceAll("\\s+", " ");

            for (String voidedPattern : voidedPatterns()) {
                resultText = resultText.replaceAll(voidedPattern, "");
            }

            while ((matcher = TAG_PATTERN.matcher(resultText)).find()) {
                resultText = matcher.replaceFirst("");
            }

            if (ANY_TAG.matcher(resultText).find()) {
                throw new SnInvalidHtmlException("Cannot parse HTML text: \n\n" + originalHtmlText);
            }

            return resultText.trim();
        }

        private static List<String> voidedPatterns() {
            return VOIDED_TAGS.stream().map(s -> "<" + s + "[^>]*?>|<\" + s + \"[^>]*?/>").toList();
        }

    }
}
