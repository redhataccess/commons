package com.redhat.common.function;

/**
 * Abstract base class for a command.
 *
 * @author sfloess
 */
public abstract class AbstractExtendedConsumer<T> implements ExtendedConsumer<T> {
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
