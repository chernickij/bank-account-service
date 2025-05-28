package com.chernickij.bankaccount.controller;

import com.chernickij.bankaccount.dto.AddEmailRequest;
import com.chernickij.bankaccount.dto.DeleteEmailRequest;
import com.chernickij.bankaccount.dto.UpdateEmailRequest;
import com.chernickij.bankaccount.entity.Email;
import com.chernickij.bankaccount.exception.ErrorResponse;
import com.chernickij.bankaccount.security.CustomUserDetails;
import com.chernickij.bankaccount.sevice.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users/email")
@RequiredArgsConstructor
@Tag(name = "Email address")
public class EmailController {
    private final EmailService emailService;

    @Operation(summary = "Add a new email address to the user", responses = {
            @ApiResponse(responseCode = "201", description = "Email added"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email is already exists for other user", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Void> addEmail(@AuthenticationPrincipal final CustomUserDetails userDetails,
                                         @RequestBody final AddEmailRequest request) {
        emailService.addEmail(userDetails.getId(), request.email());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update user email", responses = {
            @ApiResponse(responseCode = "202", description = "Email added"),
            @ApiResponse(responseCode = "404", description = "User or Email not found", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "New/Old email belongs to another user", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping
    public ResponseEntity<Void> updateEmail(@AuthenticationPrincipal final CustomUserDetails userDetails,
                                             @RequestBody final UpdateEmailRequest request) {
        emailService.updateEmail(userDetails.getId(), request.oldEmail(), request.newEmail());
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Delete user email", responses = {
            @ApiResponse(responseCode = "202", description = "Email deleted"),
            @ApiResponse(responseCode = "404", description = "User or Email not found", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email belongs to another user", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteEmail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @RequestBody final DeleteEmailRequest request) {
        emailService.deleteEmail(userDetails.getId(), request.email());
        return ResponseEntity.accepted().build();
    }
}
