package com.redhat.common.var;

import java.util.Map;

/**
 * Dictionary for managing variables (name as key).
 *
 * @author sfloess
 */
public interface Variables {

    /**
     * Return a value whose name is <code>name</code> or null if not found. If you'd rather not receive a null on not
     * found, use the get() method with that allows a default value.
     */
    <T> T get(String name);

    /**
     * Used when computing a value. If value is null, we return defaultValue.
     */
    default <T> T computeValue(final T value, final T defaultValue) {
        return null != value ? value : defaultValue;
    }

    /**
     * Return a global variable or defaultValue if not found.
     */
    default <T> T get(String name, T defaultValue) {
        final T possibleReturn = get(name);
        return null != possibleReturn ? possibleReturn : defaultValue;
    }

    /**
     * Store value and assign its name.
     */
    <T> T set(String name, T value);

    /**
     * Remove a value named <code>name</code> and return the value removed. If there was no value, null will be
     * returned.
     */
    <T> T remove(String name);

    /**
     * Removes all variables.
     */
    void clear();

    /**
     * Return all the name/values in the context.
     */
    Map<String, ?> asMap();
}
