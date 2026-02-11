package org.selion_framework.lib.exception;

public class SeWaitTimeoutException extends RuntimeException {

    public SeWaitTimeoutException(long durationLimitMilliseconds) {
        super(message(durationLimitMilliseconds));
    }

    public SeWaitTimeoutException(long durationLimitMilliseconds, Throwable th) {
        super(message(durationLimitMilliseconds), th);
    }

    private static String message(long durationLimitMilliseconds) {
        return "Wait time-out: The condition was not met within the wait duration limit of " + durationLimitMilliseconds + " milliseconds";
    }
}
