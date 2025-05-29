package com.chernickij.bankaccount.service;

import com.chernickij.bankaccount.dto.TransferRequest;
import com.chernickij.bankaccount.entity.Account;
import com.chernickij.bankaccount.exception.BadRequestException;
import com.chernickij.bankaccount.exception.ConflictException;
import com.chernickij.bankaccount.exception.NotFoundException;
import com.chernickij.bankaccount.repository.AccountRepository;
import com.chernickij.bankaccount.sevice.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTransfer_Success() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal transferAmount = BigDecimal.valueOf(100);

        Account fromAccount = new Account();
        fromAccount.setId(fromUserId);
        fromAccount.setBalance(BigDecimal.valueOf(200));

        Account toAccount = new Account();
        toAccount.setId(toUserId);
        toAccount.setBalance(BigDecimal.valueOf(50));

        when(accountRepository.findByUserId(fromUserId)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUserId(toUserId)).thenReturn(Optional.of(toAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(fromAccount).thenReturn(toAccount);

        TransferRequest request = new TransferRequest(toUserId, transferAmount);

        accountService.transfer(fromUserId, request);

        Assertions.assertEquals(BigDecimal.valueOf(100), fromAccount.getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(150), toAccount.getBalance());
        verify(accountRepository, times(1)).save(fromAccount);
        verify(accountRepository, times(1)).save(toAccount);
    }

    @Test
    void testTransfer_NotEnoughMoney() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal transferAmount = BigDecimal.valueOf(300);

        Account fromAccount = new Account();
        fromAccount.setId(fromUserId);
        fromAccount.setBalance(BigDecimal.valueOf(200));

        Account toAccount = new Account();
        toAccount.setId(toUserId);
        toAccount.setBalance(BigDecimal.valueOf(50));

        when(accountRepository.findByUserId(fromUserId)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUserId(toUserId)).thenReturn(Optional.of(toAccount));

        TransferRequest request = new TransferRequest(toUserId, transferAmount);

        ConflictException thrown = Assertions.assertThrows(ConflictException.class, () -> {
            accountService.transfer(fromUserId, request);
        });

        Assertions.assertEquals("NOT_ENOUGH_MONEY", thrown.getResourceType().name());
        Assertions.assertEquals(fromUserId.toString(), thrown.getResourceId());
    }

    @Test
    void testTransfer_UserNotFound() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal transferAmount = BigDecimal.valueOf(100);

        when(accountRepository.findByUserId(fromUserId)).thenReturn(Optional.empty());

        TransferRequest request = new TransferRequest(toUserId, transferAmount);

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            accountService.transfer(fromUserId, request);
        });

        Assertions.assertEquals("ACCOUNT", thrown.getResourceType().name());
        Assertions.assertEquals(fromUserId.toString(), thrown.getResourceId());
    }

    @Test
    void testTransfer_BadRequest_NullUserId() {
        Long fromUserId = 1L;
        TransferRequest request = new TransferRequest(null, BigDecimal.valueOf(100));

        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> {
            accountService.transfer(fromUserId, request);
        });

        Assertions.assertEquals(BadRequestException.ResourceType.USER_ID, thrown.getResourceType());
    }

    @Test
    void testTransfer_BadRequest_NullAmount() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        TransferRequest request = new TransferRequest(toUserId, null);

        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> {
            accountService.transfer(fromUserId, request);
        });

        Assertions.assertEquals(BadRequestException.ResourceType.AMOUNT, thrown.getResourceType());
    }

    @Test
    void testTransfer_BadRequest_ZeroOrNegativeAmount() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        TransferRequest request = new TransferRequest(toUserId, BigDecimal.ZERO);

        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> {
            accountService.transfer(fromUserId, request);
        });

        Assertions.assertEquals(BadRequestException.ResourceType.AMOUNT, thrown.getResourceType());
    }
}
