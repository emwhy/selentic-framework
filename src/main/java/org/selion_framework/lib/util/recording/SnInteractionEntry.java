package org.selion_framework.lib.util.recording;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class SnInteractionEntry implements Comparable<SnInteractionEntry> {
    public final long timestamp;
    public final SnInteractionType interactionType;
    public final String text;
    public final Rectangle area;
    public final String testClassName;
    public final String testMethodName;
    public final int lineNumber;
    public final long threadId;
    public final String screenshotFileName;
    private List<OptGroupErrorEntry> groupErrors = null;

    private SnInteractionEntry(SnInteractionType interactionType, String testClassName, String testMethodName, int lineNumber, String text, Rectangle area) {
        this.timestamp = System.currentTimeMillis();
        this.interactionType = interactionType;
        this.text = text;
        this.area = area;
        this.testClassName = testClassName;
        this.testMethodName = testMethodName;
        this.lineNumber = lineNumber;
        this.threadId = Thread.currentThread().threadId();
        this.screenshotFileName = timestamp + "-" + testClassName + "." + testMethodName + "-" + (lineNumber > 0 ? lineNumber : interactionType.name());
    }

    public static SnInteractionEntry createClickLogEntry(String testClassName, String testMethodName, int line, Rectangle area) {
        return new SnInteractionEntry(SnInteractionType.Click, testClassName, testMethodName, line, "", area);
    }

    public static SnInteractionEntry createSelectLogEntry(String testClassName, String testMethodName, int line, Rectangle area, String text) {
        return new SnInteractionEntry(SnInteractionType.Select, testClassName, testMethodName, line, text, area);
    }

    public static SnInteractionEntry createTextLogEntry(String testClassName, String testMethodName, int line, Rectangle area, String text) {
        return new SnInteractionEntry(SnInteractionType.TextEntry, testClassName, testMethodName, line, text, area);
    }

    public static SnInteractionEntry createNavigateToLogEntry(String testClassName, String testMethodName, int line) {
        return new SnInteractionEntry(SnInteractionType.NavigateTo, testClassName, testMethodName, line, "", null);
    }

    public static SnInteractionEntry createStartLogEntry(String testClassName, String testMethodName) {
        return new SnInteractionEntry(SnInteractionType.Start, testClassName, testMethodName, 0, "", null);
    }

    public static SnInteractionEntry createEndLogEntry(String testClassName, String testMethodName) {
        return new SnInteractionEntry(SnInteractionType.End, testClassName, testMethodName, 0, "", null);
    }

    public static SnInteractionEntry createErrorLogEntry(String testClassName, String testMethodName, int lineNumber, String errorText) {
        return new SnInteractionEntry(SnInteractionType.Error, testClassName, testMethodName, lineNumber, errorText, null);
    }

    public static SnInteractionEntry createErrorLogEntry(String testClassName, String testMethodName, int lineNumber, String errorText, List<OptGroupErrorEntry> groupErrors) {
        SnInteractionEntry entry = new SnInteractionEntry(SnInteractionType.Error, testClassName, testMethodName, lineNumber, errorText, null);
        entry.groupErrors = Collections.unmodifiableList(groupErrors);
        return entry;
    }

    public List<OptGroupErrorEntry> groupErrors() {
        return groupErrors;
    }

    @Override
    public int compareTo(SnInteractionEntry o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public String toString() {
        return this.testClassName + "." + this.testMethodName + "." + this.threadId;
    }

    public String recordingFileName() {
        return "recording." + toString() + ".htm";
    }

    public static class OptGroupErrorEntry {
        public final int lineNumber;
        public final String text;

        public OptGroupErrorEntry(int lineNumber, String text) {
            this.lineNumber = lineNumber;
            this.text = text;
        }
    }
}
