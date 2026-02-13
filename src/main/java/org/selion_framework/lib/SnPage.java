package org.selion_framework.lib;

public abstract class SnPage extends SnAbstractPage {
    private static final SnXPath PAGE_TITLE = _xpath.descendant("title");

    public static <T extends SnPage> SnWithPage<T> with(Class<T> pageType) {
        return new SnWithPage<>(pageType);
    }

    protected SnPage() {
    }

    public SnGenericComponent pageTitle() {
        return $component(PAGE_TITLE, SnGenericComponent.class);
    }

    public void reload() {
        Selion.driver().navigate().refresh();
        this.waitForPage();
    }

    public void inWindow(SnWindow.WindowActionEmpty predicate) {
        new SnWindow().inWindow(predicate);
    }

    public void inWindow(SnWindow.WindowActionController predicate) {
        new SnWindow().inWindow(predicate);
    }
}
