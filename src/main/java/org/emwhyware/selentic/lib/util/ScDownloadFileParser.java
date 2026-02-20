package org.emwhyware.selentic.lib.util;

import org.apache.commons.io.FilenameUtils;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.Objects;

public abstract class ScDownloadFileParser {
    private @MonotonicNonNull File file;

    ScDownloadFileParser() {}

    void setFile(@NonNull File file) {
        this.file = file;
    }

    @SuppressWarnings("nullness")
    protected File file() {
        return Objects.requireNonNull(file);
    }


    /**
     * Get base file name.
     * @return base file name
     */
    public String baseName() {
        return FilenameUtils.getBaseName(file().getName());
    }

    /**
     * Get file extension.
     * @return extension
     */
    public String extension() {
        return FilenameUtils.getExtension(file().getName());
    }

    /**
     * Get full file name.
     * @return full file name
     */
    public String name() {
        return file().getName();
    }
}
