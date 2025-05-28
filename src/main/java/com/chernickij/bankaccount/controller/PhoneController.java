package com.chernickij.bankaccount.controller;

import com.chernickij.bankaccount.dto.*;
import com.chernickij.bankaccount.exception.ErrorResponse;
import com.chernickij.bankaccount.security.CustomUserDetails;
import com.chernickij.bankaccount.sevice.PhoneService;
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
@RequestMapping("/users/phone")
@RequiredArgsConstructor
@Tag(name = "Phone number")
public class PhoneController {
    private final PhoneService phoneService;

    @Operation(summary = "Add a new phone number to the user", responses = {
            @ApiResponse(responseCode = "201", description = "Phone number added"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Phone number is already exists for other user", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Void> addPhone(@AuthenticationPrincipal final CustomUserDetails userDetails,
                                         @RequestBody final AddPhoneRequest request) {
        phoneService.addPhone(userDetails.getId(), request.phone());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update user phone number", responses = {
            @ApiResponse(responseCode = "202", description = "Phone number added"),
            @ApiResponse(responseCode = "404", description = "User or Phone number not found", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "New/Old phone number belongs to another user", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping
    public ResponseEntity<Void> updatePhone(@AuthenticationPrincipal final CustomUserDetails userDetails,
                                             @RequestBody final UpdatePhoneRequest request) {
        phoneService.updatePhone(userDetails.getId(), request.oldPhone(), request.newPhone());
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Delete user phone number", responses = {
            @ApiResponse(responseCode = "202", description = "Phone number deleted"),
            @ApiResponse(responseCode = "404", description = "User or Phone number not found", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Phone number belongs to another user", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping
    public ResponseEntity<Void> deletePhone(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @RequestBody final DeletePhoneRequest request) {
        phoneService.deletePhone(userDetails.getId(), request.phone());
        return ResponseEntity.accepted().build();
    }
}
