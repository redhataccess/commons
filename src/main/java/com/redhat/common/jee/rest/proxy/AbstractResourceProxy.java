package com.redhat.common.jee.rest.proxy;

import com.redhat.common.AbstractBase;
import com.redhat.common.jee.rest.utils.HttpServletRequestUtils;
import com.redhat.common.jee.rest.utils.ResponseEnum;
import com.redhat.common.jee.rest.utils.ResponseUtils;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

/**
 * Abstract base class for REST resources...
 *
 * @param <S> the type of service we call.
 *
 * @author sfloess
 */
public abstract class AbstractResourceProxy<S> extends AbstractBase {

    /**
     * The servlet request.
     */
    private HttpServletRequest request;

    /**
     * The service we are using.
     */
    private S service;

    /**
     * Default constructor.
     */
    protected AbstractResourceProxy() {
    }

    /**
     *
     * @param request
     */
    @Context
    protected void setRequest(final HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Return the current HTTP Request
     */
    protected HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Converts the HttpServletRequest to query params...
     */
    protected Map<String, String[]> getQueryParams() {
        return HttpServletRequestUtils.getQueryParams(getRequest());
    }

    /**
     * Computes a response for the entity - and gets us around casting in
     * searchForKeyword().
     */
    protected <T> Response createOkResponse(final T entity, MediaType mediaType) {
        return ResponseEnum.OK.createResponseForEntity(entity, mediaType);
    }

    /**
     * Computes a response for the entity - and gets us around casting in
     * searchForKeyword().
     */
    protected <T> Response createOkResponse(final T entity) {
        return createOkResponse(entity, MediaType.APPLICATION_JSON_TYPE);
    }

    /**
     * Computes a response for the entity - and gets us around casting in
     * searchForKeyword().
     */
    protected <T> Response createCreatedResponse(final T entity, MediaType mediaType) {
        return ResponseEnum.CREATED.createResponseForEntity(entity, mediaType);
    }

    /**
     * Computes a response for the entity - and gets us around casting in
     * searchForKeyword().
     */
    protected <T> Response createCreatedResponse(final T entity) {
        return createCreatedResponse(entity, MediaType.APPLICATION_JSON_TYPE);
    }

    /**
     * Computes a respond for the entity - and gets us around casting in
     * searchForKeyword().
     */
    protected <T> Response createNotFoundResponse() {
        return ResponseEnum.NOT_FOUND.createResponseForEntity();
    }

    /**
     * Computes a respond for the entity - and gets us around casting in
     * searchForKeyword().
     */
    protected <T> Response createInternalServerErrorResponse() {
        return ResponseUtils.createInternalServerErrorResponse();
    }

    /**
     * Computes a respond for the entity - and gets us around casting in
     * searchForKeyword().
     */
    protected <T> Response createBadRequestResponse(final String... message) {
        return ResponseUtils.createBadRequestResponse(StringUtils.join(message));
    }

    /**
     * Computes a respond for the entity - and gets us around casting in
     * searchForKeyword().
     */
    protected <T> Response createBadRequestResponse(final Throwable causedBy) {
        return ResponseUtils.createBadRequestResponse(causedBy);
    }

    /**
     * Return the EJB that performs our work.
     */
    protected S getService() {
        return service;
    }

    /**
     * Set the service to use.
     *
     * @param service the service to use.
     */
    protected void setService(final S service) {
        logIfDebug("Service injected:  [", service, "]");

        this.service = service;
    }
}
