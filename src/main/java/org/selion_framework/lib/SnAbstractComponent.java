package org.selion_framework.lib;

import org.openqa.selenium.WebDriver;
import org.selion_framework.lib.exception.SnComponentCreationException;
import org.selion_framework.lib.util.SnLogHandler;
import org.slf4j.Logger;

import java.util.Optional;

public abstract class SnAbstractComponent {
    private static final Logger LOG = SnLogHandler.logger(SnAbstractComponent.class);

    protected SnSelectorProperty _not(SnSelectorProperty selectorProperty) {
        return new SnSelectorNotProperty(selectorProperty);
    }

    protected static SnSelectorAttributeCondition _attr(String attribute) {
        return new SnSelectorAttributeCondition("@", attribute);
    }

    protected static SnSelectorCssClassesProperty _cssClasses(String... cssClasses) {
        return new SnSelectorCssClassesProperty(cssClasses);
    }

    protected static SnSelectorTagProperty _tag(String tag) {
        return new SnSelectorTagProperty(tag);
    }

    protected static SnSelectorAttributeCondition _id() {
        return _attr("id");
    }

    protected static SnSelectorIdProperty _id(String id) {
        return new SnSelectorIdProperty(id);
    }

    protected static SnSelectorAttributeCondition _name() {
        return _attr("name");
    }

    protected static SnSelectorAttributeCondition _type() {
        return _attr("type");
    }

    protected static SnSelectorTextCondition _text() {
        return new SnSelectorTextCondition();
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

    protected static SnSelectorNthOfTypeProperty _nthOfType(int index) {
        return new SnSelectorNthOfTypeProperty(index);
    }

    protected SnGenericComponent $genericComponent(SnSelector selector) {
        return $component(selector, SnGenericComponent.class);
    }

    protected SnTextbox $textbox(SnSelector selector) {
        return $component(selector, SnTextbox.class);
    }

    protected SnCheckbox $checkbox(SnSelector selector) {
        return $component(selector, SnCheckbox.class);
    }

    protected SnDropdown $dropdown(SnSelector selector) {
        return $component(selector, SnDropdown.class);
    }

    protected SnMultiSelect $multiSelect(SnSelector selector) {
        return $component(selector, SnMultiSelect.class);
    }

    protected SnLink $link(SnSelector selector) {
        return $component(selector, SnLink.class);
    }

    protected SnImage $image(SnSelector selector) {
        return $component(selector, SnImage.class);
    }

    protected SnRadioButtonGroup $radioButtons(SnSelector selector) {
        return $$components(selector, SnRadioButton.class, SnRadioButtonGroup.class);
    }

    protected void waitForDisplayed() {}

    /**
     * Returns a component of specified type with the selector when the component is defined as
     * inner class to containing object.
     * @param selector
     * @param componentType
     * @return
     * @param <T>
     */
    protected <T extends SnComponent> T $component(SnSelector selector, Class<T> componentType) {
        return $component(selector, componentType, null);
    }

    /**
     * Returns a component of specified type with the selector when the component is defined as
     * inner class to containing object.
     * @param selector
     * @param componentType
     * @param containingObject
     * @return
     * @param <T>
     */
    protected <T extends SnComponent> T $component(SnSelector selector, Class<T> componentType, SnAbstractComponent containingObject) {
        try {
            T $component;

            $component = containingObject == null ? componentType.getDeclaredConstructor().newInstance() : componentType.getDeclaredConstructor(containingObject.getClass()).newInstance(containingObject);
            $component.setSelector(selector);
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


    protected <T extends SnComponent> SnComponentCollection<T> $$components(SnSelector selector, Class<T> componentType) {
        SnComponentCollection<T> $$components = new SnComponentCollection<>();

        $$components.setSelector(selector);
        $$components.setComponentType(componentType);
        $$components.setContainingObject(Optional.empty());
        $$components.setOwnerPage(this);

        return $$components;
    }

    /**
     * Returns a collection of components of specified type with the selector when the component is defined as
     * inner class to containing object.
     * @param selector
     * @param componentType
     * @param containingObject
     * @return
     * @param <T>
     */
    protected <T extends SnComponent> SnComponentCollection<T> $$components(SnSelector selector, Class<T> componentType, SnAbstractComponent containingObject) {
        SnComponentCollection<T> $$components = new SnComponentCollection<>();

        $$components.setSelector(selector);
        $$components.setComponentType(componentType);
        $$components.setContainingObject(Optional.of(containingObject));
        $$components.setOwnerPage(this);

        return $$components;
    }

    protected <T extends SnComponent, R extends SnComponentCollection<T>> R $$components(SnSelector selector, Class<T> componentType, Class<R> componentCollectionType) {
        try {
            R $$components;

            $$components = componentCollectionType.getDeclaredConstructor().newInstance();
            $$components.setSelector(selector);
            $$components.setComponentType(componentType);
            $$components.setOwnerPage(this);
            return $$components;
        } catch (Exception ex) {
            throw new SnComponentCreationException(ex);
        }
    }


    /**
     * Handles frame content.
     * @param frameSelector
     * @param frameContentType
     * @param predicate
     * @param <T>
     */
    protected <T extends SnFrameContent> void $frame(SnSelector frameSelector, Class<T> frameContentType, SnFrameAction<T> predicate) {
        $frame(frameSelector, frameContentType, null, predicate);
    }

    /**
     * Handles frame content.
     * @param frameSelector
     * @param frameContentType
     * @param containingObject
     * @param predicate
     * @param <T>
     */
    protected <T extends SnFrameContent> void $frame(SnSelector frameSelector, Class<T> frameContentType, SnAbstractComponent containingObject, SnFrameAction<T> predicate) {
        final WebDriver webDriver = Selion.driver();
        final SnFrame $frame = this.$component(frameSelector, SnFrame.class);
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
     * @param selector
     * @param componentType
     * @param predicate
     * @param <T>
     */
    protected <T extends SnDialog> void $dialog(SnSelector selector, Class<T> componentType, SnDialogAction<T> predicate) {
        $dialog(selector, componentType, null, predicate);
    }

    /**
     * Handles any dialog component in legible coding pattern.
     * @param selector
     * @param componentType
     * @param containingObject
     * @param predicate
     * @param <T>
     */
    protected <T extends SnDialog> void $dialog(SnSelector selector, Class<T> componentType, SnAbstractComponent containingObject, SnDialogAction<T> predicate) {
        final T $dialog = containingObject == null ? this.$component(selector, componentType) : this.$component(selector, componentType, containingObject);

        $dialog.waitForDisplayed();
        LOG.debug("Open dialog: {}", $dialog.getClass().getSimpleName());
        predicate.in($dialog);
        LOG.debug("Close dialog: {}", $dialog.getClass().getSimpleName());
        $dialog.waitForHidden();
    }

}
