package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserRequest(@Schema(description = "User name")
                                @Size(max = 500, message = "Name must contain up to 500 characters")
                                String name,

                                @Schema(description = "Date of birth")
                                @Past(message = "Date of birth must be in the past")
                                LocalDate dateOfBirth) {

}
