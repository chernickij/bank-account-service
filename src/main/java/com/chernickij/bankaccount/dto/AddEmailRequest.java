package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AddEmailRequest(
        @Schema(description = "User email address") String email) {

}
