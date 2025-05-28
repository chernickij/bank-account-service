package com.chernickij.bankaccount.sevice;

public interface PhoneService {
    void addPhone(final Long userId, final String newPhone);

    void updatePhone(final Long userId, final String oldPhone, final String newPhone);

    void deletePhone(final Long userId, final String phone);
}
