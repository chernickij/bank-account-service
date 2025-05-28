package com.chernickij.bankaccount.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String httpStatus;
    private String code;
    private String message;
}
