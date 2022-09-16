package com.charles.zodrac.exception;

public class CustomException extends RuntimeException {

    public CustomException(String errorMessage) {
        super(errorMessage);
    }
}
