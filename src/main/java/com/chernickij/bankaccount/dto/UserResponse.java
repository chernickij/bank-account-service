package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @Schema(description = "User ID")
    private Long id;
    @Schema(description = "User name")
    private String name;
    @Schema(description = "User date of birth")
    private LocalDate dateOfBirth;
    @Schema(description = "User email address list")
    private List<String> emails;
    @Schema(description = "User phone number list")
    private List<String> phones;
}
