package com.chernickij.bankaccount.sevice.scheduler;

import com.chernickij.bankaccount.entity.Account;
import com.chernickij.bankaccount.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceChecker {
    private final AccountRepository accountRepository;

    @Scheduled(fixedRateString = "${app.balance-checker.fixed-rate}")
    public void updateBalances() {
        log.info("Updating account balances");
        final List<Account> accounts = accountRepository.findAll();

        accounts.forEach(account -> {
            BigDecimal newBalance = account.getBalance().multiply(BigDecimal.valueOf(1.10)); // Увеличиваем на 10%

            final BigDecimal maxBalance = account.getInitialDeposit().multiply(BigDecimal.valueOf(2.07));
            if (newBalance.compareTo(maxBalance) > 0) {
                newBalance = maxBalance;
            }

            account.setBalance(newBalance);
        });
        accountRepository.saveAll(accounts);
        log.info("Updated account balances. Count of updated accounts: {}", accounts.size());
    }
}
