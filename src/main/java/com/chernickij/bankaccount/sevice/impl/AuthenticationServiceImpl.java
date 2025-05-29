package com.chernickij.bankaccount.sevice.impl;

import com.chernickij.bankaccount.dto.*;
import com.chernickij.bankaccount.entity.Email;
import com.chernickij.bankaccount.entity.Role;
import com.chernickij.bankaccount.entity.User;
import com.chernickij.bankaccount.repository.EmailRepository;
import com.chernickij.bankaccount.repository.UserRepository;
import com.chernickij.bankaccount.security.JwtUtil;
import com.chernickij.bankaccount.sevice.AuthenticationService;
import com.chernickij.bankaccount.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        Email email = new Email(request.getEmail());


        var user = User.builder()
                .name(request.getName())
                .dateOfBirth(request.getDateOfBirth())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        email.setUser(user);
        emailRepository.save(email);


        var jwt = jwtUtil.generateToken(user, request.getEmail());
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());

        var jwt = jwtUtil.generateToken(user, request.getEmail());
        return new JwtAuthenticationResponse(jwt);
    }
}
