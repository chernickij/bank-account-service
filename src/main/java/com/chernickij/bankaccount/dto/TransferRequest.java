package com.chernickij.bankaccount.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record TransferRequest(@Schema(description = "User id to whom the money will be transferred") Long userId,
                              @Schema(description = "Amount of money") BigDecimal amount) {

}
