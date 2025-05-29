package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {
    @Schema(description = "User id")
    private String id;
    @Schema(description = "User name")
    private String name;
    @Schema(description = "User date od birth")
    private LocalDate dateOfBirth;
}
