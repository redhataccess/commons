package com.redhat.common.processor;

/**
 *
 * @author sfloess
 */
public class ProcessorException extends RuntimeException {
    public ProcessorException() {
        super();
    }

    public ProcessorException(final String message) {
        super(message);
    }

    public ProcessorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ProcessorException(final Throwable cause) {
        super(cause);
    }
}
