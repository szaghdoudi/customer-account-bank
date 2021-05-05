package customeraccount.services;

import customeraccount.domain.TransactionAccount;
import customeraccount.enums.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionAccountService {

    void processTransaction(Long accountId, BigDecimal amount, TransactionType transactionType);

    List<TransactionAccount> getTransactionsAccount(Long accountId);
}
