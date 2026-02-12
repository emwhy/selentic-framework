package org.selion_framework.lib;

import org.openqa.selenium.WebDriver;
import org.selion_framework.lib.exception.SnComponentCreationException;
import org.selion_framework.lib.util.SnLogHandler;
import org.slf4j.Logger;

import java.util.Optional;

public abstract class SnAbstractComponent {
    private static final Logger LOG = SnLogHandler.logger(SnAbstractComponent.class);

    protected SnLocatorProperty _not(SnLocatorProperty locatorProperty) {
        return new SnLocatorNotProperty(locatorProperty);
    }

    protected static SnLocatorPropertyCondition _attr(String attribute) {
        return new SnLocatorPropertyCondition("@" + attribute);
    }

    protected static SnLocatorCssClassesProperty _cssClasses(String... cssClasses) {
        return new SnLocatorCssClassesProperty(cssClasses);
    }

    protected static SnLocatorPropertyCondition _id() {
        return _attr("id");
    }

    protected static SnLocatorPropertyCondition _name() {
        return _attr("name");
    }

    protected static SnLocatorPropertyCondition _type() {
        return _attr("type");
    }

    protected static SnLocatorPropertyCondition _text() {
        return new SnLocatorPropertyCondition("text()");
    }

    protected static SnLocatorIndexProperty _indexFrom(int startIndex) {
        return new SnLocatorIndexProperty(SnLocatorIndexProperty.Conditions.From, startIndex);
    }

    protected static SnLocatorIndexProperty _indexTo(int endIndex) {
        return new SnLocatorIndexProperty(SnLocatorIndexProperty.Conditions.To, endIndex);
    }

    protected static SnLocatorIndexProperty _indexAt(int index) {
        return new SnLocatorIndexProperty(SnLocatorIndexProperty.Conditions.At, index);
    }

    protected static SnLocatorIndexProperty _first() {
        return _indexAt(0);
    }

    protected static SnLocatorIndexProperty _last() {
        return new SnLocatorIndexProperty(SnLocatorIndexProperty.Conditions.Last);
    }

    protected SnGenericComponent $genericComponent(SnXPathSelector locator) {
        return $component(locator, SnGenericComponent.class);
    }

    protected SnRadioButtonGroup $radioButtons(SnXPathSelector locator) {
        return $$components(locator, SnRadioButton.class, SnRadioButtonGroup.class);
    }

    /**
     * Returns a component of specified type with the locator when the component is defined as
     * inner class to containing object.
     * @param locator
     * @param componentType
     * @return
     * @param <T>
     */
    protected <T extends SnComponent> T $component(SnXPathSelector locator, Class<T> componentType) {
        return $component(locator, componentType, null);
    }

    /**
     * Returns a component of specified type with the locator when the component is defined as
     * inner class to containing object.
     * @param locator
     * @param componentType
     * @param containingObject
     * @return
     * @param <T>
     */
    protected <T extends SnComponent> T $component(SnXPathSelector locator, Class<T> componentType, SnAbstractComponent containingObject) {
        try {
            T $component;

            $component = containingObject == null ? componentType.getDeclaredConstructor().newInstance() : componentType.getDeclaredConstructor(containingObject.getClass()).newInstance(containingObject);
            $component.setLocator(locator);
            if (this instanceof SnComponent $this) {
                $component.setCallerComponent(Optional.of($this));
                $component.setOwnerPage($this.ownerPage());
            } else {
                $component.setOwnerPage((SnAbstractPage) this);
            }
            return $component;
        } catch (Exception ex) {
            throw new SnComponentCreationException(ex);
        }
    }


    protected <T extends SnComponent> SnComponentCollection<T> $$components(SnXPathSelector locator, Class<T> componentType) {
        SnComponentCollection<T> $$components = new SnComponentCollection<>();

        $$components.setLocator(locator);
        $$components.setComponentType(componentType);
        $$components.setContainingObject(Optional.empty());
        $$components.setOwnerPage(this);

        return $$components;
    }

    /**
     * Returns a collection of components of specified type with the locator when the component is defined as
     * inner class to containing object.
     * @param locator
     * @param componentType
     * @param containingObject
     * @return
     * @param <T>
     */
    protected <T extends SnComponent> SnComponentCollection<T> $$components(SnXPathSelector locator, Class<T> componentType, SnAbstractComponent containingObject) {
        SnComponentCollection<T> $$components = new SnComponentCollection<>();

        $$components.setLocator(locator);
        $$components.setComponentType(componentType);
        $$components.setContainingObject(Optional.of(containingObject));
        $$components.setOwnerPage(this);

        return $$components;
    }

    protected <T extends SnComponent, R extends SnComponentCollection<T>> R $$components(SnXPathSelector locator, Class<T> componentType, Class<R> componentCollectionType) {
        try {
            R $$components;

            $$components = componentCollectionType.getDeclaredConstructor().newInstance();
            $$components.setLocator(locator);
            $$components.setComponentType(componentType);
            $$components.setOwnerPage(this);
            return $$components;
        } catch (Exception ex) {
            throw new SnComponentCreationException(ex);
        }
    }


    /**
     * Handles frame content.
     * @param frameLocator
     * @param frameContentType
     * @param predicate
     * @param <T>
     */
    protected <T extends SnFrameContent> void $frame(SnXPathSelector frameLocator, Class<T> frameContentType, SnFrameAction<T> predicate) {
        $frame(frameLocator, frameContentType, null, predicate);
    }

    /**
     * Handles frame content.
     * @param frameLocator
     * @param frameContentType
     * @param containingObject
     * @param predicate
     * @param <T>
     */
    protected <T extends SnFrameContent> void $frame(SnXPathSelector frameLocator, Class<T> frameContentType, Object containingObject, SnFrameAction<T> predicate) {
        final WebDriver webDriver = Selion.driver();
        final SnFrame $frame = this.$component(frameLocator, SnFrame.class);
        T $frameContent;

        try {
            if (containingObject == null) {
                $frameContent = frameContentType.getDeclaredConstructor().newInstance();
            } else {
                $frameContent = frameContentType.getDeclaredConstructor(containingObject.getClass()).newInstance(containingObject);
            }
        } catch (Exception ex) {
            throw new SnComponentCreationException(ex);
        }

        try {
            $frame.waitForDisplayed();
            webDriver.switchTo().frame($frame.scrolled());

            $frameContent.waitForPage();
            predicate.inFrame($frameContent);
        } finally {
            webDriver.switchTo().parentFrame();
        }
    }


    /**
     * Handles any dialog component in legible coding pattern.
     * @param locator
     * @param componentType
     * @param predicate
     * @param <T>
     */
    protected <T extends SnDialog> void $dialog(SnXPathSelector locator, Class<T> componentType, SnDialogAction<T> predicate) {
        $dialog(locator, componentType, null, predicate);
    }

    /**
     * Handles any dialog component in legible coding pattern.
     * @param locator
     * @param componentType
     * @param containingObject
     * @param predicate
     * @param <T>
     */
    protected <T extends SnDialog> void $dialog(SnXPathSelector locator, Class<T> componentType, SnAbstractComponent containingObject, SnDialogAction<T> predicate) {
        final T $dialog = containingObject == null ? this.$component(locator, componentType) : this.$component(locator, componentType, containingObject);

        $dialog.waitForDisplayed();
        LOG.debug("Open dialog: {}", $dialog.getClass().getSimpleName());
        predicate.in($dialog);
        LOG.debug("Close dialog: {}", $dialog.getClass().getSimpleName());
        $dialog.waitForHidden();
    }

}
