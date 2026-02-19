package org.emwhyware.selentic.lib;

/**
 * This interface allows content of {@link ScFrame} actions.
 *
 *
 * @param <T> a frame content object that extends {@link ScFrameContent}
 * @see ScFrame
 * @see ScFrameContent
 */
public interface ScFrameAction<T extends ScFrameContent> {
    void inFrame(T frameContent);
}