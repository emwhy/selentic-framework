package org.emwhyware.selentic.lib.util;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

public abstract class ScDownloadFileParser {
    private File file;

    ScDownloadFileParser() {}

    void setFile(File file) {
        this.file = file;
    }

    protected File file() {
        return file;
    }


    /**
     * Get base file name.
     * @return
     */
    public String baseName() {
        return FilenameUtils.getBaseName(file.getName());
    }

    /**
     * Get file extension.
     * @return
     */
    public String extension() {
        return FilenameUtils.getExtension(file.getName());
    }

    /**
     * Get full file name.
     * @return
     */
    public String name() {
        return file.getName();
    }
}
