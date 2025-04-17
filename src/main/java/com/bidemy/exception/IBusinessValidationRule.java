package com.bidemy.exception;

import org.springframework.http.HttpStatus;

public interface IBusinessValidationRule {
    String getCode();

    String getMessage();

    HttpStatus getHttpStatus();
}
