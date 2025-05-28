package com.chernickij.bankaccount.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public class NotFoundException extends BaseException {

    private final ResourceType resourceType;
    private final String resourceId;

    public NotFoundException(final ResourceType resourceType) {
        super(NOT_FOUND, resourceType.getCode(), resourceType.getMessage());
        this.resourceType = resourceType;
        this.resourceId = "";
    }

    public NotFoundException(final ResourceType resourceType, final String resourceId) {
        super(NOT_FOUND, resourceType.getCode(), resourceType.getMessage() + resourceId);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    @Getter
    @RequiredArgsConstructor
    public enum ResourceType {
        USER("U-404-01", "User not found: "),
        EMAIL("U-404-02", "Email not found: ");

        private final String code;
        private final String message;
    }
}
