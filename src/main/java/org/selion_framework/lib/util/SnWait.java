package org.selion_framework.lib.util;

import org.selion_framework.lib.config.SelionConfig;
import org.selion_framework.lib.exception.SnWaitTimeoutException;

public class SnWait {
    private final long startTimestamp;
    private final long durationMilliseconds;
    private final long intervalMilliseconds;
    private final boolean throwExceptionAtTimeout;

    public static void sleep(long durationMilliseconds) {
        final SnWait wait = new SnWait(durationMilliseconds, false);

        wait.doWaitTrue(() -> true);
    }

    public static void waitUntil(SnWaitTrueCondition waitTrueCondition) {
        waitUntil(SelionConfig.config().waitTimeoutMilliseconds, waitTrueCondition);
    }

    public static void waitUntil(SnWaitTrueCondition waitTrueCondition, SnOnTimeout onTimeout) {
        waitUntil(SelionConfig.config().waitTimeoutMilliseconds, waitTrueCondition, onTimeout);
    }

    public static void waitUntil(long maxWaitMilliseconds, SnWaitTrueCondition waitTrueCondition) {
        final SnWait wait = new SnWait(maxWaitMilliseconds, true);

        wait.doWaitTrue(waitTrueCondition);
    }

    public static void waitUntil(long maxWaitMilliseconds, SnWaitTrueCondition waitTrueCondition, SnOnTimeout onTimeout) {
        final SnWait wait = new SnWait(maxWaitMilliseconds, true);

        wait.doWaitTrue(waitTrueCondition, onTimeout);
    }

    public static void waitUpTo(SnWaitTrueCondition waitTrueCondition) {
        waitUpTo(SelionConfig.config().waitTimeoutMilliseconds, waitTrueCondition);
    }

    public static void waitUpTo(long maxWaitMilliseconds, SnWaitTrueCondition waitTrueCondition) {
        final SnWait wait = new SnWait(maxWaitMilliseconds, false);

        wait.doWaitTrue(waitTrueCondition);
    }

    public static <T> T waitUntilNonNull(SnWaitPresenceCondition waitPresenceCondition) {
        return SnWait.waitUntilNonNull(SelionConfig.config().waitTimeoutMilliseconds, waitPresenceCondition);
    }

    public static <T> T waitUntilNonNull(long maxWaitMilliseconds, SnWaitPresenceCondition waitPresenceCondition) {
        final SnWait wait = new SnWait(maxWaitMilliseconds, true);

        return wait.doWaitPresence(waitPresenceCondition);
    }

    private SnWait(long durationMilliseconds, boolean throwExceptionAtTimeout) {
        this.startTimestamp = System.currentTimeMillis();
        this.durationMilliseconds = durationMilliseconds;
        this.intervalMilliseconds = durationMilliseconds < 20000 ? 5 : 10;
        this.throwExceptionAtTimeout = throwExceptionAtTimeout;
    }

    private boolean isTimedOut() {
        return System.currentTimeMillis() > this.startTimestamp + this.durationMilliseconds;
    }

    private void doWaitTrue(SnWaitTrueCondition waitTrueCondition) {
        this.doWaitTrue(waitTrueCondition, null);
    }

    private void doWaitTrue(SnWaitTrueCondition waitTrueCondition, SnOnTimeout onTimeout) {
        while (!waitTrueCondition.waitTrue()) {
            if (isTimedOut()) {
                if (this.throwExceptionAtTimeout && onTimeout == null) {
                    throw new SnWaitTimeoutException(this.durationMilliseconds);
                } else if (this.throwExceptionAtTimeout) {
                    onTimeout.doOnTimeout(new SnWaitTimeoutException(this.durationMilliseconds));
                    return;
                } else {
                    return;
                }
            }
            threadSleep((this.intervalMilliseconds));
        }
    }

    private <T> T doWaitPresence(SnWaitPresenceCondition waitNonNullCondition) {
        Object value = null;

        while ((value = waitNonNullCondition.waitNonNull()) == null) {
            if (isTimedOut()) {
                throw new SnWaitTimeoutException(this.durationMilliseconds);
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
    public interface SnWaitTrueCondition {
        boolean waitTrue();
    }

    public interface SnWaitPresenceCondition<T> {
        T waitNonNull();
    }

    public interface SnOnTimeout {
        void doOnTimeout(SnWaitTimeoutException timeoutException);
    }
}
