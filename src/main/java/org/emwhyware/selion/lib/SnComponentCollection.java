package org.emwhyware.selion.lib;

import org.emwhyware.selion.lib.exception.SnComponentCreationException;
import org.emwhyware.selion.lib.exception.SnEntryNotFoundException;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A Collection for {@link SnComponent} objects.
 *
 * <p>This class provides a high-level API to interact with multiple UI elements simultaneously,
 * supporting lazy-loading of {@link WebElement}s and functional-style operations like
 * filtering and mapping.
 *
 * @param <T> The specific type of {@link SnComponent} contained in this collection (e.g., SnButton, SnTextField).
 * @see SnComponent
 * @see SnAbstractPage
 */
public class SnComponentCollection<T extends SnComponent> implements Iterable<T> {

    private SnSelector selector;
    private Class<T> componentType;
    private SnAbstractComponent $callerComponent;
    private Optional<SnAbstractComponent> containingObject = Optional.empty();

    /**
     * Protected constructor used by the framework for internal instantiation.
     */
    protected SnComponentCollection() {}

    /**
     * Sets the {@link SnSelector} used to locate the elements in this collection.
     *
     * @param selector The selector instance.
     */
    final void setSelector(SnSelector selector) {
        this.selector = selector;
    }

    /**
     * Internal method to set the parent component or page that called this component.
     *
     * @param $componentOrPage a calling component or page.
     */
    final void setCallerComponent(SnAbstractComponent $componentOrPage) {
        this.$callerComponent = $componentOrPage;
    }

    /**
     * Sets the class type of the components to be instantiated within this collection.
     *
     * @param componentType The Class object of the component type.
     */
    final void setComponentType(Class<T> componentType) {
        this.componentType = componentType;
    }

    /**
     * Sets the parent object (typically a component or page) that contains this collection.
     *
     * @param containingObject The containing {@link SnAbstractComponent}.
     */
    final void setContainingObject(SnAbstractComponent containingObject) {
        this.containingObject = Optional.ofNullable(containingObject);
    }

    /**
     * Returns the page that owns this collection.
     *
     * @return The owner page instance.
     */
    final protected SnAbstractPage ownerPage() {
        return $callerComponent instanceof SnAbstractPage ? (SnAbstractPage) $callerComponent : ((SnComponent) $callerComponent).ownerPage();
    }

    /**
     * Retrieves the current list of {@link WebElement}s from the driver based on the defined selector.
     *
     * @return A list of live WebElements.
     */
    private List<WebElement> webElements() {
        if (this.$callerComponent instanceof SnAbstractPage || this.selector.isAbsolute()) {
            return Selion.driver().findElements(this.selector.build());
        } else {
            return ((SnComponent) this.$callerComponent).existingElement().findElements(this.selector instanceof SnXPath ? ((SnXPath) this.selector).build(true) : this.selector.build());
        }
    }

    /**
     * Factory method to instantiate a specific {@link SnComponent} from a {@link WebElement}.
     *
     * @param webElement The underlying Selenium element.
     * @param componentType The class to instantiate.
     * @param containingObject The parent context.
     * @return A new instance of type T.
     * @throws SnComponentCreationException if reflection-based instantiation fails.
     */
    private T $componentFromElement(WebElement webElement, Class<T> componentType, Optional<SnAbstractComponent> containingObject) {
        try {
            T component;
            if (containingObject.isEmpty()) {
                component = componentType.getDeclaredConstructor().newInstance();
            } else {
                component = componentType.getDeclaredConstructor(containingObject.get().getClass()).newInstance(containingObject.get());
            }
            component.setWebElement(webElement);
            component.setCallerComponent(this.$callerComponent);
            component.setSelector(this.selector);

            return component;
        } catch (Exception ex) {
            throw new SnComponentCreationException(ex);
        }
    }

    /**
     * Collects the visible text from all components in this collection.
     *
     * @return A List of strings containing the text of each component.
     */
    public List<String> texts() {
        return this.stream().map(SnComponent::text).toList();
    }

    /**
     * Retrieves the component at the specified index.
     *
     * @param index The zero-based index of the component.
     * @return The component at the given index.
     * @throws SnEntryNotFoundException if the index is out of bounds.
     */
    public T at(int index) {
        return this.stream().skip(index).findFirst().orElseThrow(() -> new SnEntryNotFoundException(index));
    }

    /**
     * Retrieves a component by its unique key.
     *
     * @param key The key string to match.
     * @return The matching component.
     * @throws SnEntryNotFoundException if no component matches the key.
     */
    public T entry(String key) {
        return this.stream().filter(c -> c.key().equals(key)).findFirst().orElseThrow(() -> new SnEntryNotFoundException(key));
    }

    /**
     * Returns a sequential {@link Stream} with this collection as its source.
     *
     * @return A stream of components of type T.
     */
    public Stream<T> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(this.iterator(), Spliterator.ORDERED), false);
    }

    /**
     * Returns the number of elements currently found by the selector.
     *
     * @return The size of the collection.
     */
    public int size() {
        return webElements().size();
    }

    /**
     * Checks if the collection is empty (no elements found in the DOM).
     *
     * @return true if no elements are found, false otherwise.
     */
    public boolean isEmpty() {
        return webElements().isEmpty();
    }

    /**
     * Checks if a component with the specified key exists in the collection.
     *
     * @param key The key to look for.
     * @return true if a match is found.
     */
    public boolean containsKey(String key) {
        return this.stream().anyMatch(c -> c.key().equals(key));
    }

    /**
     * Filters the collection based on the provided predicate.
     *
     * @param predicate The condition to filter by.
     * @return A list of components that match the predicate.
     */
    public List<T> filter(Predicate<T> predicate) {
        return this.stream().filter(predicate).toList();
    }

    /**
     * Returns an iterator over the components in this collection.
     * Each iteration triggers the mapping of a {@link WebElement} to a component instance.
     *
     * @return An {@link Iterator}.
     */
    @Override
    public Iterator<T> iterator() {
        final List<WebElement> webElements = webElements();
        return webElements.stream().map($e -> this.$componentFromElement($e, componentType, this.containingObject)).iterator();
    }
}
