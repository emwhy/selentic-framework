package org.selion_framework.lib.util;

import org.selion_framework.lib.config.SelionConfig;
import org.selion_framework.lib.exception.SeWaitTimeoutException;

public class SeWait {
    private final long startTimestamp;
    private final long durationMilliseconds;
    private final long intervalMilliseconds;
    private final boolean throwExceptionAtTimeout;

    public static void sleep(long durationMilliseconds) {
        final SeWait wait = new SeWait(durationMilliseconds, false);

        wait.doWaitTrue(() -> true);
    }

    public static void waitUntil(WaitTrueCondition waitTrueCondition) {
        waitUntil(SelionConfig.config().waitTimeoutMilliseconds, waitTrueCondition);
    }

    public static void waitUntil(long maxWaitMilliseconds, WaitTrueCondition waitTrueCondition) {
        final SeWait wait = new SeWait(maxWaitMilliseconds, true);

        wait.doWaitTrue(waitTrueCondition);
    }

    public static void waitUpTo(WaitTrueCondition waitTrueCondition) {
        waitUpTo(SelionConfig.config().waitTimeoutMilliseconds, waitTrueCondition);
    }

    public static void waitUpTo(long maxWaitMilliseconds, WaitTrueCondition waitTrueCondition) {
        final SeWait wait = new SeWait(maxWaitMilliseconds, false);

        wait.doWaitTrue(waitTrueCondition);
    }

    public static <T> T waitUntilNonNull(WaitPresenceCondition waitPresenceCondition) {
        return SeWait.waitUntilNonNull(SelionConfig.config().waitTimeoutMilliseconds, waitPresenceCondition);
    }

    public static <T> T waitUntilNonNull(long maxWaitMilliseconds, WaitPresenceCondition waitPresenceCondition) {
        final SeWait wait = new SeWait(maxWaitMilliseconds, true);

        return wait.doWaitPresence(waitPresenceCondition);
    }

    private SeWait(long durationMilliseconds, boolean throwExceptionAtTimeout) {
        this.startTimestamp = System.currentTimeMillis();
        this.durationMilliseconds = durationMilliseconds;
        this.intervalMilliseconds = durationMilliseconds < 20000 ? 5 : 10;
        this.throwExceptionAtTimeout = throwExceptionAtTimeout;
    }

    private boolean isTimedOut() {
        return System.currentTimeMillis() > this.startTimestamp + this.durationMilliseconds;
    }

    private void doWaitTrue(WaitTrueCondition waitTrueCondition) {
        while (!waitTrueCondition.waitTrue()) {
            if (isTimedOut()) {
                if (this.throwExceptionAtTimeout) {
                    throw new SeWaitTimeoutException(this.durationMilliseconds);
                } else {
                    return;
                }
            }
            threadSleep((this.intervalMilliseconds));
        }
    }

    private <T> T doWaitPresence(WaitPresenceCondition waitNonNullCondition) {
        Object value = null;

        while ((value = waitNonNullCondition.waitNonNull()) == null) {
            if (isTimedOut()) {
                throw new SeWaitTimeoutException(this.durationMilliseconds);
            }
            threadSleep((this.intervalMilliseconds));
        }
        return (T) value;
    }

    private void threadSleep(long intervalMilliseconds) {
        try {
            Thread.sleep(this.intervalMilliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Interface sets up the predicate.
     */
    public interface WaitTrueCondition {
        boolean waitTrue();
    }

    public interface WaitPresenceCondition<T> {
        T waitNonNull();
    }
}
