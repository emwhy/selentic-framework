package org.emwhyware.selentic.lib;

import org.apache.commons.io.FilenameUtils;
import org.emwhyware.selentic.lib.exception.ScDownloadFileException;
import org.emwhyware.selentic.lib.util.ScDownloadFile;

import java.util.Arrays;

public class ScLink extends ScClickableComponent {

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
    public ScDownloadFile download() {
        if (href().isBlank()) {
            throw new ScDownloadFileException("No href attribute. Nothing to download.");
        } else {
            String fileName;

            this.click();
            fileName = Arrays.stream(href().split("/")).toList().getLast();

            return ScDownloadFile.file(FilenameUtils.getBaseName(fileName), FilenameUtils.getExtension(fileName));
        }
    }
}
