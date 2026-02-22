package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * This interface allows content of {@link ScDialog} actions.
 *
 *
 * @param <T> a dialog object that extends {@link ScDialog}
 * @see ScDialog
 */
public interface ScDialogAction<T extends ScDialog> {
    void in(@NonNull T dialog);
}