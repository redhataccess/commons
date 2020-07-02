package com.redhat.common.processor.executor;

import com.redhat.common.processor.Processor;

/**
 *
 * @author sfloess
 */
public interface Executor<T, P extends Processor<T>> {

    /**
     * Using processor, will force it to process toProcess.
     *
     * @param processor the processor who will process.
     * @param toProcess the thing processor will process.
     *
     * @return the result of processing.
     */
    default T executeProcessor(final P processor, final T toProcess) {
        return toProcess;
    }
}
