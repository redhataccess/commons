package com.redhat.common;

/**
 *
 * API to convert an object to a StringBuilder and makes it pretty.
 *
 * @author sfloess
 *
 */
public interface Stringifiable {
    String LINE_SEPARATOR_PROPERTY = "line.separator";

    String LINE_SEPARATOR_STRING = System.getProperty(LINE_SEPARATOR_PROPERTY);

    /**
     * A default prefix to use.
     */
    String DEFAULT_PREFIX = "    ";

    /**
     * Using <code>stringBuilder</code>, compute the string representation of self whose prefix for computation is
     * <code>prefix</code>.
     *
     * @param stringBuilder will have the string representation of self appended.
     * @param prefix        is the prefix to be first appended prior to self's string representation.
     *
     * @return a string builder that can be reused.
     */
    StringBuilder toStringBuilder(StringBuilder stringBuilder, String prefix);

    /**
     * Using <code>stringBuilder</code>, compute the string representation of self.
     *
     * @param stringBuilder will have the string representation of self appended.
     *
     * @return a string builder that can be reused.
     */
    StringBuilder toStringBuilder(StringBuilder stringBuilder);

    /**
     * Using <code>prefix</code> return the string representation of self.
     *
     * @param prefix is the prefix to emit when return the string representation of self.
     *
     * @return the string representation of self.
     */
    StringBuilder toStringBuilder(String prefix);
}
