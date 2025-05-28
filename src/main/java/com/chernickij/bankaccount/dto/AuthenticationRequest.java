package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthenticationRequest(@Schema(description = "User email address") String email,
                                    @Schema(description = "User password") String password) {

}
