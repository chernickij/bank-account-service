package com.chernickij.bankaccount.controller;

import com.chernickij.bankaccount.dto.*;
import com.chernickij.bankaccount.entity.Email;
import com.chernickij.bankaccount.security.CustomUserDetails;
import com.chernickij.bankaccount.sevice.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/phone")
@RequiredArgsConstructor
public class PhoneController {
    private final PhoneService phoneService;

    @PostMapping
    public ResponseEntity<Void> addEmail(@AuthenticationPrincipal final CustomUserDetails userDetails,
                                         @RequestBody final AddPhoneRequest request) {
        phoneService.addPhone(userDetails.getId(), request.phone());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping
    public ResponseEntity<Email> updateEmail(@AuthenticationPrincipal final CustomUserDetails userDetails,
                                             @RequestBody final UpdatePhoneRequest request) {
        phoneService.updatePhone(userDetails.getId(), request.oldPhone(), request.newPhone());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEmail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @RequestBody final DeletePhoneRequest request) {
        phoneService.deletePhone(userDetails.getId(), request.phone());
        return ResponseEntity.noContent().build();
    }
}
