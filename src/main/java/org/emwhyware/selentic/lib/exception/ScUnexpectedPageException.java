package org.emwhyware.selentic.lib.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ScUnexpectedPageException extends RuntimeException {
    public ScUnexpectedPageException(@NonNull String expectedPageClassName, @NonNull Throwable th) {
        super(errorMessage(expectedPageClassName), th);
    }

    private static String errorMessage(@NonNull String expectedPageClassName) {
        final String errorMessage = """
                Expected page was not loaded. The possible reason may be:
                    - The previous action is behaving unexpectedly.
                    - It is taking more time than expected before
                      displaying the component.
                    - Improper implementation in 'waitAdditional()'
                      method to indicate that the page being loaded.
                """;

        return errorMessage + "\n\t\tThe expected page was " + expectedPageClassName;
    }
}
