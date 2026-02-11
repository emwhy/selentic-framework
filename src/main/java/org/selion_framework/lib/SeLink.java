package org.selion_framework.lib;

import org.apache.commons.io.FilenameUtils;
import org.selion_framework.lib.exception.SeDownloadFileException;
import org.selion_framework.lib.util.SeDownloadFile;

import java.util.Arrays;

public class SeLink extends SeClickableComponent {

    @Override
    protected void rules(SeComponentRule rule) {
        rule.tag().is("a");
    }

    public String href() {
        return this.attr("href").orElse("");
    }

    /**
     * Download file and return the file object.
     * @return
     */
    public SeDownloadFile download() {
        if (href().isBlank()) {
            throw new SeDownloadFileException("No href attribute. Nothing to download.");
        } else {
            String fileName;

            this.click();
            fileName = Arrays.stream(href().split("/")).toList().getLast();

            return SeDownloadFile.file(FilenameUtils.getBaseName(fileName), FilenameUtils.getExtension(fileName));
        }
    }
}
