package com.chernickij.bankaccount.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ServiceException extends BaseException{
    private ErrorType errorType;

    public ServiceException(final String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.UNEXPECTED_TECHNICAL_ERROR.getCode(), message);
        this.errorType = ErrorType.UNEXPECTED_TECHNICAL_ERROR;
    }

    public ServiceException(final String message, final Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.UNEXPECTED_TECHNICAL_ERROR.getCode(), message, cause);
        this.errorType = ErrorType.UNEXPECTED_TECHNICAL_ERROR;
    }

    public ServiceException(final ErrorType errorType, final String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorType.getCode(), errorType.getMessage() + ". " + message);
        this.errorType = ErrorType.UNEXPECTED_TECHNICAL_ERROR;
    }

    public ServiceException(final ErrorType errorType, final String message, final Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorType.getCode(), errorType.getMessage() + ". " + message, cause);
        this.errorType = ErrorType.UNEXPECTED_TECHNICAL_ERROR;
    }

    @Getter
    public enum ErrorType {
        UNEXPECTED_TECHNICAL_ERROR("U-500-00", "Internal Server Error ");
        // todo others

        private String code;
        private String message;

        ErrorType(final String code, final String message) {
            this.code = code;
            this.message = message;
        }
    }
}
