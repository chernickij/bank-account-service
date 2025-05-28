package com.chernickij.bankaccount.sevice.impl;

import com.chernickij.bankaccount.dto.AuthenticationRequest;
import com.chernickij.bankaccount.dto.AuthenticationResponse;
import com.chernickij.bankaccount.security.CustomUserDetails;
import com.chernickij.bankaccount.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    private final String USER_ID = "user_id";

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthenticationResponse authenticate(final AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        final CustomUserDetails user = userDetailsService.loadUserByUsername(request.email());

        final Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put(USER_ID, user.getId());

        var jwtToken = jwtUtil.generateToken(extraClaims, user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
