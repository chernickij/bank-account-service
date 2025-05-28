package com.chernickij.bankaccount.sevice.impl;

import com.chernickij.bankaccount.entity.Email;
import com.chernickij.bankaccount.entity.User;
import com.chernickij.bankaccount.exception.ConflictException;
import com.chernickij.bankaccount.exception.NotFoundException;
import com.chernickij.bankaccount.exception.NotFoundException.ResourceType;
import com.chernickij.bankaccount.repository.EmailRepository;
import com.chernickij.bankaccount.repository.UserRepository;
import com.chernickij.bankaccount.sevice.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addEmail(final Long userId, final String newEmail){
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ResourceType.USER, userId.toString()));

        if (emailRepository.findByEmail(newEmail).isPresent()) {
            throw new ConflictException(ConflictException.ResourceType.EMAIL, newEmail);
        }

        final Email email = new Email();
        email.setEmail(newEmail);
        email.setUser(user);
        emailRepository.save(email);
    }

    @Override
    @Transactional
    public void updateEmail(final Long userId, final String oldEmail, final String newEmail) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ResourceType.USER, userId.toString()));

        final Email email = emailRepository.findByEmail(oldEmail)
                .orElseThrow(() -> new NotFoundException(ResourceType.EMAIL, oldEmail));

        if (!email.getUser().getId().equals(user.getId())) {
            throw new NotFoundException(ResourceType.EMAIL, oldEmail);
        }

        if (!email.getEmail().equals(newEmail) && emailRepository.findByEmail(newEmail).isPresent()) {
            throw new ConflictException(ConflictException.ResourceType.NOT_USER_EMAIL, newEmail);
        }

        email.setEmail(newEmail);
        emailRepository.save(email);
    }

    @Override
    @Transactional
    public void deleteEmail(final Long userId, final String emailToDelete) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ResourceType.USER, userId.toString()));

        final Email email = emailRepository.findByEmail(emailToDelete)
                .orElseThrow(() -> new NotFoundException(ResourceType.EMAIL, emailToDelete));

        if (!email.getUser().getId().equals(user.getId())) {
            throw new ConflictException(ConflictException.ResourceType.NOT_USER_EMAIL, emailToDelete);
        }

        emailRepository.delete(email);
    }
}
