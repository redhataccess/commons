package com.redhat.common.markup;

/**
 *
 * @author sfloess
 */
public class MarkupException extends RuntimeException {
    public MarkupException() {
        super();
    }

    public MarkupException(final String message) {
        super(message);
    }

    public MarkupException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MarkupException(final Throwable cause) {
        super(cause);
    }
}
