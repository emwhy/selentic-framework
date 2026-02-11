package org.selion_framework.lib;

import org.openqa.selenium.WebDriver;
import org.selion_framework.lib.exception.SeComponentCreationException;
import org.selion_framework.lib.util.SeLogHandler;
import org.slf4j.Logger;

import java.util.Optional;

public abstract class SeAbstractComponent {
    private static final Logger LOG = SeLogHandler.logger(SeAbstractComponent.class);

    protected SeLocatorProperty _not(SeLocatorProperty locatorProperty) {
        return new SeLocatorNotProperty(locatorProperty);
    }

    protected static SeLocatorPropertyCondition _attr(String attribute) {
        return new SeLocatorPropertyCondition("@" + attribute);
    }

    protected static SeLocatorCssClassesProperty _cssClasses(String... cssClasses) {
        return new SeLocatorCssClassesProperty(cssClasses);
    }

    protected static SeLocatorPropertyCondition _id() {
        return _attr("id");
    }

    protected static SeLocatorPropertyCondition _name() {
        return _attr("name");
    }

    protected static SeLocatorPropertyCondition _type() {
        return _attr("type");
    }

    protected static SeLocatorPropertyCondition _text() {
        return new SeLocatorPropertyCondition("text()");
    }

    protected static SeLocatorIndexProperty _indexFrom(int startIndex) {
        return new SeLocatorIndexProperty(SeLocatorIndexProperty.Conditions.From, startIndex);
    }

    protected static SeLocatorIndexProperty _indexTo(int endIndex) {
        return new SeLocatorIndexProperty(SeLocatorIndexProperty.Conditions.To, endIndex);
    }

    protected static SeLocatorIndexProperty _indexAt(int index) {
        return new SeLocatorIndexProperty(SeLocatorIndexProperty.Conditions.At, index);
    }

    protected static SeLocatorIndexProperty _first() {
        return _indexAt(0);
    }

    protected static SeLocatorIndexProperty _last() {
        return new SeLocatorIndexProperty(SeLocatorIndexProperty.Conditions.Last);
    }

    protected SeGenericComponent $genericComponent(SeLocatorNode locator) {
        return $component(locator, SeGenericComponent.class);
    }

    protected SeRadioButtonGroup $radioButtons(SeLocatorNode locator) {
        return $$components(locator, SeRadioButton.class, SeRadioButtonGroup.class);
    }

    /**
     * Returns a component of specified type with the locator when the component is defined as
     * inner class to containing object.
     * @param locator
     * @param componentType
     * @return
     * @param <T>
     */
    protected <T extends SeComponent> T $component(SeLocatorNode locator, Class<T> componentType) {
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
    protected <T extends SeComponent> T $component(SeLocatorNode locator, Class<T> componentType, SeAbstractComponent containingObject) {
        try {
            T $component;

            $component = containingObject == null ? componentType.getDeclaredConstructor().newInstance() : componentType.getDeclaredConstructor(containingObject.getClass()).newInstance(containingObject);
            $component.setLocator(locator);
            if (this instanceof SeComponent $this) {
                $component.setCallerComponent(Optional.of($this));
                $component.setOwnerPage($this.ownerPage());
            } else {
                $component.setOwnerPage((SeAbstractPage) this);
            }
            return $component;
        } catch (Exception ex) {
            throw new SeComponentCreationException(ex);
        }
    }


    protected <T extends SeComponent> SeComponentCollection<T> $$components(SeLocatorNode locator, Class<T> componentType) {
        SeComponentCollection<T> $$components = new SeComponentCollection<>();

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
    protected <T extends SeComponent> SeComponentCollection<T> $$components(SeLocatorNode locator, Class<T> componentType, SeAbstractComponent containingObject) {
        SeComponentCollection<T> $$components = new SeComponentCollection<>();

        $$components.setLocator(locator);
        $$components.setComponentType(componentType);
        $$components.setContainingObject(Optional.of(containingObject));
        $$components.setOwnerPage(this);

        return $$components;
    }

    protected <T extends SeComponent, R extends SeComponentCollection<T>> R $$components(SeLocatorNode locator, Class<T> componentType, Class<R> componentCollectionType) {
        try {
            R $$components;

            $$components = componentCollectionType.getDeclaredConstructor().newInstance();
            $$components.setLocator(locator);
            $$components.setComponentType(componentType);
            $$components.setOwnerPage(this);
            return $$components;
        } catch (Exception ex) {
            throw new SeComponentCreationException(ex);
        }
    }


    /**
     * Handles frame content.
     * @param frameLocator
     * @param frameContentType
     * @param predicate
     * @param <T>
     */
    protected <T extends SeFrameContent> void $frame(SeLocatorNode frameLocator, Class<T> frameContentType, SeFrameAction<T> predicate) {
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
    protected <T extends SeFrameContent> void $frame(SeLocatorNode frameLocator, Class<T> frameContentType, Object containingObject, SeFrameAction<T> predicate) {
        final WebDriver webDriver = Selion.driver();
        final SeFrame $frame = this.$component(frameLocator, SeFrame.class);
        T $frameContent;

        try {
            if (containingObject == null) {
                $frameContent = frameContentType.getDeclaredConstructor().newInstance();
            } else {
                $frameContent = frameContentType.getDeclaredConstructor(containingObject.getClass()).newInstance(containingObject);
            }
        } catch (Exception ex) {
            throw new SeComponentCreationException(ex);
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
    protected <T extends SeDialog> void $dialog(SeLocatorNode locator, Class<T> componentType, SeDialogAction<T> predicate) {
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
    protected <T extends SeDialog> void $dialog(SeLocatorNode locator, Class<T> componentType, SeAbstractComponent containingObject, SeDialogAction<T> predicate) {
        final T $dialog = containingObject == null ? this.$component(locator, componentType) : this.$component(locator, componentType, containingObject);

        $dialog.waitForDisplayed();
        LOG.debug("Open dialog: {}", $dialog.getClass().getSimpleName());
        predicate.in($dialog);
        LOG.debug("Close dialog: {}", $dialog.getClass().getSimpleName());
        $dialog.waitForHidden();
    }

}
