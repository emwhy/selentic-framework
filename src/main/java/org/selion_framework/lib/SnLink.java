package org.selion_framework.lib;

import org.apache.commons.io.FilenameUtils;
import org.selion_framework.lib.exception.SnDownloadFileException;
import org.selion_framework.lib.util.SnDownloadFile;

import java.util.Arrays;

public class SnLink extends SnClickableComponent {

    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("a");
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String href() {
        return this.attr("href").orElse("");
    }

    /**
     * Download file and return the file object.
     * @return
     */
    public SnDownloadFile download() {
        if (href().isBlank()) {
            throw new SnDownloadFileException("No href attribute. Nothing to download.");
        } else {
            String fileName;

            this.click();
            fileName = Arrays.stream(href().split("/")).toList().getLast();

            return SnDownloadFile.file(FilenameUtils.getBaseName(fileName), FilenameUtils.getExtension(fileName));
        }
    }
}
