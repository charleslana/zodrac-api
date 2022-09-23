package com.charles.zodrac.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class BusinessRuleException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String status;

    public BusinessRuleException(String status, String message) {
        super(message);
        this.status = status;
    }
}
