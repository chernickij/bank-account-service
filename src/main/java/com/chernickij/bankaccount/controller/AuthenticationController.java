package com.chernickij.bankaccount.controller;

import com.chernickij.bankaccount.dto.AuthenticationRequest;
import com.chernickij.bankaccount.dto.AuthenticationResponse;
import com.chernickij.bankaccount.sevice.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl service;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody final AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
