package customeraccount.repositories;

import customeraccount.domain.Account;
import customeraccount.domain.TransactionAccount;

import java.util.List;

public interface TransactionAccountRepository {

    public List<TransactionAccount> findAll(Long accountId);

    public TransactionAccount save(TransactionAccount transactionAccount);
}
