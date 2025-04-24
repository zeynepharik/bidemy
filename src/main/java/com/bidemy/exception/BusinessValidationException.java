package com.bidemy.exception;

import lombok.Getter;

@Getter
public class BusinessValidationException extends RuntimeException {
    private final IBusinessValidationRule rule;

    public BusinessValidationException(IBusinessValidationRule rule) {
        super(rule.getMessage());
        this.rule=rule;
    }

    public BusinessValidationException(IBusinessValidationRule rule, Object... params){
        super(String.format(rule.getMessage(),params));
        this.rule=rule;
    }
}
