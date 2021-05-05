package customeraccount.services.impl;

import customeraccount.domain.Account;
import customeraccount.domain.TransactionAccount;
import customeraccount.enums.TransactionType;
import customeraccount.exceptions.DataValidationException;
import customeraccount.exceptions.InvalidAmountException;
import customeraccount.repositories.TransactionAccountRepository;
import customeraccount.services.AccountService;
import customeraccount.services.TransactionAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionAccountServiceImplTest {

    @InjectMocks
    TransactionAccountServiceImpl transactionAccountService;

    @Mock
    AccountService accountService;

    @Mock
    TransactionAccountRepository transactionAccountRepository;

    @Captor
    ArgumentCaptor<TransactionAccount> transactionAccountArgumentCaptor;


    @Test
    void givenNullTypeOperation_processTransaction_willThrowException() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.TEN;

        DataValidationException exception = assertThrows(DataValidationException.class,
                () -> transactionAccountService.processTransaction(accountId, amount, null),
                "transactionAccountService.processTransaction must throw exception, but it didn't");
        assertEquals("TransactionType must not be null", exception.getMessage());

        verify(accountService, never()).deposit(any(), any());
        verify(accountService, never()).withdrawal(any(), any());
        verify(transactionAccountRepository, never()).save(any());


    }

    @Test
    void givenNullAccountId_processTransaction_willThrowException() {
        BigDecimal amount = BigDecimal.TEN;

        DataValidationException exception = assertThrows(DataValidationException.class,
                () -> transactionAccountService.processTransaction(null, amount, TransactionType.DEPOSIT),
                "transactionAccountService.processTransaction must throw exception, but it didn't");
        assertEquals("AccountId cannot be null", exception.getMessage());

        verify(accountService, never()).deposit(any(), any());
        verify(accountService, never()).withdrawal(any(), any());
        verify(transactionAccountRepository, never()).save(any());
    }

    @Test
    void givenNullAmount_processTransaction_willThrowException() {
        Long accountId = 1L;
        BigDecimal nullAmount = null;

        DataValidationException exception = assertThrows(DataValidationException.class,
                () -> transactionAccountService.processTransaction(accountId, nullAmount, TransactionType.DEPOSIT),
                "transactionAccountService.processTransaction must throw exception, but it didn't");
        assertEquals("Amount must be > 0", exception.getMessage());

        verify(accountService, never()).deposit(any(), any());
        verify(accountService, never()).withdrawal(any(), any());
        verify(transactionAccountRepository, never()).save(any());
    }

    @Test
    void givenNegativeAmount_processTransaction_willThrowException() {
        Long accountId = 1L;
        BigDecimal negativeAmount = new BigDecimal(-10);

        DataValidationException exception = assertThrows(DataValidationException.class,
                () -> transactionAccountService.processTransaction(accountId, negativeAmount, TransactionType.DEPOSIT),
                "transactionAccountService.processTransaction must throw exception, but it didn't");
        assertEquals("Amount must be > 0", exception.getMessage());

        verify(accountService, never()).deposit(any(), any());
        verify(accountService, never()).withdrawal(any(), any());
        verify(transactionAccountRepository, never()).save(any());
    }

    @Test
    void givenDepositTransaction_processTransaction_willDoDeposit() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.TEN;

        transactionAccountService.processTransaction(accountId, amount, TransactionType.DEPOSIT);

        verify(accountService, times(1)).deposit(accountId, amount);
        verify(transactionAccountRepository, times(1)).save(transactionAccountArgumentCaptor.capture());
        TransactionAccount transactionAccount = transactionAccountArgumentCaptor.getValue();

        assertEquals(accountId, transactionAccount.getAccountId());
        assertEquals(amount, transactionAccount.getAmount());
        assertEquals(TransactionType.DEPOSIT, transactionAccount.getTransactionType());
        assertNotNull(transactionAccount.getDateOperation());
    }

    @Test
    void givenWithdrawalTransaction_processTransaction_willDoWithdrawal() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.TEN;

        transactionAccountService.processTransaction(accountId, amount, TransactionType.WITHDRAWAL);

        verify(accountService, times(1)).withdrawal(accountId, amount);
        verify(transactionAccountRepository, times(1)).save(transactionAccountArgumentCaptor.capture());
        TransactionAccount transactionAccount = transactionAccountArgumentCaptor.getValue();

        assertEquals(accountId, transactionAccount.getAccountId());
        assertEquals(amount, transactionAccount.getAmount());
        assertEquals(TransactionType.WITHDRAWAL, transactionAccount.getTransactionType());
        assertNotNull(transactionAccount.getDateOperation());
    }

}
