package customeraccount.services.impl;

import customeraccount.domain.Account;
import customeraccount.exceptions.InvalidAmountException;
import customeraccount.repositories.AccountRepository;
import customeraccount.services.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountRepository accountRepository;

    @Captor
    ArgumentCaptor<Account> accountCaptor;

    @Test
    void givenNullAmount_depositAccount_willThrowException() {
        Long accountId = 1L;
        BigDecimal nullAmount = null;

        InvalidAmountException exception = assertThrows(InvalidAmountException.class,
                () -> accountService.deposit(accountId, nullAmount),
                "accountService.deposit must throw exception, but it didn't");
        assertEquals("Amount must be greater that 0, amount: " + nullAmount, exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void givenNegativeAmount_depositAccount_willThrowException() {
        Long accountId = 1L;
        BigDecimal negativeAmount = new BigDecimal(-50);

        InvalidAmountException exception = assertThrows(InvalidAmountException.class,
                () -> accountService.deposit(accountId, negativeAmount),
                "accountService.deposit must throw exception, but it didn't");
        assertEquals("Amount must be greater that 0, amount: " + negativeAmount, exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void givenValidAmount_depositAccount_willAddAmountToBalance() {
        Long accountId = 1L;
        BigDecimal validAmount = new BigDecimal(50);

        Account account = new Account();
        account.setAccountId(accountId);
        account.setBalance(BigDecimal.valueOf(20));
        willReturn(account).given(accountRepository).findById(accountId);

        accountService.deposit(accountId, validAmount);

        verify(accountRepository, times(1)).save(accountCaptor.capture());

        Account newAccount = accountCaptor.getValue();
        assertEquals(new BigDecimal(70), newAccount.getBalance());
    }


    @Test
    void givenNullAmount_withdrawalAccount_willThrowException() {
        Long accountId = 1L;
        BigDecimal nullAmount = null;

        InvalidAmountException exception = assertThrows(InvalidAmountException.class,
                () -> accountService.withdrawal(accountId, nullAmount),
                "accountService.withdrawal must throw exception, but it didn't");
        assertEquals("Amount must be greater that 0, amount: " + nullAmount, exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void givenNegativeAmount_withdrawalAccount_willThrowException() {
        Long accountId = 1L;
        BigDecimal negativeAmount = new BigDecimal(-50);

        InvalidAmountException exception = assertThrows(InvalidAmountException.class,
                () -> accountService.withdrawal(accountId, negativeAmount),
                "accountService.withdrawal must throw exception, but it didn't");
        assertEquals("Amount must be greater that 0, amount: " + negativeAmount, exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void givenEnoughBalance_withdrawalAccount_willThrowException() {
        Long accountId = 1L;
        BigDecimal negativeAmount = new BigDecimal(50);
        final BigDecimal balance = BigDecimal.valueOf(20);
        Account account = new Account();
        account.setAccountId(accountId);
        account.setBalance(balance);

        willReturn(account).given(accountRepository).findById(accountId);

        InvalidAmountException exception = assertThrows(InvalidAmountException.class,
                () -> accountService.withdrawal(accountId, negativeAmount),
                "accountService.withdrawal must throw exception, but it didn't");
        assertEquals("You dont have enough balance, current balance: " + balance, exception.getMessage());
        verify(accountRepository, never()).save(any());

    }

    @Test
    void givenValidAmount_withdrawalAccount_willSubstractFromBalance() {
        Long accountId = 1L;
        BigDecimal validAmount = new BigDecimal(50);

        Account account = new Account();
        account.setAccountId(accountId);
        account.setBalance(BigDecimal.valueOf(70));
        willReturn(account).given(accountRepository).findById(accountId);

        accountService.withdrawal(accountId, validAmount);

        verify(accountRepository, times(1)).save(accountCaptor.capture());

        Account newAccount = accountCaptor.getValue();
        assertEquals(new BigDecimal(20), newAccount.getBalance());
    }

}
