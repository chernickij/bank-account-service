package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record UpdatePhoneRequest(
        @Schema(description = "User old phone number")
        @Pattern(regexp = "^7\\d{10}$")
        String oldPhone,
        @Schema(description = "User new phone number")
        @Pattern(regexp = "^7\\d{10}$")
        String newPhone) {

}
