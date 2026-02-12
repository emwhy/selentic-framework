package org.selion_framework.lib;

import org.openqa.selenium.WebElement;
import org.selion_framework.lib.exception.SnComponentCreationException;
import org.selion_framework.lib.exception.SnEntryNotFoundException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SnComponentCollection<T extends SnComponent> implements Iterable<T> {
    private SnXPathSelector locator;
    private Class<T> componentType;
    private Optional<SnComponent> $callerComponent = Optional.empty();
    private Optional<SnAbstractComponent> containingObject = Optional.empty();
    private SnAbstractPage $ownerPage;

    protected SnComponentCollection() {}


    final void setLocator(SnXPathSelector locator) {
        this.locator = locator;
    }

    final void setComponentType(Class<T> componentType) {
        this.componentType = componentType;
    }

    final void setContainingObject(Optional<SnAbstractComponent> containingObject) {
        this.containingObject = containingObject;
    }

    final void setOwnerPage(SnAbstractComponent abstractComponent) {
        switch (abstractComponent) {
            case SnAbstractPage optPage -> this.$ownerPage = optPage;
            case SnComponent optComponent -> this.$ownerPage = optComponent.ownerPage();
            case null, default -> throw new SnComponentCreationException("Failed to set page where the component belongs to.");
        }
    }

    final protected <P extends SnAbstractPage> P ownerPage() {
        return (P) $ownerPage;
    }

    private List<WebElement> webElements() {
        if (this.$callerComponent.isEmpty() || this.locator instanceof SnXPathSelectorPage) {
            return Selion.driver().findElements(this.locator.build());
        } else {
            return this.$callerComponent.get().existing().findElements(this.locator.build("."));
        }
    }

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
            component.setLocator(this.locator);
            component.setOwnerPage(this.$ownerPage);
            return component;
        } catch (Exception ex) {
            throw new SnComponentCreationException(ex);
        }
    }

    public List<String> texts() {
        return this.stream().map(SnComponent::text).toList();
    }

    public T at(int index) {
        return this.stream().skip(index).findFirst().orElseThrow(() -> new SnEntryNotFoundException(index));

    }

    public T entry(String key) {
        return this.stream().filter(c -> c.key().equals(key)).findFirst().orElseThrow(() -> new SnEntryNotFoundException(key));
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
