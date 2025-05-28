package com.chernickij.bankaccount.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public class BadRequestException extends BaseException {

    private final ResourceType resourceType;
    private final String resourceId;

    public BadRequestException(final ResourceType resourceType) {
        super(BAD_REQUEST, resourceType.getCode(), resourceType.getMessage());
        this.resourceType = resourceType;
        this.resourceId = "";
    }

    public BadRequestException(final ResourceType resourceType, final String resourceId) {
        super(BAD_REQUEST, resourceType.getCode(), resourceType.getMessage() + resourceId);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    @Getter
    @RequiredArgsConstructor
    public enum ResourceType {
        USER_ID("U-400-01", "User id cannot be null."),
        AMOUNT("U-400-02", "Amount of money cannot be null.");

        private final String code;
        private final String message;
    }
}
