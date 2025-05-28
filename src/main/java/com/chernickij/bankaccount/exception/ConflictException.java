package com.chernickij.bankaccount.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.CONFLICT;

@Getter
public class ConflictException extends BaseException {

    private final ResourceType resourceType;
    private final String resourceId;

    public ConflictException(final ResourceType resourceType, final String resourceId) {
        super(CONFLICT, resourceType.getCode(), resourceType.getMessage() + resourceId);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    @Getter
    @RequiredArgsConstructor
    public enum ResourceType {
        EMAIL("U-409-01", "Email is already in use: "),
        PHONE("U-409-02", "Phone is already in use: "),
        NOT_USER_EMAIL("U-409-03", "Email belongs to another user: "),
        NOT_USER_PHONE("U-409-04", "Phone number belongs to another user: ");

        private final String code;
        private final String message;
    }
}
