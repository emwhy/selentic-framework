package org.emwhyware.selion.lib.util;

import org.apache.commons.io.FileUtils;
import org.emwhyware.selion.lib.exception.SnDownloadFileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SnDownloadCsvFileParser extends SnDownloadFileParser {

    public String contentText() {
        try {
            return FileUtils.readFileToString(this.file(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new SnDownloadFileException("Error while reading download file", ex);
        }
    }

    /**
     * Get CSV content of the file.
     * @return
     */
    public List<List<String>> csvContents() {
        return csvContents(true);
    }

    /**
     * Get CSV content of the file.
     * @param trimQuotes
     * @return
     */
    public List<List<String>> csvContents(boolean trimQuotes) {
        List<List<String>> csvContent = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file()))) {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();

                if (trimQuotes) {
                    csvContent.add(Arrays.stream(line.split(",")).map(s -> s.replaceAll("\"", "").trim()).toList());
                } else {
                    csvContent.add(Arrays.stream(line.split(",")).map(String::trim).toList());
                }
            }
        } catch (IOException ex) {
            throw new SnDownloadFileException("Error while reading download file", ex);
        }
        return csvContent;
    }
}
