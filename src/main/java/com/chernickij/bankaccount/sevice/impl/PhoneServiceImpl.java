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
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {
    public static final String PHONE_CACHE_NAME = "phoneCache";

    private final CacheManager cacheManager;
    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addPhone(final Long userId, final String newPhone) {
        log.info("Adding phone {}", newPhone);
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
                .orElseThrow(() -> new NotFoundException(NotFoundException.ResourceType.USER, userId.toString()));

        final Phone phone = phoneRepository.findByPhone(newPhone)
                .orElseThrow(() -> new NotFoundException(NotFoundException.ResourceType.PHONE, oldPhone));

        if (!phone.getUser().getId().equals(user.getId())) {
            throw new ConflictException(ConflictException.ResourceType.NOT_USER_PHONE, oldPhone);
        }

        if (!phone.getPhone().equals(newPhone) && phoneRepository.findByPhone(newPhone).isPresent()) {
            throw new ConflictException(ConflictException.ResourceType.PHONE, newPhone);
        }

        phone.setPhone(newPhone);
        phoneRepository.save(phone);
        cacheManager.getCache(PHONE_CACHE_NAME).evict(oldPhone);
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
        cacheManager.getCache(PHONE_CACHE_NAME).evict(phoneToDelete);
    }
}
