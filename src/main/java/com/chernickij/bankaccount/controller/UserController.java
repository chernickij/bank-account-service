package com.chernickij.bankaccount.controller;

import com.chernickij.bankaccount.dto.UpdateUserRequest;
import com.chernickij.bankaccount.dto.UserResponse;
import com.chernickij.bankaccount.dto.UserSearch;
import com.chernickij.bankaccount.exception.ErrorResponse;
import com.chernickij.bankaccount.security.CustomUserDetails;
import com.chernickij.bankaccount.sevice.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Data")
public class UserController {
    private final UserServiceImpl userService;

    @Operation(summary = "Search users by specified criteria", responses = {
            @ApiResponse(responseCode = "200", description = "Return users data",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public Page<UserResponse> searchUsers(
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String email,
            @RequestParam(required = false) final String phone,
            @RequestParam(required = false) final LocalDateTime dateOfBirth,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size) {
        final UserSearch criteria = new UserSearch(name, email, phone, dateOfBirth, page, size);
        return userService.searchUsers(criteria);
    }

    @Operation(summary = "Get user data", responses = {
            @ApiResponse(responseCode = "200", description = "Return user data",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable final Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @Operation(summary = "Update user", responses = {
            @ApiResponse(responseCode = "200", description = "Updated user data",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal final CustomUserDetails userDetails,
                                                   @RequestBody final UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(userDetails.getId(), request));
    }
}
