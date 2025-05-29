package com.chernickij.bankaccount.repository;

import com.chernickij.bankaccount.entity.Account;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.chernickij.bankaccount.sevice.impl.EmailServiceImpl.EMAIL_CACHE_NAME;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Cacheable(value = EMAIL_CACHE_NAME, key = "#id")
    Optional<Account> findByUserId(final Long id);
}
