package com.yieldlab.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.concurrent.CompletionException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.class, HttpServerErrorException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {

        if (ex instanceof HttpClientErrorException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            HttpClientErrorException httpClientErrorException = (HttpClientErrorException) ex;

            return handleRestException(httpClientErrorException, status, request);
        } else if (ex instanceof HttpServerErrorException) {
            HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
            HttpServerErrorException httpServerErrorException = (HttpServerErrorException) ex;

            return handleRestException(httpServerErrorException, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, status, request);
        }
    }

    @ExceptionHandler({ResourceAccessException.class, CompletionException.class})
    public final ResponseEntity<ApiError> handleResourceAccess(Exception ex, WebRequest request) {

        if (ex instanceof ResourceAccessException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ResourceAccessException httpClientErrorException = (ResourceAccessException) ex;

            return handleRestException(httpClientErrorException, status, request);
        } else if (ex instanceof CompletionException) {
            CompletionException completionException = (CompletionException) ex;
            HttpStatus status = HttpStatus.BAD_REQUEST;

            return handleCompletionException(completionException, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, status, request);
        }
    }

    protected ResponseEntity<ApiError> handleRestException(RestClientException ex, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new ApiError(status, ex.getMessage(), Collections.emptyList()), status, request);
    }

    protected ResponseEntity<ApiError> handleCompletionException(CompletionException ex, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, new ApiError(status, ex.getMessage(), Collections.emptyList()), status, request);
    }

    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, status);
    }
}
