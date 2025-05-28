package com.chernickij.bankaccount.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class BaseException extends ResponseStatusException {

    private final String code;
    private final String message;

    public BaseException(final HttpStatus status, final String code, final String message) {
        super(status);
        this.code = code;
        this.message =  message;
    }

    public BaseException(final HttpStatus status, final String code, final String message, final Throwable cause) {
        super(status, message, cause);
        this.code = code;
        this.message =  message;
    }
}
