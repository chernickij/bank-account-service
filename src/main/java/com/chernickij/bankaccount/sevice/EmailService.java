package com.chernickij.bankaccount.sevice;

public interface EmailService {

    void addEmail(final Long userId, final String newEmail);

    void updateEmail(final Long userId, final String oldEmail, final String newEmail);

    void deleteEmail(final Long userId, final String email);
}
