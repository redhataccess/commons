package com.redhat.common.utils;

/**
 * When computing a field value, use this factory to compute the actual data type.
 *
 * @author Scot P. Floess
 */
public interface SolrInputDocumentFieldFactory {
    /**
     * Converts the value to a good field data type.
     */
    Object computeFieldValue(final String name, final String value);
}
