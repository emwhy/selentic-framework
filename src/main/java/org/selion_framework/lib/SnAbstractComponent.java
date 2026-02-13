package org.selion_framework.lib;

import org.openqa.selenium.WebDriver;
import org.selion_framework.lib.exception.SnComponentCreationException;
import org.selion_framework.lib.util.SnLogHandler;
import org.slf4j.Logger;

import java.util.Optional;

public abstract class SnAbstractComponent {
    private static final Logger LOG = SnLogHandler.logger(SnAbstractComponent.class);

    protected SnSelectorProperty _not(SnSelectorProperty locatorProperty) {
        return new SnSelectorNotProperty(locatorProperty);
    }

    protected static SnSelectorPropertyCondition _attr(String attribute) {
        return new SnSelectorPropertyCondition("@" + attribute);
    }

    protected static SnSelectorCssClassesProperty _cssClasses(String... cssClasses) {
        return new SnSelectorCssClassesProperty(cssClasses);
    }

    protected static SnSelectorPropertyCondition _id() {
        return _attr("id");
    }

    protected static SnSelectorPropertyCondition _name() {
        return _attr("name");
    }

    protected static SnSelectorPropertyCondition _type() {
        return _attr("type");
    }

    protected static SnSelectorPropertyCondition _text() {
        return new SnSelectorPropertyCondition("text()");
    }

    protected static SnSelectorIndexProperty _indexFrom(int startIndex) {
        return new SnSelectorIndexProperty(SnSelectorIndexProperty.Conditions.From, startIndex);
    }

    protected static SnSelectorIndexProperty _indexTo(int endIndex) {
        return new SnSelectorIndexProperty(SnSelectorIndexProperty.Conditions.To, endIndex);
    }

    protected static SnSelectorIndexProperty _indexAt(int index) {
        return new SnSelectorIndexProperty(SnSelectorIndexProperty.Conditions.At, index);
    }

    protected static SnSelectorIndexProperty _first() {
        return _indexAt(0);
    }

    protected static SnSelectorIndexProperty _last() {
        return new SnSelectorIndexProperty(SnSelectorIndexProperty.Conditions.Last);
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
