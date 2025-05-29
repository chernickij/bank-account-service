package com.chernickij.bankaccount.controller;

import com.chernickij.bankaccount.dto.*;
import com.chernickij.bankaccount.exception.ErrorResponse;
import com.chernickij.bankaccount.sevice.impl.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl service;

    @Operation(summary = "Register new user", responses = {
            @ApiResponse(responseCode = "200", description = "User registered"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid final SignUpRequest request) {
        return service.signUp(request);
    }

    @Operation(summary = "Authentication user", responses = {
            @ApiResponse(responseCode = "200", description = "User authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid final SignInRequest request) {
        return service.signIn(request);
    }
}
