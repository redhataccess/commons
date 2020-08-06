package com.redhat.common.markup.json;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redhat.common.AbstractBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract base class for JSON Objects
 *
 * @author sfloess
 */
public abstract class AbstractJsonBase extends AbstractBase {
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonIgnore
    private List<String> orderedAdditionalProperties = new ArrayList<>();

    @JsonAnyGetter
    protected Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnyGetter
    protected List<String> getOrderedAdditionalProperties() {
        return this.orderedAdditionalProperties;
    }

    @JsonAnySetter
    protected void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        this.orderedAdditionalProperties.add(0, name);

        logWarning("Received unmanaged property [", name, "] -> [", value, "]  Allowing sub-class to process");

        processUnmanagedProperty(name, value);
    }

    /**
     * Called when we receive an unmanaged property.
     *
     * @param name  the name of the unmanaged property.
     * @param value the value of the unmanaged property.
     */
    protected abstract void processUnmanagedProperty(final String name, final Object value);
}
