package com.chernickij.bankaccount.repository;

import com.chernickij.bankaccount.entity.Phone;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.chernickij.bankaccount.sevice.impl.PhoneServiceImpl.PHONE_CACHE_NAME;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    @Cacheable(value = PHONE_CACHE_NAME, key = "#value")
    Optional<Phone> findByPhone(final String value);
}
