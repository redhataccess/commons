package com.redhat.common;

import com.redhat.common.markup.json.*;

/**
 *
 * @author sfloess
 */
public class CommonException extends RuntimeException {
    public CommonException() {
        super();
    }

    public CommonException(final String message) {
        super(message);
    }

    public CommonException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CommonException(final Throwable cause) {
        super(cause);
    }
}
