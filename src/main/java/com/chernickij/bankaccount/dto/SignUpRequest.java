package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "User name")
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 500, message = "Name must contain up to 500 characters")
    private String name;

    @Schema(description = "User date of birth")
    @Past(message = "Date of birth must be in the past")
    @NotNull
    private LocalDate dateOfBirth;

    @Schema(description = "Email address")
    @Size(max = 200, message = "Email must contain up to 200 characters")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be user@example.com format")
    private String email;

    @Schema(description = "Password")
    @Size(min = 5, max = 500, message = "Password must contain up from 5 to 500 characters")
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
