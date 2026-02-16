package org.emwhyware.selion.lib.exception;

public class SnWaitTimeoutException extends RuntimeException {

    public SnWaitTimeoutException(long durationLimitMilliseconds) {
        super(message(durationLimitMilliseconds));
    }

    public SnWaitTimeoutException(long durationLimitMilliseconds, Throwable th) {
        super(message(durationLimitMilliseconds), th);
    }

    private static String message(long durationLimitMilliseconds) {
        return "Wait time-out: The condition was not met within the wait duration limit of " + durationLimitMilliseconds + " milliseconds";
    }
}
