package org.emwhyware.selentic.lib;

public interface SnFrameAction<T extends ScFrameContent> {
    void inFrame(T frameContent);
}