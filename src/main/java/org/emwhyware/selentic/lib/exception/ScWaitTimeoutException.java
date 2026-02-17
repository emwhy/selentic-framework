package org.emwhyware.selentic.lib.exception;

public class ScWaitTimeoutException extends RuntimeException {

    public ScWaitTimeoutException(long durationLimitMilliseconds) {
        super(message(durationLimitMilliseconds));
    }

    public ScWaitTimeoutException(long durationLimitMilliseconds, Throwable th) {
        super(message(durationLimitMilliseconds), th);
    }

    private static String message(long durationLimitMilliseconds) {
        return "Wait time-out: The condition was not met within the wait duration limit of " + durationLimitMilliseconds + " milliseconds";
    }
}
