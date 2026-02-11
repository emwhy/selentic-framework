package org.selion_framework.lib;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.selion_framework.lib.exception.SeElementNotFoundException;
import org.selion_framework.lib.exception.SeWaitTimeoutException;
import org.selion_framework.lib.util.SeWait;

import java.util.Optional;

public abstract class SeComponent extends SeAbstractComponent {
    private Optional<SeLocatorNode> locator = Optional.empty();
    private Optional<SeComponent> $callerComponent = Optional.empty();
    private SeAbstractPage $ownerPage;
    private WebElement webElement;
    final static protected SeComponentLocatorBuilder _xpath = new SeComponentLocatorBuilder();
    private boolean ruleVerified = false;

    protected SeComponent() {
    }

    final void setLocator(SeLocatorNode locator) {
        this.locator = Optional.of(locator);
    }

    final void setOwnerPage(SeAbstractPage $page) {
        this.$ownerPage = $page;
    }

    final SeAbstractPage ownerPage() {
        return $ownerPage;
    }

    final void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    final void setCallerComponent(Optional<SeComponent> $component) {
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
            if (this.locator.isPresent() && (this.$callerComponent.isEmpty() || this.locator.get() instanceof SeLocatorNodePage)) {
                this.webElement = Selion.driver().findElement(locator.get().build());
            } else if (this.locator.isPresent()) {
                this.webElement = this.$callerComponent.get().existing().findElement(locator.get().build("."));
            } else {
                throw new SeElementNotFoundException("Locator is not present.");
            }
        }

        return this.webElement;
    }

    protected abstract void rules(SeComponentRule rule);

    private void verifyRules(WebElement element) {
        if (!this.ruleVerified) {
            final SeComponentRule componentRule = new SeComponentRule(element);

            this.rules(componentRule);
            componentRule.verifyRules(this.getClass());
            this.ruleVerified = true;
        }
    }

    protected final WebElement existing() {
        try {
            WebElement element;

            SeWait.waitUntil(this::exists);
            element = webElement();
            this.verifyRules(element);
            return element;
        } catch (SeWaitTimeoutException ex) {
            throw new SeElementNotFoundException("Element does not exist.", ex);
        }
    }

    protected final WebElement displayed() {
        try {
            SeWait.waitUntil(this::isDisplayed);
            return webElement();
        } catch (SeWaitTimeoutException ex) {
            throw new SeElementNotFoundException("Element is not displayed.", ex);
        }
    }

    protected final WebElement scrolled() {
        return this.scrolled(scrollOptions());
    }

    protected final WebElement scrolled(SeScrollOptions options) {
        final WebElement e = displayed();

        Selion.executeScript("arguments[0].scrollIntoView(arguments[1])", this, options.toString());
        return e;
    }

    public boolean exists() {
        try {
            this.webElement().getTagName();

            return true;
        } catch (NoSuchElementException | StaleElementReferenceException ex) {
            return false;
        }
    }

    public boolean isDisplayed() {
        return webElement().isDisplayed();
    }

    protected void waitForDisplayed() {
        SeWait.waitUntil(this::isDisplayed);
    }

    protected SeScrollOptions scrollOptions() {
        return new SeScrollOptions();
    }

    protected void click() {
        this.scrolled().click();
    }

    protected void doubleClick() {
        final Actions actions = new Actions(Selion.driver());

        actions.doubleClick(this.scrolled()).perform();
    }

    protected void clickAt(int x, int y) {
        final Actions actions = new Actions(Selion.driver());

        actions.moveToElement(this.scrolled(), x, y).click().perform();
    }

    protected void doubleClickAt(int x, int y) {
        final Actions actions = new Actions(Selion.driver());

        actions.moveToElement(this.scrolled(), x, y).doubleClick().perform();
    }

    protected void focus() {
        new Actions(Selion.driver())
                .moveToElement(this.scrolled())
                .perform();
    }

    public String text() {
        return this.key();
    }

    public String key() {
        return this.webElement().getText().trim();
    }

    public final String id() {
        return this.webElement().getAttribute("id");
    }

    public final String innerHtml() {
        return this.webElement().getAttribute("innerHTML");
    }

    public final String ownText() {
        return "";
    }

    public final String innerText() {
        return this.webElement().getAttribute("innerText");
    }

    public String tag() {
        return this.webElement().getTagName();
    }

    public Optional<String> attr(String name) {
        String a;

        return (a = this.webElement().getDomAttribute(name)) == null ? Optional.empty() : Optional.of(a);
    }
}
