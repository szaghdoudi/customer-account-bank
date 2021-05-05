package customeraccount.services;

import customeraccount.domain.Account;

import java.math.BigDecimal;

public interface AccountService {

    void deposit(Long accountId, BigDecimal amount);

    void withdrawal(Long accountId, BigDecimal amount);

    Account getAccount(Long accountId);



}
