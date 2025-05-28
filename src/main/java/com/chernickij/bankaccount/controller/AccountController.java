package com.chernickij.bankaccount.controller;

import com.chernickij.bankaccount.dto.TransferRequest;
import com.chernickij.bankaccount.exception.ErrorResponse;
import com.chernickij.bankaccount.security.CustomUserDetails;
import com.chernickij.bankaccount.sevice.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "User Account")
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "Transfer money to another user", responses = {
            @ApiResponse(responseCode = "202", description = "Money transferred"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Accounts not found", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not enough money to transfer", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/transfer")
    public ResponseEntity<Void> transferMoney(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              @RequestBody TransferRequest request) {
        accountService.transfer(userDetails.getId(), request);
        return ResponseEntity.accepted().build();
    }
}
