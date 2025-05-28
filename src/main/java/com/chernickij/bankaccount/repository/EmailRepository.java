package com.chernickij.bankaccount.repository;

import com.chernickij.bankaccount.entity.Email;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.chernickij.bankaccount.sevice.impl.EmailServiceImpl.EMAIL_CACHE_NAME;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    @Cacheable(value = EMAIL_CACHE_NAME, key = "#value")
    Optional<Email> findByEmail(final String value);
}
