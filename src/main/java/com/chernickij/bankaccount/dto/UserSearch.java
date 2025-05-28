package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearch {
    @Schema(description = "User name")
    private String name;
    @Schema(description = "User email address")
    private String email;
    @Schema(description = "User phone number")
    private String phone;
    @Schema(description = "User date of birth")
    private LocalDateTime dateOfBirth;
    @Schema(description = "Page number")
    private int page;
    @Schema(description = "Page size")
    private int size;
}
