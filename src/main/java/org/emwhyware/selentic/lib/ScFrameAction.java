package org.emwhyware.selentic.lib;

public interface ScFrameAction<T extends ScFrameContent> {
    void inFrame(T frameContent);
}