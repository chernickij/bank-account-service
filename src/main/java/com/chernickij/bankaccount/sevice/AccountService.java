package com.chernickij.bankaccount.sevice;

import com.chernickij.bankaccount.dto.TransferRequest;

public interface AccountService {

    void transfer(final Long userId, final TransferRequest request);
}
