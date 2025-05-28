package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record UpdateUserRequest(@Schema(description = "User name") String name,
                                @Schema(description = "Date of birth") LocalDate dateOfBirth) {

}
