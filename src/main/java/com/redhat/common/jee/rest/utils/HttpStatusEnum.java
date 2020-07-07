package com.redhat.common.jee.rest.utils;

import javax.ws.rs.core.Response;

/**
 * Helps us with HTTP Status Codes as returned from Fusion/Solr as well as allow
 * us to raise exceptions on the Strata side.
 *
 * @author sfloess
 */
public enum HttpStatusEnum {
    OK(Response.Status.OK),
    CREATED(Response.Status.CREATED),
    ACCEPTED(Response.Status.ACCEPTED),
    NO_CONTENT(Response.Status.NO_CONTENT),
    MOVED_PERMANENTLY(Response.Status.MOVED_PERMANENTLY),
    SEE_OTHER(Response.Status.SEE_OTHER),
    NOT_MODIFIED(Response.Status.NOT_MODIFIED),
    TEMPORARY_REDIRECT(Response.Status.TEMPORARY_REDIRECT),
    BAD_REQUEST(Response.Status.BAD_REQUEST),
    UNAUTHORIZED(Response.Status.UNAUTHORIZED),
    FORBIDDEN(Response.Status.FORBIDDEN),
    NOT_FOUND(Response.Status.NOT_FOUND),
    NOT_ACCEPTABLE(Response.Status.NOT_ACCEPTABLE),
    CONFLICT(Response.Status.CONFLICT),
    GONE(Response.Status.GONE),
    PRECONDITION_FAILED(Response.Status.PRECONDITION_FAILED),
    UNSUPPORTED_MEDIA_TYPE(Response.Status.UNSUPPORTED_MEDIA_TYPE),
    INTERNAL_SERVER_ERROR(Response.Status.INTERNAL_SERVER_ERROR),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(Response.Status.SERVICE_UNAVAILABLE);

    private static final HttpStatusEnum DEFAULT_HTTP_STATUS = HttpStatusEnum.BAD_GATEWAY;

    private final int statusCode;
    private final String statusCodeAsStr;

    /**
     * This method will iterate across all our enum values looking for the one
     * whose code str is str. If one isn't found, the defaultHttpStatus is
     * returned... Additionally, it assumes str has already been trimmed. I
     */
    static HttpStatusEnum findHttpStatusEnumByStatusCodeStr(final String str, final HttpStatusEnum defaultHttpStatus) {
        if ("".equals(str)) {
            return defaultHttpStatus;
        }

        for (final HttpStatusEnum stat : HttpStatusEnum.values()) {
            if (stat.getStatusCodeAsStr().equals(str)) {
                return stat;
            }
        }

        return defaultHttpStatus;
    }

    /**
     * Sets the HTTP status code as an int.
     */
    private HttpStatusEnum(final int httpStatusCode) {
        this.statusCode = httpStatusCode;
        this.statusCodeAsStr = String.valueOf(httpStatusCode);
    }

    /**
     * Uses httpStatus' int value.
     */
    private HttpStatusEnum(final Response.Status httpStatus) {
        this(httpStatus.getStatusCode());
    }

    /**
     * Return our status code as an int.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Return our status code as a string.
     */
    public String getStatusCodeAsStr() {
        return statusCodeAsStr;
    }

    /**
     * Using statusCode, find the corresponding enum value that it represents.
     * If one isn't found, return defaultHttpStatus.
     */
    public static HttpStatusEnum findByStatusCode(final int statusCode, final HttpStatusEnum defaultHttpStatus) {
        for (final HttpStatusEnum stat : HttpStatusEnum.values()) {
            if (statusCode == stat.getStatusCode()) {
                return stat;
            }
        }

        return defaultHttpStatus;
    }

    /**
     * Using statusCode, find the correspond enum value. If one isn't found we
     * return DEFAULT_HTTP_STATUS.
     *
     * @see DEFAULT_HTTP_STATUS
     */
    public static HttpStatusEnum findByStatusCode(final int statusCode) {
        return findByStatusCode(statusCode, DEFAULT_HTTP_STATUS);
    }

    /**
     * Find the enum value whose string version of status code is str. If not
     * found, return defaultHttpStatus.
     */
    public static HttpStatusEnum findByStr(final String str, final HttpStatusEnum defaultHttpStatus) {
        if (null == str) {
            return defaultHttpStatus;
        }

        return findHttpStatusEnumByStatusCodeStr(str.trim(), defaultHttpStatus);
    }

    /**
     * Find the enum value whose string version of status code is str. If not
     * found, return DEFAULT_HTTP_STATUS.
     *
     * @see DEFAULT_HTTP_STATUS
     */
    public static HttpStatusEnum findByStr(final String str) {
        return findByStr(str, DEFAULT_HTTP_STATUS);
    }
}
