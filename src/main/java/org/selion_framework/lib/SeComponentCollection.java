package org.selion_framework.lib;

import org.openqa.selenium.WebElement;
import org.selion_framework.lib.exception.SeComponentCreationException;
import org.selion_framework.lib.exception.SeEntryNotFoundException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SeComponentCollection<T extends SeComponent> implements Iterable<T> {
    private SeLocatorNode locator;
    private Class<T> componentType;
    private Optional<SeComponent> $callerComponent = Optional.empty();
    private Optional<SeAbstractComponent> containingObject = Optional.empty();
    private SeAbstractPage $ownerPage;

    protected SeComponentCollection() {}


    final void setLocator(SeLocatorNode locator) {
        this.locator = locator;
    }

    final void setComponentType(Class<T> componentType) {
        this.componentType = componentType;
    }

    final void setContainingObject(Optional<SeAbstractComponent> containingObject) {
        this.containingObject = containingObject;
    }

    final void setOwnerPage(SeAbstractComponent abstractComponent) {
        switch (abstractComponent) {
            case SeAbstractPage optPage -> this.$ownerPage = optPage;
            case SeComponent optComponent -> this.$ownerPage = optComponent.ownerPage();
            case null, default -> throw new SeComponentCreationException("Failed to set page where the component belongs to.");
        }
    }

    final protected <P extends SeAbstractPage> P ownerPage() {
        return (P) $ownerPage;
    }

    private List<WebElement> webElements() {
        if (this.$callerComponent.isEmpty() || this.locator instanceof SeLocatorNodePage) {
            return Selion.driver().findElements(this.locator.build());
        } else {
            return this.$callerComponent.get().existing().findElements(this.locator.build("."));
        }
    }

    private T $componentFromElement(WebElement webElement, Class<T> componentType, Optional<SeAbstractComponent> containingObject) {
        try {
            T component;

            if (containingObject.isEmpty()) {
                component = componentType.getDeclaredConstructor().newInstance();
            } else {
                component = componentType.getDeclaredConstructor(containingObject.get().getClass()).newInstance(containingObject.get());
            }
            component.setWebElement(webElement);
            component.setCallerComponent(this.$callerComponent);
            component.setLocator(this.locator);
            component.setOwnerPage(this.$ownerPage);
            return component;
        } catch (Exception ex) {
            throw new SeComponentCreationException(ex);
        }
    }

    public List<String> texts() {
        return this.stream().map(SeComponent::text).toList();
    }

    public T at(int index) {
        return this.stream().skip(index).findFirst().orElseThrow(() -> new SeEntryNotFoundException(index));

    }

    public T entry(String key) {
        return this.stream().filter(c -> c.key().equals(key)).findFirst().orElseThrow(() -> new SeEntryNotFoundException(key));
    }

    public Stream<T> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(this.iterator(), Spliterator.ORDERED), false);
    }

    public int size() {
        return webElements().size();
    }

    public boolean isEmpty() {
        return webElements().isEmpty();
    }

    public boolean containsKey(String key) {
        return this.stream().anyMatch(c -> c.key().equals(key));
    }

    public List<T> filter(Predicate<T> predicate) {
        return this.stream().filter(predicate).toList();
    }

    @Override
    public Iterator<T> iterator() {
        final List<WebElement> webElements = webElements();

        return webElements.stream().map($e -> this.$componentFromElement($e, componentType, this.containingObject)).iterator();
    }

}
