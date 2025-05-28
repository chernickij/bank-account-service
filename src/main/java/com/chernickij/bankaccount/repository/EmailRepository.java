package com.chernickij.bankaccount.repository;

import com.chernickij.bankaccount.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    Optional<Email> findByEmail(final String email);
}
