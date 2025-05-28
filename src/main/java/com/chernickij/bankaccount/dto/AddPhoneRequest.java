package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AddPhoneRequest(
        @Schema(description = "User phone number") String phone) {

}
