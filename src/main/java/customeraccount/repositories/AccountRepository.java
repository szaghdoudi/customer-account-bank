package customeraccount.repositories;

import customeraccount.domain.Account;

public interface AccountRepository {

    public Account findById(Long id);

    public Account save(Account account);
}
