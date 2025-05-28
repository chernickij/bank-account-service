package com.chernickij.bankaccount.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.chernickij.bankaccount.exception.ServiceException.ErrorType.UNEXPECTED_TECHNICAL_ERROR;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> handleBaseException(final BaseException ex) {
        log.error("Following exception occurred: ", ex);
        return ResponseEntity.status(ex.getStatusCode())
                .body(ErrorResponse.builder()
                .httpStatus(ex.getStatusCode().toString())
                .code(ex.getCode())
                .message(ex.getMessage())
                .build());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorResponse> handleException(final Exception ex) {
        log.error("Internal Server Error: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .code(UNEXPECTED_TECHNICAL_ERROR.getCode())
                        .message(ex.getMessage())
                        .build());
    }
}
