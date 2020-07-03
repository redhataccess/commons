package com.redhat.common.jee.rest.utils;

import com.redhat.common.utils.HttpStatusEnum;
import java.util.function.UnaryOperator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 *
 * @author sfloess
 */
public enum ResponseEnum {
    OK(HttpStatusEnum.OK),
    CREATED(HttpStatusEnum.CREATED),
    ACCEPTED(HttpStatusEnum.ACCEPTED),
    NO_CONTENT(HttpStatusEnum.NO_CONTENT),
    MOVED_PERMANENTLY(HttpStatusEnum.MOVED_PERMANENTLY),
    SEE_OTHER(HttpStatusEnum.SEE_OTHER),
    NOT_MODIFIED(HttpStatusEnum.NOT_MODIFIED),
    TEMPORARY_REDIRECT(HttpStatusEnum.TEMPORARY_REDIRECT),
    BAD_REQUEST(HttpStatusEnum.BAD_REQUEST),
    UNAUTHORIZED(HttpStatusEnum.UNAUTHORIZED),
    FORBIDDEN(HttpStatusEnum.FORBIDDEN),
    NOT_FOUND(HttpStatusEnum.NOT_FOUND),
    NOT_ACCEPTABLE(HttpStatusEnum.NOT_ACCEPTABLE),
    CONFLICT(HttpStatusEnum.CONFLICT),
    GONE(HttpStatusEnum.GONE),
    PRECONDITION_FAILED(HttpStatusEnum.PRECONDITION_FAILED),
    UNSUPPORTED_MEDIA_TYPE(HttpStatusEnum.UNSUPPORTED_MEDIA_TYPE),
    INTERNAL_SERVER_ERROR(HttpStatusEnum.INTERNAL_SERVER_ERROR),
    BAD_GATEWAY(HttpStatusEnum.BAD_GATEWAY),
    SERVICE_UNAVAILABLE(HttpStatusEnum.SERVICE_UNAVAILABLE);

    private final HttpStatusEnum httpStatus;

    private ResponseEnum(final HttpStatusEnum httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatusEnum getHttpStatus() {
        return httpStatus;
    }

    public <U extends UnaryOperator<ResponseBuilder>> Response createResponse(final U unary) {
        return unary.apply(Response.status(getHttpStatus().getStatusCode())).build();
    }

    public Response createResponse() {
        return createResponse(u -> u);
    }
}
