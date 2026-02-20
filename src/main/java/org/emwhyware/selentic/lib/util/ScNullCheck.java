package org.emwhyware.selentic.lib.util;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Checks for null and throw {@link IllegalStateException} if fails. This is used to ensure the null safety of
 * variables throughout the framework.
 *
 * @see NonNull
 * @see Nullable
 */
public final class ScNullCheck {
    /**
     * Check if object is null. Throws {@link IllegalStateException} if null. Otherwise, the value is casted to
     * provided type.
     *
     * @param object checked object
     * @param classType class type to be returned
     * @return non-null casted value
     * @param <T> any type
     */
    public static @NonNull <T> T requiresNonNull(@Nullable T object, @NonNull Class<T> classType) {
        if (object == null) {
            throw new IllegalStateException("Object is not set. class = " + classType.getName());
        }
        return object;
    }

    /**
     * Check if text is null. Throws {@link IllegalStateException} if null. Otherwise, the String value is returned.
     *
     * @param text checked String
     * @return non-null String value
     */
    public static @NonNull String requiresNonNull(@Nullable String text) {
        return requiresNonNull(text, String.class);
    }
}
