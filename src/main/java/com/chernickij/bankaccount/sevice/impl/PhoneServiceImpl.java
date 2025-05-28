package com.chernickij.bankaccount.sevice.impl;

import com.chernickij.bankaccount.entity.Phone;
import com.chernickij.bankaccount.entity.User;
import com.chernickij.bankaccount.exception.ConflictException;
import com.chernickij.bankaccount.exception.NotFoundException;
import com.chernickij.bankaccount.exception.NotFoundException.ResourceType;
import com.chernickij.bankaccount.repository.PhoneRepository;
import com.chernickij.bankaccount.repository.UserRepository;
import com.chernickij.bankaccount.sevice.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addPhone(final Long userId, final String newPhone){
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ResourceType.USER, userId.toString()));

        if (phoneRepository.findByPhone(newPhone).isPresent()) {
            throw new ConflictException(ConflictException.ResourceType.PHONE, newPhone);
        }

        final Phone phone = new Phone();
        phone.setPhone(newPhone);
        phone.setUser(user);
        phoneRepository.save(phone);
    }

    @Override
    @Transactional
    public void updatePhone(final Long userId, final String oldPhone, final String newPhone) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ResourceType.USER, userId.toString()));

        final Phone phone = phoneRepository.findByPhone(newPhone)
                .orElseThrow(() -> new NotFoundException(ResourceType.PHONE, oldPhone));

        if (!phone.getUser().getId().equals(user.getId())) {
            throw new NotFoundException(ResourceType.PHONE, oldPhone);
        }

        if (!phone.getPhone().equals(newPhone) && phoneRepository.findByPhone(newPhone).isPresent()) {
            throw new ConflictException(ConflictException.ResourceType.NOT_USER_PHONE, newPhone);
        }

        phone.setPhone(newPhone);
        phoneRepository.save(phone);
    }

    @Override
    @Transactional
    public void deletePhone(final Long userId, final String phoneToDelete) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ResourceType.USER, userId.toString()));

        final Phone phone = phoneRepository.findByPhone(phoneToDelete)
                .orElseThrow(() -> new NotFoundException(ResourceType.PHONE, phoneToDelete));

        if (!phone.getUser().getId().equals(user.getId())) {
            throw new ConflictException(ConflictException.ResourceType.NOT_USER_PHONE, phoneToDelete);
        }

        phoneRepository.delete(phone);
    }
}
