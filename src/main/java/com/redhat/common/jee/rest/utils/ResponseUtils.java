package com.redhat.common.jee.rest.utils;

import com.redhat.common.utils.HttpStatusEnum;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Response utility class.
 *
 * @author sfloess
 */
public final class ResponseUtils {

    /**
     * Create a response builder.
     *
     * @param status is the status of the response.
     *
     * @return a response builder.
     */
    public static Response.ResponseBuilder createResponseBuilderForStatusCode(final int status) {
        return Response.status(status);
    }

    /**
     * Create a response builder.
     *
     * @param status is the status of the response.
     *
     * @return a response builder.
     */
    public static Response.ResponseBuilder createResponseBuilderForStatus(final Response.Status status) {
        return Response.status(status);
    }

    /**
     * Create a response builder.
     *
     * @param entity to include in our response.
     * @param status the return status code.
     *
     * @return a response builder.
     */
    public static <T> Response.ResponseBuilder createResponseBuilderForEntity(final T entity, final int status) {
        return createResponseBuilderForStatusCode(status).entity(entity);
    }

    /**
     * Create a response builder.
     *
     * @param entity to include in our response.
     * @param status the return status code.
     *
     * @return a response builder.
     */
    public static <T> Response.ResponseBuilder createResponseBuilderForEntity(final T entity, final Response.Status status) {
        return createResponseBuilderForEntity(entity, status.getStatusCode());
    }

    /**
     * Create a response.
     *
     * @param status is the status of the response.
     *
     * @return a response builder.
     */
    public static Response createResponseForStatus(final Response.Status status) {
        return createResponseBuilderForStatus(status).build();
    }

    /**
     * Create an a response for entity whose status is status.
     *
     * @param entity create a response for this.
     * @param status the Http Status for the response.
     *
     * @return a response for the entity and status.
     */
    public static <T> Response createResponseForEntity(final T entity, final int status) {
        return createResponseBuilderForEntity(entity, status).build();
    }

    /**
     * Create an a response for entity whose status is status.
     *
     * @param entity create a response for this.
     * @param status the Http Status for the response.
     *
     * @return a response for the entity and status.
     */
    public static <T> Response createResponseForEntity(final T entity, final int status, final MediaType mediaType) {
        return createResponseBuilderForEntity(entity, status).type(mediaType).build();
    }

    /**
     * Create an a response for entity whose status is status.
     *
     * @param entity create a response for this.
     * @param status the Http Status for the response.
     *
     * @return a response for the entity and status.
     */
    public static <T> Response createResponseForEntity(final T entity, final Response.Status status) {
        return createResponseForEntity(entity, status.getStatusCode());
    }

    /**
     * Create an a response for entity whose status is status.
     *
     * @param entity create a response for this.
     * @param status the Http Status for the response.
     *
     * @return a response for the entity and status.
     */
    public static <T> Response createResponseForEntity(final T entity, final Response.Status status, final MediaType mediaType) {
        return createResponseBuilderForEntity(entity, status.getStatusCode()).type(mediaType).build();
    }

    /**
     * Create an a response for entity whose status is status.
     *
     * @param entity create a response for this.
     * @param status the Http Status for the response.
     *
     * @return a response for the entity and status.
     */
    public static <T> Response createResponseForEntity(final T entity, final HttpStatusEnum status) {
        return createResponseForEntity(entity, status.getStatusCode());
    }

    /**
     * Create an a response for entity whose status is status.
     *
     * @param entity create a response for this.
     * @param status the Http Status for the response.
     *
     * @return a response for the entity and status.
     */
    public static <T> Response createResponseForEntity(final T entity, final HttpStatusEnum status, final MediaType mediaType) {
        return createResponseBuilderForEntity(entity, status.getStatusCode()).type(mediaType).build();
    }

    /**
     * Default constructor not allowed.
     */
    private ResponseUtils() {
    }
}
