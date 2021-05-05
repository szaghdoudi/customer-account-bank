package customeraccount.services.impl;

import customeraccount.domain.TransactionAccount;
import customeraccount.enums.TransactionType;
import customeraccount.exceptions.DataValidationException;
import customeraccount.repositories.TransactionAccountRepository;
import customeraccount.services.AccountService;
import customeraccount.services.TransactionAccountService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class TransactionAccountServiceImpl implements TransactionAccountService {

    private final AccountService accountService;

    private final TransactionAccountRepository transactionAccountRepository;

    public TransactionAccountServiceImpl(AccountService accountService, TransactionAccountRepository transactionAccountRepository) {
        this.accountService = accountService;
        this.transactionAccountRepository = transactionAccountRepository;
    }

    @Override
    public void processTransaction(Long accountId, BigDecimal amount, TransactionType transactionType) {
        validateBeforeProcessTransaction(accountId, amount, transactionType);

        if (TransactionType.DEPOSIT.equals(transactionType)) {
            accountService.deposit(accountId, amount);
        } else {
            accountService.withdrawal(accountId, amount);
        }
        TransactionAccount transactionAccount = new TransactionAccount(accountId, amount, Instant.now(), transactionType);
        transactionAccountRepository.save(transactionAccount);
    }

    @Override
    public List<TransactionAccount> getTransactionsAccount(Long accountId) {
        return transactionAccountRepository.findAll(accountId);
    }

    private void validateBeforeProcessTransaction(Long accountId, BigDecimal amount, TransactionType transactionType) {

        if (accountId == null) {
            DataValidationException.throwException("AccountId cannot be null");
        }
        if (Objects.isNull(amount) || amount.signum() != 1) {
            DataValidationException.throwException("Amount must be > 0");
        }

        if (Objects.isNull(transactionType)) {
            DataValidationException.throwException("TransactionType must not be null");
        }
    }
}
