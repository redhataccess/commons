package com.redhat.common.processor;

import com.redhat.common.AbstractJsonBase;

/**
 * Abstract base class for processors
 *
 * @author sfloess
 */
public abstract class AbstractProcessor<T> extends AbstractJsonBase implements Processor<T> {
    /**
     * Our name.
     */
    private String name;

    /**
     * Our description.
     */
    private String description;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void processUnmanagedProperty(final String name, final Object value) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the name of the processor.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the processor.
     */
    public void setDescription(final String description) {
        this.description = description;
    }
}
