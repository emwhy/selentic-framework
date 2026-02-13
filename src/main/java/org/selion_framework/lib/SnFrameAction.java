package org.selion_framework.lib;

public interface SnFrameAction<T extends SnFrameContent> {
    void inFrame(T frameContent);
}