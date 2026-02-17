package org.emwhyware.selentic.lib.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.emwhyware.selentic.lib.exception.ScDownloadFileException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class ScDownloadFile {
    private File file;

    /**
     * Get download file.
     * @param startsWithFileName
     * @return
     */
    public synchronized static ScDownloadFile file(String startsWithFileName) {
        return new ScDownloadFile(getDownloadFile(startsWithFileName, ""));
    }

    /**
     * Get download file.
     * @param startsWithFileName
     * @param extension
     * @return
     */
    public synchronized static ScDownloadFile file(String startsWithFileName, String extension) {
        return new ScDownloadFile(getDownloadFile(startsWithFileName, extension));
    }

    /**
     * Get download file.
     * @param pattern
     * @return
     */
    public synchronized static ScDownloadFile file(Pattern pattern) {
        return new ScDownloadFile(getDownloadFile(pattern, ""));
    }

    /**
     * Get download file.
     * @param pattern
     * @param extension
     * @return
     */
    public synchronized static ScDownloadFile file(Pattern pattern, String extension) {
        return new ScDownloadFile(getDownloadFile(pattern, extension));
    }

    /**
     * Safari does not allow modifying the download directory. So once the file is downloaded, it can be
     * manually copied over to the download directory using this method.
     * @param downloadFile
     * @return
     */
    public synchronized static ScDownloadFile from(File downloadFile) {
        try {
            if (downloadFile.exists()) {
                FileUtils.copyFile(downloadFile, new File(ScLogHandler.downloadDirectory(), downloadFile.getName()));
            }
            throw new ScDownloadFileException("Specified download file does not exist.");
        } catch (IOException ex) {
            throw new ScDownloadFileException("Failed to copy download file.", ex);
        }
    }

    /**
     * constructor.
     */
    protected ScDownloadFile(File file) {
        this.file = file;
    }


    /**
     * Get file.
     * @return
     */
    protected File file() {
        return file;
    }

    /**
     * Cast this download file object to specific download type.
     * @param downloadFileParser
     * @return
     * @param <T>
     */
    public <T extends ScDownloadFileParser> T parse(Class<T> downloadFileParser) {
        try {
            final T downloadFile = downloadFileParser.getDeclaredConstructor().newInstance();

            downloadFile.setFile(this.file);
            return downloadFile;
        } catch (Exception ex) {
            throw new ScDownloadFileException("Error while trying to instantiate download file", ex);
        }
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

    /**
     * Recursively look for download file based on provided file name and extension.
     * @param startWithFileName
     * @param extension
     * @return
     */
    private static File getDownloadFile(String startWithFileName, String extension) {
        final AtomicReference<File> file = new AtomicReference<>();
        final IOFileFilter fileFilter = new IOFileFilter() {
            @Override
            public boolean accept(File file) {
                if (extension.isEmpty()) {
                    return FilenameUtils.getBaseName(file.getName()).startsWith(startWithFileName);
                } else {
                    return FilenameUtils.getBaseName(file.getName()).startsWith(startWithFileName) && FilenameUtils.getExtension(file.getName()).equals(extension);
                }
            }

            @Override
            public boolean accept(File dir, String s) {
                return accept(new File(dir, s));
            }
        };

        ScWait.waitUntil(60000, () -> {
            for (Iterator<File> it = FileUtils.iterateFiles(ScLogHandler.downloadDirectory(), fileFilter, TrueFileFilter.INSTANCE); it.hasNext(); ) {
                file.set(it.next());
                break;
            }
            return file.get() != null;
        });

        if (file.get() != null) {
            return file.get();
        }
        throw new ScDownloadFileException("Unable to find the download file with matching name and extension: " + startWithFileName +  ", extension: " + extension);
    }

    /**
     * Recursively look for download file based on pattern and extension.
     * @param fileNamePattern
     * @param extension
     * @return
     */
    private static File getDownloadFile(Pattern fileNamePattern, String extension) {
        final AtomicReference<File> file = new AtomicReference<>();
        final IOFileFilter fileFilter = new IOFileFilter() {
            @Override
            public boolean accept(File file) {
                if (extension.isEmpty()) {
                    return fileNamePattern.matcher(FilenameUtils.getBaseName(file.getName())).find();
                } else {
                    return fileNamePattern.matcher(FilenameUtils.getBaseName(file.getName())).find() && FilenameUtils.getExtension(file.getName()).equals(extension);
                }
            }

            @Override
            public boolean accept(File dir, String s) {
                return accept(new File(dir, s));
            }
        };
        ScWait.waitUntil(60000, () -> {
            for (Iterator<File> it = FileUtils.iterateFiles(ScLogHandler.downloadDirectory(), fileFilter, TrueFileFilter.INSTANCE); it.hasNext(); ) {
                file.set(it.next());
                break;
            }
            return file.get() != null;
        });

        if (file.get() != null) {
            return file.get();
        }
        throw new ScDownloadFileException("Unable to find the download file with matching name and extension: " + fileNamePattern.pattern() +  ", extension: " + extension);
    }
}
