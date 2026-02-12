package org.selion_framework.lib;

import org.selion_framework.lib.exception.SnPageCreationException;

import java.lang.reflect.InvocationTargetException;

public final class SnWithPage<T extends SnPage> {
    private final T page;

    SnWithPage(Class<T> pageType) {
        try {
            this.page = pageType.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
            throw new SnPageCreationException(ex);
        }
    }

    public void inPage(InPageAction<T> action) {
        this.page.waitForPage();
        action.inPage(this.page);
    }

    public interface InPageAction<T extends SnPage> {
        void inPage(T page);
    }
}
