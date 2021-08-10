package com.redhat.common.jee.rest.proxy;

import com.redhat.common.AbstractBase;
import com.redhat.common.jee.servlet.utils.HttpServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.Map;

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
     * Return the service that performs our work.
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
