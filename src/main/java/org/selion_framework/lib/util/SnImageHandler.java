package org.selion_framework.lib.util;

import org.apache.commons.io.FileUtils;
import org.selion_framework.lib.exception.SnImageException;
import org.selion_framework.lib.util.recording.SnInteractionEntry;
import org.selion_framework.lib.util.recording.SnInteractionType;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Handles the image content (byte[]) manipulation in separate thread.
 */
public class SnImageHandler {
    private static final Logger LOG = SnLogHandler.logger(SnImageHandler.class);
    private static final String IMAGE_FORMAT = "jpg";
    private static final SnImageHandler INSTANCE = new SnImageHandler();
    private final ThreadPoolExecutor executor;
    private final HashMap<String, byte[]> imageCache = new HashMap<>();
    private final List<String> cancelledImages = new ArrayList<>();

    /**
     * Returns the instance of SnImageResizer.
     * @return
     */
    public static SnImageHandler instance() {
        return INSTANCE;
    }

    /**
     * Return the image file extension.
     * @return
     */
    public static String extension() {
        return "." + IMAGE_FORMAT;
    }

    private SnImageHandler() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }

    /**
     * Start the image resizing process, and store the result to cache. The file is written out when "waitCompletion"
     * is called, and all processes are ended.
     * @param original
     * @param file
     * @return
     */
    public String resize(byte[] original, File file) {
        final String fileName = file.getAbsoluteFile() + extension();
        final ZonedDateTime now = ZonedDateTime.now();

        executor.submit(() -> {
            if (!cancelledImages.contains(fileName)) {
                imageCache.put(fileName, resizeImage(now, file, original));
            }
        });
        return fileName;
    }

    /**
     * Start the image resizing process, and store the result to cache. The file is written out when "waitCompletion"
     * is called, and all processes are ended.
     * @param original
     * @param directory
     * @param logEntry
     * @return
     */
    public String resize(byte[] original, File directory, SnInteractionEntry logEntry) {
        final File file = new  File(directory, logEntry.screenshotFileName);
        final String fileName = file.getAbsoluteFile() + extension();

        executor.submit(() -> {
            if (!cancelledImages.contains(fileName)) {
                imageCache.put(fileName, resizeImage(original, directory, logEntry));
            }
        });
        return fileName;
    }

    /**
     * Cancel writing the image. The image will not be written to the file system when "complete()"
     * is called.
     */
    public synchronized void cancel(File directory, String screenshotFileName) {
        final File file = new File(directory, screenshotFileName);
        final String fileName = file.getAbsoluteFile() + extension();

        imageCache.remove(fileName);

        // Some images may already be in another thread and being resized. Keep track of them, and
        // remove them at "complete()".
        cancelledImages.add(fileName);
    }

    /**
     * Wait for all resizing thread to complete, and write cached images.
     */
    public synchronized void complete() {
        if (!executor.isShutdown() && !executor.isTerminated()) {
            // Initiate shutdown
            executor.shutdown();

            // Wait for all tasks to complete
            try {
                if (!executor.awaitTermination(120, TimeUnit.SECONDS)) { // Wait up to 60 seconds
                    LOG.error("Timeout: Some tasks did not finish.");
                    executor.shutdownNow(); // Snionally force shutdown
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.error("Main thread interrupted while waiting for termination.");
                executor.shutdownNow(); // Force shutdown if interrupted
            }

            // Ensure to remove all cancelled image.
            cancelledImages.forEach(imageCache::remove);

            if (!imageCache.isEmpty()) {
                // Write cached screenshot images.
                for (Map.Entry<String, byte[]> entry : imageCache.entrySet()) {
                    try {
                        File screenshotFile = new File(entry.getKey());

                        LOG.debug("Writing screenshot: file://{}", screenshotFile.getAbsolutePath());
                        FileUtils.writeByteArrayToFile(screenshotFile, entry.getValue());
                    } catch (IOException ex) {
                        LOG.error("Error while writing screenshot: {}", entry.getKey(), ex);
                    }
                }

                LOG.debug("Completed all image resizing: queue count = {}", executor.getQueue().size());
            }
        }
    }

    /**
     * Resize the image.
     * @param original
     * @return
     */
    private byte[] resizeImage(ZonedDateTime dateTime, File file, byte[] original) {
        return this.resizeImage(dateTime, file, original, null, null, "");
    }

    /**
     * Resize the image.
     * @param original
     * @param directory
     * @param logEntry
     * @return
     */
    private byte[] resizeImage(byte[] original, File directory, SnInteractionEntry logEntry) {
        return resizeImage(ZonedDateTime.ofInstant(Instant.ofEpochMilli(logEntry.timestamp), ZoneId.systemDefault()), new File(directory, logEntry.screenshotFileName), original, logEntry.area, logEntry.interactionType, logEntry.text);
    }

    /**
     * Resize the image. Add marking for where the web element appeared.
     * @param original
     * @param rectangle
     * @param interactionType
     * @param text
     * @return
     */
    private byte[] resizeImage(ZonedDateTime dateTime, File file, byte[] original, Rectangle rectangle, SnInteractionType interactionType, String text) {
        try {
            final BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(original));
            final int originalWidth = originalImage.getWidth();
            final int originalHeight = originalImage.getHeight();
            final int targetWidth = 800;
            final int targetHeight = originalHeight * targetWidth / originalWidth;
            final Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            final BufferedImage buffered = new BufferedImage(targetWidth, targetHeight + 20, BufferedImage.TYPE_INT_RGB);
            final Graphics2D g2d = buffered.createGraphics();
            byte[] resized;

            if (interactionType == SnInteractionType.Error) {
                g2d.setPaint(Color.RED);
                g2d.fillRect(0, targetHeight, targetWidth, targetHeight + 20);
            }

            g2d.drawImage(resizedImage, 0, 0, null);

            // If rectangle is provided, draw it on the image.
            if (rectangle != null) {
                final int scaledX = rectangle.x * targetWidth / originalWidth;
                final int scaledY = rectangle.y * targetWidth / originalWidth;
                final int scaledWidth = rectangle.width * targetWidth / originalWidth;
                final int scaledHeight = rectangle.height * targetWidth / originalWidth;

                g2d.setPaint(Color.RED);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRect(scaledX - 5, scaledY - 5, scaledWidth + 10, scaledHeight + 10);

                if (interactionType == SnInteractionType.Click) {
                    drawClickIcon(g2d, scaledX,  scaledY);
                } else if (interactionType == SnInteractionType.Select || interactionType == SnInteractionType.TextEntry) {
                    drawActionText(g2d, interactionType, text, scaledX, scaledY + scaledHeight);
                }
            }
            drawLabel(g2d, dateTime, file.getName(), targetHeight + 20);
            g2d.dispose();

            try (final ByteArrayOutputStream resizedOutputStream = new ByteArrayOutputStream()) {
                ImageIO.write(buffered, IMAGE_FORMAT, resizedOutputStream);
                resizedOutputStream.flush();
                resized = resizedOutputStream.toByteArray();
            }
            LOG.debug("Resized screenshot: {}, original = {}KB, resized = {}KB ({})", file.getName(), original.length / 1024, resized.length / 1024, IMAGE_FORMAT);
            return resized;
        } catch (IOException ex) {
            throw new SnImageException("Error while resizing image.", ex);
        }
    }

    /**
     * Draw click icon.
     * @param g2d
     * @param x
     * @param y
     */
    private void drawClickIcon(Graphics2D g2d, int x, int y) {
        GeneralPath clickPath = new GeneralPath();

        clickPath.moveTo(x - 1, y - 22);
        clickPath.lineTo(x - 1, y - 12);

        clickPath.moveTo(x + 13.14, y - 17.85);
        clickPath.lineTo(x + 7.48, y - 11.51);

        clickPath.moveTo(x -16.85, y + 12.14);
        clickPath.lineTo(x -10.51, y + 6.48);

        clickPath.moveTo(x -21, y - 2);
        clickPath.lineTo(x -13, y - 2);

        clickPath.moveTo(x -16.85, y - 17.85);
        clickPath.lineTo(x -10.51, y - 11.51);

        g2d.setPaint(Color.RED);
        g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, null, 0.0f));
        g2d.draw(clickPath);
    }

    /**
     * Draw label on image.
     * @param g2d
     * @param dateTime
     * @param file
     * @param y
     */
    private void drawLabel(Graphics2D g2d, ZonedDateTime dateTime, String fileName, int y) {
        Font font = new Font("Arial", Font.PLAIN, 12);

        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString(dateTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS z")), 5, y - 5);
        if (fileName.length() > 90) {
            g2d.drawString("..." + fileName.substring(fileName.length() - 90), 230, y - 5);
        } else {
            g2d.drawString(fileName, 230, y - 5);
        }
    }

    /**
     * Draw text onto image for select and send-keys actions.
     * @param g2d
     * @param interactionType
     * @param text
     * @param x
     * @param y
     */
    private void drawActionText(Graphics2D g2d, SnInteractionType interactionType, String text, int x, int y) {
        final Font font = new Font("Arial", Font.PLAIN, 12);
        String drawText = "";

        if (interactionType == SnInteractionType.Select) {
            drawText = "Select => " + text;
        } else if (interactionType == SnInteractionType.TextEntry) {
            drawText = "Enter => " + text;
        }

        g2d.setFont(font);
        g2d.setColor(Color.RED);

        g2d.drawString(drawText, x, y + 20);
    }
}

