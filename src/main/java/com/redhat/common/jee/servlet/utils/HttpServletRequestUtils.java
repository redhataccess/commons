package com.redhat.common.jee.servlet.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Utility class for HttpServletRequests. This class is not unit tested as the
 * HttpServletRequest is a pain to mock. The Cookies interface does not seem to
 * want to mock and is not found (it's declared inside HttpServletRequest)...
 *
 * @author sfloess
 */
public final class HttpServletRequestUtils {

    /**
     * Return the query params contained in request.
     */
    public static Map<String, String[]> getQueryParams(final HttpServletRequest request) {
        final Map<String, String[]> retVal = new HashMap<>();

        // Just in case the request is null - shouldn't be!
        retVal.putAll(null != request ? request.getParameterMap() : Collections.EMPTY_MAP);

        return retVal;
    }

    /**
     * Default constructor not allowed.
     */
    private HttpServletRequestUtils() {
    }
}
