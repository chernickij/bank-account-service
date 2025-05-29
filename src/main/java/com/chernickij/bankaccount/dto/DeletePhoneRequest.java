package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record DeletePhoneRequest(
        @Schema(description = "User phone number")
        @Pattern(regexp = "^7\\d{10}$")
        String phone) {

}
