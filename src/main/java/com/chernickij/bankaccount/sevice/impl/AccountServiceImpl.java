package com.chernickij.bankaccount.sevice.impl;

import com.chernickij.bankaccount.dto.TransferRequest;
import com.chernickij.bankaccount.entity.Account;
import com.chernickij.bankaccount.exception.BadRequestException;
import com.chernickij.bankaccount.exception.ConflictException;
import com.chernickij.bankaccount.exception.NotFoundException;
import com.chernickij.bankaccount.repository.AccountRepository;
import com.chernickij.bankaccount.sevice.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void transfer(final Long fromUserId, final TransferRequest request) {
        log.info("Transfer {} from {} to {}", request.amount(), fromUserId, request.userId());
        final Long toUserId = request.userId();
        final BigDecimal amount = request.amount();

        if (toUserId == null) {
            throw new BadRequestException(BadRequestException.ResourceType.USER_ID);
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException(BadRequestException.ResourceType.AMOUNT);
        }

        final Account fromAccount = accountRepository.findByUserId(fromUserId)
                .orElseThrow(() -> new NotFoundException(NotFoundException.ResourceType.ACCOUNT, fromUserId.toString()));
        final Account toAccount = accountRepository.findByUserId(toUserId)
                .orElseThrow(() -> new NotFoundException(NotFoundException.ResourceType.ACCOUNT, toUserId.toString()));

        if (fromAccount.getBalance().compareTo(amount) >= 0) {
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
            log.info("Transfer from {} to {} was success.", fromUserId, toUserId);
        } else {
            log.error("Transfer from {} to {} failed. Not enough money on account with user id {}", fromUserId, toUserId, fromUserId);
            throw new ConflictException(ConflictException.ResourceType.NOT_ENOUGH_MONEY, fromUserId.toString());
        }
    }
}
