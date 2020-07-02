package com.redhat.common.processor;

/**
 * Interface for classes that can process an object.
 *
 * @author sfloess
 */
@FunctionalInterface
public interface Processor<T> {
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
    default T preProcess(T preProcess) {
        return preProcess;
    }

    /**
     * Perform processing.
     */
    T process(T toProcess);

    /**
     * Called after processing is invoked.
     */
    default T postProcess(T processed, T resultOfProcessing) {
        return resultOfProcessing;
    }

    /**
     * Called after processing is invoked when there is a failure.
     */
    default T postProcessFailure(T failedProcess, final Throwable failure) {
        throw new ProcessorException("Failed to process:  " + failedProcess, failure);
    }

}
