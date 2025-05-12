package com.bidemy.handler;

import com.bidemy.exception.BusinessValidationException;
import io.micrometer.tracing.Tracer;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final Tracer tracer;

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error("", ex);

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .traceId(tracer.currentSpan().context().traceId())
                .message(ex.getMessage())
                .build();

        if (ex instanceof BusinessValidationException) {
            BusinessValidationException businessException = (BusinessValidationException) ex;
            errorResponse.setCode(businessException.getRule().getCode());
            httpStatus = businessException.getRule().getHttpStatus();
        }
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), httpStatus, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders httpHeaders, HttpStatusCode httpStatusCode, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ErrorResponse errorResponse = ErrorResponse.builder()
                .traceId(tracer.currentSpan().context().traceId())
                .message(errors.toString())
                .build();

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @Getter
    @Setter
    @Builder
    private static class ErrorResponse {
        private String traceId;

        @Builder.Default
        private String code = "DEFAULT";

        private String message;
    }
}
