package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * This interface allows content of {@link ScFrame} actions.
 *
 *
 * @param <T> a frame content object that extends {@link ScFrameContent}
 * @see ScFrame
 * @see ScFrameContent
 */
public interface ScFrameAction<T extends ScFrameContent> {
    void inFrame(@NonNull T frameContent);
}