package org.selion_framework.lib;

import org.openqa.selenium.WebElement;
import org.selion_framework.lib.exception.SnComponentCreationException;
import org.selion_framework.lib.exception.SnEntryNotFoundException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A Collection for {@link SnComponent} objects.
 *
 * <p>This class provides a high-level API to interact with multiple UI elements simultaneously,
 * supporting lazy-loading of {@link WebElement}s and functional-style operations like
 * filtering and mapping.</p>
 *
 * @param <T> The specific type of {@link SnComponent} contained in this collection (e.g., SnButton, SnTextField).
 * @see SnComponent
 * @see SnAbstractPage
 */
public class SnComponentCollection<T extends SnComponent> implements Iterable<T> {

    private SnSelector selector;
    private Class<T> componentType;
    private Optional<SnComponent> $callerComponent = Optional.empty();
    private Optional<SnAbstractComponent> containingObject = Optional.empty();
    private SnAbstractPage $ownerPage;

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
     * Associates this collection with its parent page.
     *
     * @param abstractComponent The component or page to derive the owner page from.
     * @throws SnComponentCreationException If the page context cannot be resolved.
     */
    final void setOwnerPage(SnAbstractComponent abstractComponent) {
        switch (abstractComponent) {
            case SnAbstractPage optPage -> this.$ownerPage = optPage;
            case SnComponent optComponent -> this.$ownerPage = optComponent.ownerPage();
            case null, default -> throw new SnComponentCreationException("Failed to set page where the component belongs to.");
        }
    }

    /**
     * Returns the page that owns this collection.
     *
     * @param <P> The expected subtype of {@link SnAbstractPage}.
     * @return The owner page instance.
     */
    final protected <P extends SnAbstractPage> P ownerPage() {
        return (P) $ownerPage;
    }

    /**
     * Retrieves the current list of {@link WebElement}s from the driver based on the defined selector.
     *
     * @return A list of live WebElements.
     */
    private List<WebElement> webElements() {
        if (this.$callerComponent.isEmpty() || this.selector instanceof SnXPathPage) {
            return Selion.driver().findElements(this.selector.build());
        } else {
            return this.$callerComponent.get().existing().findElements(
                    this.selector instanceof SnXPath ? ((SnXPath) this.selector).build(true) : this.selector.build());
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
            component.setOwnerPage(this.$ownerPage);
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
