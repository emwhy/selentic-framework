package org.emwhyware.selion.lib;

public interface SnFrameAction<T extends SnFrameContent> {
    void inFrame(T frameContent);
}