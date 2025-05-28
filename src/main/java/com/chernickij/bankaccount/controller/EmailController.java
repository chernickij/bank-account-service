package com.chernickij.bankaccount.controller;

import com.chernickij.bankaccount.dto.AddEmailRequest;
import com.chernickij.bankaccount.dto.DeleteEmailRequest;
import com.chernickij.bankaccount.dto.UpdateEmailRequest;
import com.chernickij.bankaccount.entity.Email;
import com.chernickij.bankaccount.security.CustomUserDetails;
import com.chernickij.bankaccount.sevice.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> addEmail(@AuthenticationPrincipal final CustomUserDetails userDetails,
                                          @RequestBody final AddEmailRequest request) {
        emailService.addEmail(userDetails.getId(), request.email());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping
    public ResponseEntity<Email> updateEmail(@AuthenticationPrincipal final CustomUserDetails userDetails,
                                             @RequestBody final UpdateEmailRequest request) {
        emailService.updateEmail(userDetails.getId(), request.oldEmail(), request.newEmail());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEmail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @RequestBody final DeleteEmailRequest request) {
        emailService.deleteEmail(userDetails.getId(), request.email());
        return ResponseEntity.noContent().build();
    }
}
