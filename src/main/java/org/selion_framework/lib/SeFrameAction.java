package org.selion_framework.lib;

public interface SeFrameAction<T extends SeFrameContent> {
    void inFrame(T frameContent);
}