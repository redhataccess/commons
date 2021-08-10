package com.redhat.common.processor.executor;

import com.redhat.common.AbstractBase;
import com.redhat.common.processor.Processor;
import com.redhat.common.processor.ProcessorException;

import java.util.Objects;

/**
 * Abstract base class for using a processor to process an object.
 *
 * @author sfloess
 */
public abstract class AbstractExecutor<T, P extends Processor<T>> extends AbstractBase implements Executor<T, P> {
    /**
     * Give subclasses a chance to manage pre-processing.
     */
    protected T doPreProcess(final P processor, final T toProcess) {
        return processor.preProcess(toProcess);
    }

    /**
     * Subclasses should perform actual processing.
     */
    protected abstract T doProcess(final P processor, final T toProcess);

    /**
     * Give subclasses a chance to manage post-processing.
     */
    protected T doPostProcess(final P processor, final T toProcess, final T result) {
        return processor.postProcess(toProcess, result);
    }

    /**
     * Give subclasses a chance to manage post-processing failures.
     */
    protected void doPostProcessFailure(final P processor, final T toProcess, final Throwable failure) {
        processor.postProcessFailure(toProcess, failure);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T executeProcessor(final P processor, final T toProcess) {
        Objects.requireNonNull(processor, "Cannot provide a null processor for processing!");
        Objects.requireNonNull(toProcess, "Cannot provide a null object for processing!");

        try {
            return doPostProcess(processor, toProcess, doProcess(processor, doPreProcess(processor, toProcess)));
        } catch (final Throwable throwable) {
            logError(throwable, "Processor [", processor, "] had trouble processing: ", toProcess);

            doPostProcessFailure(processor, toProcess, throwable);

            throw new ProcessorException(throwable);
        }
    }
}
