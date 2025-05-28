package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePhoneRequest(
        @Schema(description = "User old phone number") String oldPhone,
        @Schema(description = "User new phone number") String newPhone) {

}
