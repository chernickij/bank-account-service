package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {
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
