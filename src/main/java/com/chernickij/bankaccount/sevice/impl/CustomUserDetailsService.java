package com.chernickij.bankaccount.sevice.impl;

import com.chernickij.bankaccount.entity.Email;
import com.chernickij.bankaccount.entity.User;
import com.chernickij.bankaccount.exception.NotFoundException;
import com.chernickij.bankaccount.repository.EmailRepository;
import com.chernickij.bankaccount.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.chernickij.bankaccount.exception.NotFoundException.*;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmailRepository emailRepository;

    @Override
    public CustomUserDetails loadUserByUsername(final String email) {
        final Email emailEntity = emailRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ResourceType.EMAIL, email));

        final User user = emailEntity.getUser();

        return new CustomUserDetails(
                user.getId(),
                emailEntity.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
