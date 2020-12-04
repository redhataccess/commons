package com.redhat.common.function;

import java.util.function.Consumer;

/**
 *
 * @author sfloess
 */
public interface ExtendedConsumer<T> extends Consumer<T> {
    /**
     * The name of a processor.
     */
    default public String getName() {
        return "";
    }

    /**
     * A description of the processor.
     */
    default public String getDescription() {
        return "";
    }

    /**
     * Called before processing is invoked.
     */
    default T preApply(T preProcess) {
        return preProcess;
    }

    /**
     * Called after processing is invoked.
     */
    default T postApply(T processed) {
        return processed;
    }

    /**
     * Called after processing is invoked when there is a failure.
     */
    default T postApplyFailure(T failedProcess, final Throwable failure) {
        throw new ExtendedConsumerException("Failed to process:  " + failedProcess, failure);
    }
}
