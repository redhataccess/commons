package com.redhat.common.utils;

import java.util.Objects;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author sfloess
 */
public final class Strings {
    /**
     * Default message when a required string is null or blank.
     */
    public static final String DEFAULT_REQUIRED_ERR_MSG = "Required string is null or empty";

    /**
     * Require a toEnsure to have data.
     *
     * @param toEnsure   the toEnsure to ensure contains data.
     * @param errMessage if null or blank, the error message in the raised exception.
     *
     * @return toEnsure if not null or blank.
     *
     * @throws NullPointerException     if <code>toEnsure</code> is null.
     * @throws IllegalArgumentException if <code>toEnsure</code> is blank.
     */
    public static String require(final String toEnsure, final String errMessage) {
        Objects.requireNonNull(toEnsure, errMessage);

        if (StringUtils.isAllBlank(toEnsure)) {
            throw new IllegalArgumentException(errMessage);
        }

        return toEnsure;
    }

    /**
     * Require a toEnsure to have data.
     *
     * @param toEnsure the toEnsure to ensure contains data.
     *
     * @return toEnsure if not null or blank.
     *
     * @throws NullPointerException     if <code>toEnsure</code> is null.
     * @throws IllegalArgumentException if <code>toEnsure</code> is blank.
     */
    public static String require(final String toEnsure) {
        return require(toEnsure, DEFAULT_REQUIRED_ERR_MSG);
    }

    /**
     * Is the string empty? Empty is either blank, white space or null.
     */
    public static boolean isBlank(final String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * Generate a unique string.
     */
    public static final String generateUniqueString() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate a unique value that has <code>prefix</code> as it's prefix.
     */
    public static String generateUniqueStringForPrefix(final String... prefix) {
        return StringUtils.join(prefix, generateUniqueString());
    }

    /**
     * Generate a unique value that has <code>prefix</code> as it's prefix.
     */
    public static String generateUniqueStringForPrefix(final String prefix) {
        return StringUtils.join(prefix, generateUniqueString());
    }

    /**
     * Generate a unique value that has <code>prefix</code> as it's prefix and <code>suffix</code> as it's suffix.
     */
    public static String generateUniqueString(final String prefix, final String suffix) {
        return StringUtils.join(prefix, generateUniqueString(), suffix);
    }

    /**
     * Generate a unique value that has <code>suffix</code> as it's suffix.
     */
    public static String generateUniqueStringForSuffix(final String... suffix) {
        return StringUtils.join(generateUniqueString(), suffix);
    }

    /**
     * Generate a unique value that has <code>suffix</code> as it's suffix.
     */
    public static String generateUniqueStringForSuffix(final String suffix) {
        return StringUtils.join(generateUniqueString(), suffix);
    }

    /**
     * Return true of value is not null and it's a String.
     */
    public static boolean isString(final Object value) {
        return null != value && String.class == value.getClass();
    }
}
