package customeraccount.services.impl;

import customeraccount.domain.Account;
import customeraccount.repositories.AccountRepository;
import customeraccount.services.AccountService;
import customeraccount.exceptions.InvalidAmountException;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        validateBeforeDeposit(accountId, amount);
        Account account = accountRepository.findById(accountId);
        final BigDecimal balance = account.getBalance();
        account.setBalance(balance == null ? amount : amount.add(balance));
        accountRepository.save(account);
    }

    @Override
    public void withdrawal(Long accountId, BigDecimal amount) {
        validateBeforeWithdrawal(accountId, amount);
        Account account = accountRepository.findById(accountId);
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    private void validateBeforeDeposit(Long accountId, BigDecimal amount) {
        validateAmount(amount);
        verifyAccountStatus(accountId);

    }


    private void validateBeforeWithdrawal(Long accountId, BigDecimal amount) {
        validateAmount(amount);
        verifyAccountStatus(accountId);
        verifyBalanceStatus(accountId, amount);
    }

    private void verifyBalanceStatus(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId);
        BigDecimal balance = account.getBalance() == null ? BigDecimal.ZERO : account.getBalance();
        if (amount.compareTo(balance) == 1) {
            throw new InvalidAmountException("You dont have enough balance, current balance: " + balance);
        }
    }

    private void verifyAccountStatus(Long accountId) {
        System.out.println("Account validation....");
        System.out.println("No issue found....");
    }

    private void validateAmount(BigDecimal amount) {
        if (Objects.isNull(amount) || amount.signum() != 1) {
            throw new InvalidAmountException("Amount must be greater that 0, amount: " + amount);
        }
    }


}
