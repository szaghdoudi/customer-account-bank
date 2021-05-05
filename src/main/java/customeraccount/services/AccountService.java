package customeraccount.services;

import java.math.BigDecimal;

public interface AccountService {

    public void deposit(Long accountId, BigDecimal amount);
}
