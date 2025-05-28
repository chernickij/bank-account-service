package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateEmailRequest(
        @Schema(description = "Old user email address") String oldEmail,
        @Schema(description = "New user email address") String newEmail) {

}
