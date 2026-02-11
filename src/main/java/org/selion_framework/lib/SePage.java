package org.selion_framework.lib;

public abstract class SePage extends SeAbstractPage {
    private static final SeLocatorNode PAGE_TITLE = _xpath.descendant("title");

    public static <T extends SePage> SeWithPage<T> with(Class<T> pageType) {
        return new SeWithPage<>(pageType);
    }

    protected SePage() {
    }

    public SeGenericComponent pageTitle() {
        return $component(PAGE_TITLE, SeGenericComponent.class);
    }

    public void reload() {
        Selion.driver().navigate().refresh();
        this.waitForPage();
    }

    public void inWindow(SeWindow.WindowActionEmpty predicate) {
        new SeWindow().inWindow(predicate);
    }

    public void inWindow(SeWindow.WindowActionController predicate) {
        new SeWindow().inWindow(predicate);
    }
}
