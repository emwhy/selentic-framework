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

public abstract class SnComponent extends SnAbstractComponent {
    private Optional<SnXPathSelector> locator = Optional.empty();
    private Optional<SnComponent> $callerComponent = Optional.empty();
    private SnAbstractPage $ownerPage;
    private WebElement webElement;
    final static protected SnComponentLocatorBuilder _xpath = new SnComponentLocatorBuilder();
    private boolean ruleVerified = false;

    protected SnComponent() {
    }

    final void setLocator(SnXPathSelector locator) {
        this.locator = Optional.of(locator);
    }

    final void setOwnerPage(SnAbstractPage $page) {
        this.$ownerPage = $page;
    }

    final SnAbstractPage ownerPage() {
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
            if (this.locator.isPresent() && (this.$callerComponent.isEmpty() || this.locator.get() instanceof SnXPathSelectorPage)) {
                this.webElement = Selion.driver().findElement(locator.get().build());
            } else if (this.locator.isPresent()) {
                this.webElement = this.$callerComponent.get().existing().findElement(locator.get().build("."));
            } else {
                throw new SnElementNotFoundException("Locator is not present.");
            }
        }

        return this.webElement;
    }

    protected abstract void rules(SnComponentRule rule);

    private void verifyRules(WebElement element) {
        if (!this.ruleVerified) {
            final SnComponentRule componentRule = new SnComponentRule(element);

            this.rules(componentRule);
            componentRule.verifyRules(this.getClass());
            this.ruleVerified = true;
        }
    }

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

    protected final WebElement displayed() {
        try {
            SnWait.waitUntil(this::isDisplayed);
            return webElement();
        } catch (SnWaitTimeoutException ex) {
            throw new SnElementNotFoundException("Element is not displayed.", ex);
        }
    }

    protected final WebElement scrolled() {
        return this.scrolled(scrollOptions());
    }

    protected final WebElement scrolled(SnScrollOptions options) {
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
        SnWait.waitUntil(this::isDisplayed);
    }

    protected SnScrollOptions scrollOptions() {
        return new SnScrollOptions();
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
        return SeOwnText.removeHtml(this.innerHtml());
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
