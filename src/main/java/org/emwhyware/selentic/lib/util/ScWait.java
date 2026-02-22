package org.emwhyware.selentic.lib.util;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.emwhyware.selentic.lib.config.SelenticConfig;
import org.emwhyware.selentic.lib.exception.ScWaitTimeoutException;

/**
 * Utility class for handling wait conditions and timeouts.
 * <p>
 * The {@code ScWait} class provides a flexible framework for waiting until specific conditions
 * are met or until a specified timeout is reached. It supports three types of wait operations:
 * <ul>
 *   <li><strong>Sleep</strong>: Simple thread sleep for a specified duration</li>
 *   <li><strong>Wait Until True</strong>: Waits until a boolean condition becomes true or timeout occurs</li>
 *   <li><strong>Wait Until Non-Null</strong>: Waits until a value becomes non-null or timeout occurs</li>
 * </ul>
 *
 * <p>
 * All wait operations poll at regular intervals (5ms for durations &lt; 20 seconds, 10ms otherwise)
 * and throw {@link ScWaitTimeoutException} if the condition is not met within the specified timeout.
 * 
 *
 * <h2>Usage Examples:</h2>
 * <pre>
 * // Simple sleep
 * ScWait.sleep(1000);
 *
 * // Wait until condition is true using default timeout
 * ScWait.waitUntil(() -> component.isDisplayed());
 *
 * // Wait until condition is true with custom timeout
 * ScWait.waitUntil(5000, () -> component.isDisplayed());
 *
 * // Wait until condition is true with custom timeout and exception handler
 * ScWait.waitUntil(5000, () -> component.isDisplayed(),
 *     exception -> new CustomException("Component not found"));
 *
 * // Wait until value is non-null using default timeout
 * String value = ScWait.waitUntilNonNull(() -> component.text());
 *
 * // Wait until value is non-null with custom timeout
 * String value = ScWait.waitUntilNonNull(3000, () -> component.text());
 * </pre>
 */
public class ScWait {
    private final long startTimestamp;
    private final long durationMilliseconds;
    private final long intervalMilliseconds;

    /**
     * Pauses execution for the specified duration.
     *
     * @param durationMilliseconds the duration to sleep in milliseconds
     * @throws IllegalArgumentException if durationMilliseconds is negative
     */
    public static void sleep(long durationMilliseconds) {
        final ScWait wait = new ScWait(durationMilliseconds);

        try {
            wait.doWaitTrue(() -> false);
        } catch (ScWaitTimeoutException ex) {
            // Do nothing.
        }
    }

    /**
     * Waits until the specified condition becomes true using the default timeout.
     * <p>
     * The default timeout is retrieved from {@link SelenticConfig#waitTimeoutMilliseconds()}.
     * If the condition is not satisfied within the timeout period, a {@link ScWaitTimeoutException}
     * is thrown.
     * 
     *
     * @param waitTrueCondition the condition to evaluate, must not be null
     * @throws ScWaitTimeoutException if the condition does not become true within the default timeout
     * @throws NullPointerException if waitTrueCondition is null
     */
    public static void waitUntil(@NonNull ScWaitTrueCondition waitTrueCondition) {
        waitUntil(SelenticConfig.config().waitTimeoutMilliseconds(), waitTrueCondition);
    }

    /**
     * Waits until the specified condition becomes true using the default timeout with custom exception handling.
     * <p>
     * The default timeout is retrieved from {@link SelenticConfig#waitTimeoutMilliseconds()}.
     * If the timeout is exceeded, the {@code onTimeout} handler is invoked to determine the exception to throw.
     * 
     *
     * @param waitTrueCondition the condition to evaluate, must not be null
     * @param onTimeout the timeout handler that processes the timeout exception, may be null
     * @throws RuntimeException the exception returned by the onTimeout handler, or {@link ScWaitTimeoutException} if handler returns null
     * @throws NullPointerException if waitTrueCondition is null
     */
    public static void waitUntil(@NonNull ScWaitTrueCondition waitTrueCondition, ScOnTimeout onTimeout) {
        waitUntil(SelenticConfig.config().waitTimeoutMilliseconds(), waitTrueCondition, onTimeout);
    }

    /**
     * Waits until the specified condition becomes true or the maximum wait time is exceeded.
     * <p>
     * If the condition is not satisfied within the specified timeout, a {@link ScWaitTimeoutException} is thrown.
     * 
     *
     * @param maxWaitMilliseconds the maximum time to wait in milliseconds
     * @param waitTrueCondition the condition to evaluate, must not be null
     * @throws ScWaitTimeoutException if the condition does not become true within maxWaitMilliseconds
     * @throws NullPointerException if waitTrueCondition is null
     */
    public static void waitUntil(long maxWaitMilliseconds, @NonNull ScWaitTrueCondition waitTrueCondition) {
        final ScWait wait = new ScWait(maxWaitMilliseconds);

        wait.doWaitTrue(waitTrueCondition);
    }

    /**
     * Waits until the specified condition becomes true or the maximum wait time is exceeded with custom exception handling.
     * <p>
     * If the timeout is exceeded, the {@code onTimeout} handler is invoked to determine the exception to throw.
     * If the handler returns null, a default {@link ScWaitTimeoutException} is thrown.
     * 
     *
     * @param maxWaitMilliseconds the maximum time to wait in milliseconds
     * @param waitTrueCondition the condition to evaluate, must not be null
     * @param onTimeout the timeout handler that processes the timeout exception, may be null
     * @throws RuntimeException the exception returned by the onTimeout handler, or {@link ScWaitTimeoutException} if handler returns null or onTimeout is null
     * @throws NullPointerException if waitTrueCondition is null
     */
    public static void waitUntil(long maxWaitMilliseconds, @NonNull ScWaitTrueCondition waitTrueCondition, ScOnTimeout onTimeout) {
        final ScWait wait = new ScWait(maxWaitMilliseconds);

        wait.doWaitTrue(waitTrueCondition, onTimeout);
    }

    /**
     * Waits until a non-null value is returned from the specified condition using the default timeout.
     * <p>
     * The default timeout is retrieved from {@link SelenticConfig#waitTimeoutMilliseconds()}.
     * This method is useful for waiting until an element or object is available before proceeding.
     * 
     *
     * @param <T> the type of the value to be returned
     * @param waitPresenceCondition the condition that returns the value to check, must not be null
     * @return the non-null value returned by the condition
     * @throws ScWaitTimeoutException if no non-null value is obtained within the default timeout
     * @throws NullPointerException if waitPresenceCondition is null
     */
    public static <T> @NonNull T waitUntilNonNull(@NonNull ScWaitNonNullCondition<T> waitPresenceCondition) {
        return ScWait.waitUntilNonNull(SelenticConfig.config().waitTimeoutMilliseconds(), waitPresenceCondition);
    }

    /**
     * Waits until a non-null value is returned from the specified condition or the maximum wait time is exceeded.
     * <p>
     * This method is useful for waiting until an element or object becomes available before proceeding.
     * 
     *
     * @param <T> the type of the value to be returned
     * @param maxWaitMilliseconds the maximum time to wait in milliseconds
     * @param waitPresenceCondition the condition that returns the value to check, must not be null
     * @return the non-null value returned by the condition
     * @throws ScWaitTimeoutException if no non-null value is obtained within maxWaitMilliseconds
     * @throws NullPointerException if waitPresenceCondition is null
     */
    public static <T> @NonNull T waitUntilNonNull(long maxWaitMilliseconds, @NonNull ScWaitNonNullCondition<T> waitPresenceCondition) {
        final ScWait wait = new ScWait(maxWaitMilliseconds);

        return wait.doWaitNonNull(waitPresenceCondition);
    }

    /**
     * Constructs a new ScWait instance with the specified duration.
     *
     * @param durationMilliseconds the duration in milliseconds
     */
    private ScWait(long durationMilliseconds) {
        this.startTimestamp = System.currentTimeMillis();
        this.durationMilliseconds = durationMilliseconds;
        this.intervalMilliseconds = durationMilliseconds < 20000 ? durationMilliseconds/5 : durationMilliseconds/10;
    }

    /**
     * Checks if the wait duration has been exceeded.
     *
     * @return true if the current time exceeds the start time plus duration, false otherwise
     */
    private boolean isTimedOut() {
        return System.currentTimeMillis() > this.startTimestamp + this.durationMilliseconds;
    }

    /**
     * Internal method that repeatedly checks a condition until it becomes true or timeout occurs.
     *
     * @param waitTrueCondition the condition to evaluate
     */
    private void doWaitTrue(@NonNull ScWaitTrueCondition waitTrueCondition) {
        this.doWaitTrue(waitTrueCondition, null);
    }

    /**
     * Internal method that repeatedly checks a condition until it becomes true or timeout occurs.
     * <p>
     * If a timeout occurs and an onTimeout handler is provided, the handler is invoked to determine
     * which exception should be thrown. If no handler is provided or if the handler returns null,
     * a {@link ScWaitTimeoutException} is thrown.
     * 
     *
     * @param waitTrueCondition the condition to evaluate
     * @param onTimeout the optional timeout handler
     * @throws RuntimeException if timeout occurs and handler is invoked, or {@link ScWaitTimeoutException} otherwise
     */
    private void doWaitTrue(@NonNull ScWaitTrueCondition waitTrueCondition, @Nullable ScOnTimeout onTimeout) {
        while (!waitTrueCondition.waitTrue()) {
            if (isTimedOut()) {
                if (onTimeout == null) {
                    throw new ScWaitTimeoutException(this.durationMilliseconds);
                } else {
                    final RuntimeException ex;

                    if ((ex = onTimeout.doOnTimeout(new ScWaitTimeoutException(this.durationMilliseconds))) == null) {
                        throw new ScWaitTimeoutException(this.durationMilliseconds);
                    } else {
                        throw ex;
                    }
                }
            }
            threadSleep();
        }
    }

    /**
     * Internal method that repeatedly checks a condition until it returns a non-null value or timeout occurs.
     *
     * @param <T> the type of the value to be returned
     * @param waitNonNullCondition the condition that returns the value to check
     * @return the non-null value from the condition
     * @throws ScWaitTimeoutException if the condition consistently returns null until timeout
     */
    @SuppressWarnings("unchecked")
    private <T> @NonNull T doWaitNonNull(@NonNull ScWaitNonNullCondition<T> waitNonNullCondition) {
        Object value;

        while ((value = waitNonNullCondition.waitNonNull()) == null) {
            if (isTimedOut()) {
                throw new ScWaitTimeoutException(this.durationMilliseconds);
            }
            threadSleep();
        }
        return (@NonNull T) value;
    }

    /**
     * Sleeps the current thread for the specified interval while handling interruptions.
     * <p>
     * If the thread is interrupted during sleep, the interrupt status is restored on the current thread.
     * 
     */
    private void threadSleep() {
        try {
            Thread.sleep(this.intervalMilliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Functional interface that defines a condition to be repeatedly evaluated until it returns true.
     * <p>
     * Implementations should return true when the desired condition is met, and false otherwise.
     * The condition is evaluated at regular polling intervals until it returns true or timeout occurs.
     * 
     */
    public interface ScWaitTrueCondition {
        /**
         * Evaluates the wait condition.
         *
         * @return true if the condition is met, false otherwise
         */
        boolean waitTrue();
    }

    /**
     * Functional interface that defines a condition that returns a value to be checked for non-null.
     * <p>
     * Implementations should return a non-null value when the desired object/element is available,
     * or null if it's not yet available. The condition is evaluated at regular polling intervals
     * until it returns a non-null value or timeout occurs.
     * 
     *
     * @param <T> the type of the value returned by this condition
     */
    public interface ScWaitNonNullCondition<T> {
        /**
         * Evaluates the condition and returns a value.
         *
         * @return the value being waited for, or null if not yet available
         */
        @Nullable T waitNonNull();
    }

    /**
     * Functional interface for handling timeout events.
     * <p>
     * When a wait operation times out, the {@code doOnTimeout} method is invoked to allow
     * custom exception handling. If this method returns null, a default {@link ScWaitTimeoutException}
     * is thrown. If it returns an exception, that exception is thrown instead.
     * 
     */
    public interface ScOnTimeout {
        /**
         * Handles a timeout event and determines which exception should be thrown.
         *
         * @param timeoutException the {@link ScWaitTimeoutException} that triggered the timeout
         * @return a custom exception to throw, or null to throw the default timeout exception
         */
        RuntimeException doOnTimeout(@NonNull ScWaitTimeoutException timeoutException);
    }
}