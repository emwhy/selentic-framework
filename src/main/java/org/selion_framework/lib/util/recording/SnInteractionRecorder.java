//package org.selion_framework.lib.util.recording;
//
//import com.codeborne.selenide.Selenide;
//import com.codeborne.selenide.WebDriverRunner;
//import com.google.common.html.HtmlEscapers;
//import com.google.gson.Gson;
//import com.optavise.automation.framework.config.OptRuntimeConfig;
//import com.optavise.automation.framework.exception.OptException;
//import com.optavise.automation.framework.exception.OptGroupAssertionError;
//import com.optavise.automation.framework.util.OptFileUtils;
//import com.optavise.automation.framework.util.OptImageResizer;
//import com.optavise.automation.framework.util.OptLogger;
//import com.optavise.automation.framework.util.code.OptJavaCodeParser;
//import com.optavise.automation.framework.util.html.OptHtmlBuilder;
//import com.optavise.automation.framework.util.html.OptHtmlElement;
//import com.optavise.automation.framework.util.html.OptHtmlPrettyLevel;
//import com.optavise.automation.framework.util.html.OptSvgIcons;
//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.WebElement;
//import org.selion_framework.lib.util.SnImageHandler;
//import org.selion_framework.lib.util.SnLogHandler;
//import org.slf4j.Logger;
//import org.testng.IInvokedMethod;
//import org.testng.ITestNGMethod;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class SnInteractionRecorder {
//    private static final Logger LOG = SnLogHandler.logger(SnInteractionRecorder.class);
//
//    private static final List<SnInteractionEntry> INTERACTION_ENTRIES = new ArrayList<>();
//    private static final SnInteractionRecorder INSTANCE = new SnInteractionRecorder();
//
//    private final List<String> testMethodNames = new ArrayList<>();
//    private final List<String> testClassNames = new ArrayList<>();
//    private final Optional<File> recordingDirectory;
//
//    public static SnInteractionRecorder getInstance() {
//        return INSTANCE;
//    }
//
//    private SnInteractionRecorder() {
//        if (new OptRuntimeConfig().getRecordingEnabled()) {
//            try {
//                final File recordingDirectory = new File(OptFileUtils.getLogDirectory(), "recording");
//
//                if (!recordingDirectory.exists()) {
//                    FileUtils.forceMkdir(recordingDirectory);
//                }
//                this.recordingDirectory = Optional.of(recordingDirectory);
//            } catch (IOException ex) {
//                throw new OptException("Failed to create recording directory", ex);
//            }
//        } else {
//            recordingDirectory = Optional.empty();
//        }
//    }
//
//    public boolean isEnabled() {
//        return recordingDirectory.isPresent();
//    }
//
//    /**
//     * Add list of test methods that are to be recorded.
//     * @param testMethods
//     */
//    public void addTestMethods(List<ITestNGMethod> allTestMethods) {
//        List<String> testMethodNames = allTestMethods.stream().map(ITestNGMethod::getMethodName).toList();
//        List<String> testClassNames = allTestMethods.stream().map(m -> m.getTestClass().getName()).toList();
//
//        this.testMethodNames.addAll(testMethodNames);
//        this.testClassNames.addAll(testClassNames);
//    }
//
//    /**
//     * Remove test from the recording.
//     * @param testClassName
//     * @param testMethodName
//     */
//    public void removeTestMethod(String testClassName, String testMethodName) {
//        if (isEnabled()) {
//            INTERACTION_ENTRIES.stream().filter(e -> e.testClassName.equals(testClassName) && e.testMethodName.equals(testMethodName)).forEach(e -> {
//                OptImageResizer.getInstance().cancel(recordingDirectory.get(), e.screenshotFileName);
//            });
//            INTERACTION_ENTRIES.removeIf(e -> e.testClassName.equals(testClassName) && e.testMethodName.equals(testMethodName));
//        }
//    }
//
//    /**
//     * Return recording file name based on test class and test method names.
//     * @param testClassName
//     * @param testMethodName
//     * @return
//     */
//    public Optional<String> recordingFileName(String testClassName, String testMethodName) {
//        if (isEnabled()) {
//            return INTERACTION_ENTRIES.stream().filter(e -> e.testClassName.equals(testClassName) && e.testMethodName.equals(testMethodName)).map(SnInteractionEntry::recordingFileName).findFirst();
//        } else {
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Returns the interaction entries.
//     * @return
//     */
//    public List<SnInteractionEntry> interactionEntries() {
//        return INTERACTION_ENTRIES;
//    }
//
//    /**
//     * Write recording data to JSON file.
//     */
//    public void writeRecordingJsonFile() {
//        if (isEnabled() && recordingDirectory.get().exists() && !INTERACTION_ENTRIES.isEmpty()) {
//            Gson gson = new Gson()
//                    .newBuilder()
//                    .disableHtmlEscaping()
//                    .setPrettyPrinting()
//                    .create();
//            try {
//                File jsonFile = new File(recordingDirectory.get(), "recording." + System.currentTimeMillis() + ".data.json");
//
//                FileUtils.write(jsonFile, gson.toJson(INTERACTION_ENTRIES), StandardCharsets.UTF_8);
//                optLogger.logger().info("Generated recording data JSON file: file://{}", jsonFile.getAbsoluteFile());
//            } catch (IOException ex) {
//                throw new OptException("Unable to write recording data file", ex);
//            }
//        }
//    }
//
//    /**
//     * Write recording HTML file.
//     */
//    public void writeRecordingHtmlFiles() {
//        try {
//            if (recordingDirectory.isPresent() && recordingDirectory.get().exists() && !INTERACTION_ENTRIES.isEmpty()) {
//                final List<String> recordedTestMethods = INTERACTION_ENTRIES.stream().map(SnInteractionEntry::toString).distinct().toList();
//
//                for (String recordedTestMethod : recordedTestMethods) {
//                    final List<SnInteractionEntry> logEntries = new ArrayList<>(INTERACTION_ENTRIES.stream().filter(e -> recordedTestMethod.equals(e.toString())).toList());
//                    final OptHtmlBuilder htmlBuilder = OptHtmlBuilder.createHTMLBuilder(OptHtmlPrettyLevel.NoFormat);
//                    final File htmlFile = new File(recordingDirectory.get(), logEntries.getFirst().recordingFileName());
//
//                    logEntries.sort(null);
//
//                    htmlBuilder.addCSSFile("recording/recording.css");
//                    htmlBuilder.addJavaScriptFile("recording/recording.js");
//                    htmlBuilder.head(head -> {
//                        head.add("title", title -> title.text("Recording: " + recordedTestMethod));
//                    });
//                    htmlBuilder.body(body -> {
//                        final AtomicInteger imgIndex = new AtomicInteger(0);
//                        final List<Integer> screenshotLineNumbers = logEntries.stream().filter(e -> e.interactionType != SnInteractionType.Error).map(e -> e.lineNumber).distinct().toList();
//                        final Optional<SnInteractionEntry> errorEntry = logEntries.stream().filter(e -> e.interactionType == SnInteractionType.Error).findFirst();
//
//                        for (SnInteractionEntry logEntry : logEntries) {
//                            // Image display.
//                            if (imgIndex.get() == 0) {
//                                final OptHtmlElement sourceCodeHtmlElement = sourceCodeHtmlElement(logEntry.testClassName, logEntry.testMethodName, screenshotLineNumbers, errorEntry);
//
//                                body.add("div", containerDiv -> {
//                                    containerDiv.id("container");
//                                    containerDiv.add("div", screenshotAreaDiv -> {
//                                        screenshotAreaDiv.id("screenshot-area");
//                                        screenshotAreaDiv.add("div", screenshotDisplayDiv -> {
//                                            screenshotDisplayDiv.id("screenshot-display");
//                                            screenshotDisplayDiv.add("img", img -> {
//                                                img.attr("src", "");
//                                                img.attr("timestamp", "");
//                                                img.attr("line", "");
//                                                img.attr("image-index", "");
//                                                img.id("screenshot-display-image");
//                                            });
//                                        });
//                                        screenshotAreaDiv.add("div", noScreenshotDiv -> {
//                                            noScreenshotDiv.id("no-screenshot");
//                                            noScreenshotDiv.text("No Screenshot");
//                                        });
//                                        screenshotAreaDiv.add("div", div -> {
//                                            div.id("screenshot-prev");
//                                            div.cssClasses("screenshot-nav");
//                                            div.attr("onmouseover", "mouseOver(this)");
//                                            div.attr("onmouseout", "mouseOut(this)");
//                                            div.attr("onclick", "shiftImage(-1)");
//                                            div.text("&laquo;");
//                                        });
//                                        screenshotAreaDiv.add("div", div -> {
//                                            div.id("screenshot-next");
//                                            div.cssClasses("screenshot-nav");
//                                            div.attr("onmouseover", "mouseOver(this)");
//                                            div.attr("onmouseout", "mouseOut(this)");
//                                            div.attr("onclick", "shiftImage(1)");
//                                            div.text("&raquo;");
//                                        });
//                                    });
//                                    containerDiv.add(sourceCodeHtmlElement);
//                                });
//
//                            }
//                            // Preload images.
//                            body.add("img", img -> {
//                                img.attr("src", logEntry.screenshotFileName + OptImageResizer.extension());
//                                img.attr("test-class", logEntry.testClassName);
//                                img.attr("test-method", logEntry.testMethodName);
//                                img.attr("test-thread-id", String.valueOf(logEntry.threadId));
//                                img.attr("timestamp",  String.valueOf(logEntry.timestamp));
//                                img.attr("image-index", String.valueOf(imgIndex.get()));
//                                img.attr("width", "1");
//                                img.attr("height", "1");
//                                if (logEntry.interactionType == SnInteractionType.Start) {
//                                    img.attr("line-number", "start");
//                                    img.cssClasses("start");
//                                } else if (logEntry.interactionType == SnInteractionType.End) {
//                                    img.attr("line-number", "end");
//                                    img.cssClasses("end");
//                                } else {
//                                    img.attr("line-number", String.valueOf(logEntry.lineNumber));
//                                }
//                                img.cssClasses("preloaded-screenshot");
//
//                                if (logEntry.interactionType == SnInteractionType.Error) {
//                                    img.cssClasses("error-screenshot");
//                                    if (logEntry.groupErrors() != null && !logEntry.groupErrors().isEmpty()) {
//                                        img.attr("group-error-line-numbers", String.join(",", logEntry.groupErrors().stream().map(e -> String.valueOf(e.lineNumber)).toList()));
//                                    }
//                                }
//                            });
//
//                            // Write error content in separate div tag.
//                            if (logEntry.interactionType == SnInteractionType.Error) {
//                                body.add("div", errorDiv -> {
//                                    errorDiv.id("test-error");
//                                    errorDiv.add("div", errorContentDiv -> {
//                                        errorContentDiv.cssClasses("error-content");
//                                        errorContentDiv.attr("image-index", String.valueOf(imgIndex.get()));
//                                        errorContentDiv.attr("line-number", String.valueOf(logEntry.lineNumber));
//                                        errorContentDiv.add("div", div -> {
//                                            div.cssClasses("error-icon");
//                                            div.add(OptSvgIcons.errorIcon(p -> p.id("error-" + logEntry.lineNumber)));
//                                        });
//                                        errorContentDiv.add("pre", pre -> {
//                                            pre.cssClasses("error-text");
//                                            pre.text(HtmlEscapers.htmlEscaper().escape(logEntry.text));
//                                        });
//                                        errorContentDiv.add("div", div -> {
//                                            div.cssClasses("error-line-number");
//                                            div.text("Line " + logEntry.lineNumber);
//                                        });
//                                    });
//
//                                    // Group assertion error can contain multiple point of errors. Write them as the attribute
//                                    // to the preloaded image.
//                                    if (logEntry.groupErrors() != null && !logEntry.groupErrors().isEmpty()) {
//                                        for (SnInteractionEntry.OptGroupErrorEntry groupErrorEntry : logEntry.groupErrors()) {
//                                            errorDiv.add("div", groupErrorDiv -> {
//                                                groupErrorDiv.cssClasses("group-error", "error-content");
//                                                groupErrorDiv.attr("line-number", String.valueOf(groupErrorEntry.lineNumber));
//                                                groupErrorDiv.attr("image-index", String.valueOf(imgIndex.get()));
//                                                groupErrorDiv.add("div", div -> {
//                                                    div.cssClasses("error-icon");
//                                                    div.add(OptSvgIcons.errorIcon(p -> p.id("error-" + groupErrorEntry.lineNumber)));
//                                                });
//                                                groupErrorDiv.add("pre", pre -> {
//                                                    pre.cssClasses("error-text");
//                                                    pre.text(HtmlEscapers.htmlEscaper().escape(groupErrorEntry.text));
//                                                });
//                                                groupErrorDiv.add("div", div -> {
//                                                    div.cssClasses("error-line-number");
//                                                    div.text("Line " + groupErrorEntry.lineNumber);
//                                                });
//                                            });
//                                        }
//                                    }
//                                });
//                            }
//
//                            imgIndex.addAndGet(1);
//                        }
//                    });
//
//                    FileUtils.write(htmlFile, htmlBuilder.build(), StandardCharsets.UTF_8);
//                    optLogger.logger().info("Generated recording HTML file: file://{}", htmlFile.getAbsoluteFile());
//                }
//            }
//        } catch (IOException ex) {
//            throw new OptException("Unable to write recording HTML files", ex);
//        }
//    }
//
//    /**
//     * Record start of a test method.
//     * @param invokedMethod
//     */
//    public void recordStart(IInvokedMethod invokedMethod) {
//        writeInteraction(SnInteractionType.Start, null, invokedMethod.getTestMethod().getTestClass().getName(), invokedMethod.getTestMethod().getMethodName(), 0, "");
//    }
//
//    /**
//     * Record end of a test method.
//     * @param invokedMethod
//     */
//    public void recordEnd(IInvokedMethod invokedMethod) {
//        writeInteraction(SnInteractionType.End, null, invokedMethod.getTestMethod().getTestClass().getName(), invokedMethod.getTestMethod().getMethodName(), 0, "");
//    }
//
//    /**
//     * Record error from a test method.
//     * @param invokedMethod
//     */
//    public void recordError(IInvokedMethod invokedMethod) {
//        writeErrorInteraction(invokedMethod.getTestMethod().getTestClass().getName(), invokedMethod.getTestMethod().getMethodName(), invokedMethod.getTestResult().getThrowable());
//    }
//
//    /**
//     * Write log entry for action. (click, text entry, etc.) It attempts to detect the line of code in the test class
//     * where the action is happening, including class name, method name, and line number.
//     * @param interactionType
//     */
//    public void record(SnInteractionType interactionType) {
//        record(interactionType, null);
//    }
//
//    /**
//     * Record action. (click, text entry, etc.) It attempts to detect the line of code in the test class
//     * where the action is happening, including class name, method name, and line number.
//     * @param interactionType
//     * @param element
//     */
//    public void record(SnInteractionType interactionType, WebElement element) {
//        record(interactionType, element, "");
//    }
//
//    /**
//     * Record action. (click, text entry, etc.) It attempts to detect the line of code in the test class
//     * where the action is happening, including class name, method name, and line number.
//     * @param interactionType
//     * @param element
//     * @param text
//     */
//    public void record(SnInteractionType interactionType, WebElement element, String text) {
//        StackWalker.getInstance().walk(stream -> {
//            final List<StackWalker.StackFrame> stackFrames = stream.filter(frame -> this.testClassNames.contains(frame.getClassName())).toList();
//            final Optional<StackWalker.StackFrame> methodFrame = stackFrames.stream().filter(f -> testMethodNames.contains(f.getMethodName())).findFirst();
//
//            if (!stackFrames.isEmpty() && methodFrame.isPresent()) {
//                final String fullClassName = stackFrames.getFirst().getClassName();
//                final String methodName = methodFrame.get().getMethodName();
//                final int line = stackFrames.getFirst().getLineNumber();
//
//                writeInteraction(interactionType, element, fullClassName, methodName, line, text);
//            }
//            return true;
//        });
//    }
//
//    /**
//     * Write to an interaction entry, and take screenshot if possible.
//     * @param interactionType
//     * @param element
//     * @param fullClassName
//     * @param methodName
//     * @param line
//     */
//    private void writeInteraction(SnInteractionType interactionType, WebElement element, String fullClassName, String methodName, int lineNumber, String text) {
//        if (lineNumber > 0) {
//            optLogger.logger().debug("{} => {}[line={}, method={}, thread={}]", interactionType, fullClassName, lineNumber, methodName, Thread.currentThread().threadId());
//        } else {
//            optLogger.logger().debug("{} => {}[line={}, method={}, thread={}]", interactionType, fullClassName, interactionType, methodName, Thread.currentThread().threadId());
//        }
//
//        try {
//            if (recordingDirectory.isPresent()) {
//                if (element == null) {
//                    final SnInteractionEntry interactionEntry = switch (interactionType) {
//                        case Start -> SnInteractionEntry.createStartLogEntry(fullClassName, methodName);
//                        case End -> SnInteractionEntry.createEndLogEntry(fullClassName, methodName);
//                        case NavigateTo -> SnInteractionEntry.createNavigateToLogEntry(fullClassName, methodName, lineNumber);
//                        default -> null;
//                    };
//
//                    INTERACTION_ENTRIES.add(interactionEntry);
//                    screenshot(recordingDirectory.get(), interactionEntry);
//                } else {
//                    final int boundingX = ((Number) Objects.requireNonNull(Selenide.executeJavaScript("return arguments[0].getBoundingClientRect().left;", element))).intValue();
//                    final int boundingY = ((Number) Objects.requireNonNull(Selenide.executeJavaScript("return arguments[0].getBoundingClientRect().top;", element))).intValue();
//                    final org.openqa.selenium.Dimension d = element.getSize();
//                    final java.awt.Rectangle rect = new java.awt.Rectangle(boundingX, boundingY, d.width, d.height);
//                    final SnInteractionEntry interactionEntry = switch (interactionType) {
//                        case TextEntry -> SnInteractionEntry.createTextLogEntry(fullClassName, methodName, lineNumber, rect, text);
//                        case Select -> SnInteractionEntry.createSelectLogEntry(fullClassName, methodName, lineNumber, rect, text);
//                        case Click -> SnInteractionEntry.createClickLogEntry(fullClassName, methodName, lineNumber, rect);
//                        default -> null;
//                    };
//
//                    INTERACTION_ENTRIES.add(interactionEntry);
//                    screenshot(recordingDirectory.get(), interactionEntry);
//                }
//            }
//        } catch (Throwable throwable) {}
//    }
//
//    /**
//     * Write error interaction.
//     * @param fullClassName
//     * @param methodName
//     * @param th
//     */
//    private void writeErrorInteraction(String fullClassName, String methodName, Throwable th) {
//        final List<StackTraceElement> stackTraceElements = Arrays.stream(th.getStackTrace()).filter(s -> this.testClassNames.contains(s.getClassName()) && this.testMethodNames.contains(s.getMethodName())).toList();
//        final int lineNumber = stackTraceElements.isEmpty() ? 0 : stackTraceElements.getFirst().getLineNumber();
//
//        if (lineNumber > 0) {
//            optLogger.logger().debug("{} => {}[line={}, method={}, thread={}]", SnInteractionType.Error, fullClassName, lineNumber, methodName, Thread.currentThread().threadId());
//        } else {
//            optLogger.logger().debug("{} => {}[line={}, method={}, thread={}]", SnInteractionType.Error, fullClassName, "?", methodName, Thread.currentThread().threadId());
//        }
//
//        try {
//            if (recordingDirectory.isPresent()) {
//                final SnInteractionEntry interactionEntry;
//
//                if (th instanceof OptGroupAssertionError) {
//                    interactionEntry = SnInteractionEntry.createErrorLogEntry(fullClassName, methodName, lineNumber, th.getMessage(), groupErrorEntries(fullClassName, methodName, (OptGroupAssertionError) th));
//                } else {
//                    interactionEntry = SnInteractionEntry.createErrorLogEntry(fullClassName, methodName, lineNumber, th.getMessage() == null ? th.toString() : th.getMessage());
//                }
//                INTERACTION_ENTRIES.add(interactionEntry);
//                screenshot(recordingDirectory.get(), interactionEntry);
//            }
//        } catch (Throwable throwable) {}
//    }
//
//    /**
//     * Convert the assertion group errors in to a list of log entry group error entries.
//     * @param fullClassName
//     * @param methodName
//     * @param groupAssertionError
//     * @return
//     */
//    private List<SnInteractionEntry.OptGroupErrorEntry> groupErrorEntries(String fullClassName, String methodName, OptGroupAssertionError groupAssertionError) {
//        final List<SnInteractionEntry.OptGroupErrorEntry> errorEntries = new ArrayList<>();
//
//        for (Throwable th : groupAssertionError.errors) {
//            final List<StackTraceElement> stackTraceElements = Arrays.stream(th.getStackTrace()).filter(s -> s.getClassName().equals(fullClassName) && s.getMethodName().contains(methodName)).toList();
//            final int lineNumber = stackTraceElements.isEmpty() ? 0 : stackTraceElements.getFirst().getLineNumber();
//
//            errorEntries.add(new SnInteractionEntry.OptGroupErrorEntry(lineNumber, th.getMessage()));
//        }
//        return Collections.unmodifiableList(errorEntries);
//    }
//
//    /**
//     * Returns the HTML element that contain the test method source code.
//     * @param testClassName
//     * @param testMethodName
//     * @param screenshotLineNumbers
//     * @param errorEntry
//     * @return
//     */
//    private OptHtmlElement sourceCodeHtmlElement(String testClassName, String testMethodName, List<Integer> screenshotLineNumbers, Optional<SnInteractionEntry> errorEntry) {
//        final File rootDirectory = OptFileUtils.getRootDirectory();
//        final File[] directories = rootDirectory.listFiles(file -> file.isDirectory() && file.getName().endsWith("-automation"));
//
//        if (directories == null) {
//            return OptHtmlElement.createHTMLElement("div");
//        }
//
//        for (File directory : directories) {
//            final File sourceFile = new File(directory, "/src/main/java/" + testClassName.replaceAll("\\.", "/") + ".java");
//
//            if (sourceFile.exists()) {
//                return new OptJavaCodeParser(sourceFile, testMethodName, screenshotLineNumbers, errorEntry).codeHtmlElement();
//            }
//        }
//        return OptHtmlElement.createHTMLElement("div");
//    }
//
//    /**
//     * Take screenshot using information from log entry.
//     * @param directory
//     * @param logEntry
//     */
//    private void screenshot(File directory, SnInteractionEntry logEntry) {
//        if (WebDriverRunner.getWebDriver().toString() != null) {
//            try {
//                OptImageResizer.getInstance().resize(Objects.requireNonNull(Selenide.screenshot(OutputType.BYTES)), directory, logEntry);
//            } catch (Throwable ex) {
//                throw new OptException("WebDriver is not available. Failed to take screenshot.", ex);
//            }
//        } else {
//            throw new OptException("WebDriver is not available. Failed to take screenshot.");
//        }
//    }
//}
