package org.selion_framework.lib.exception;

public class SeUnexpectedPageException extends RuntimeException {
    public SeUnexpectedPageException(String expectedPageClassName, Throwable th) {
        super(errorMessage(expectedPageClassName), th);
    }

    private static String errorMessage(String expectedPageClassName) {
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
