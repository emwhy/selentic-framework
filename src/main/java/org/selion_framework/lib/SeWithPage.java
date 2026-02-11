package org.selion_framework.lib;

import org.selion_framework.lib.exception.SePageCreationException;

import java.lang.reflect.InvocationTargetException;

public final class SeWithPage<T extends SePage> {
    private final T page;

    SeWithPage(Class<T> pageType) {
        try {
            this.page = pageType.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
            throw new SePageCreationException(ex);
        }
    }

    public void inPage(InPageAction<T> action) {
        this.page.waitForPage();
        action.inPage(this.page);
    }

    public interface InPageAction<T extends SePage> {
        void inPage(T page);
    }
}
