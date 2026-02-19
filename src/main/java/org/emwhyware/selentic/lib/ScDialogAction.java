package org.emwhyware.selentic.lib;

/**
 * This interface allows content of {@link ScDialog} actions.
 *
 *
 * @param <T> a dialog object that extends {@link ScDialog}
 * @see ScDialog
 */
public interface ScDialogAction<T extends ScDialog> {
    void in(T dialog);
}