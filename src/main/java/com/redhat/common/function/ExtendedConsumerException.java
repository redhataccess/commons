package com.redhat.common.function;

/**
 *
 * @author sfloess
 */
public class ExtendedConsumerException extends RuntimeException {
    public ExtendedConsumerException() {
        super();
    }

    public ExtendedConsumerException(final String message) {
        super(message);
    }

    public ExtendedConsumerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ExtendedConsumerException(final Throwable cause) {
        super(cause);
    }
}
