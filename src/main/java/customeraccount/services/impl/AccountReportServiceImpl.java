package customeraccount.services.impl;

import customeraccount.domain.Account;
import customeraccount.domain.TransactionAccount;
import customeraccount.dto.BalanceTransactionHistoryDto;
import customeraccount.services.AccountReportService;
import customeraccount.services.AccountService;
import customeraccount.services.TransactionAccountService;

import java.util.List;

public class AccountReportServiceImpl implements AccountReportService {

    final private AccountService accountService;

    final private TransactionAccountService transactionAccountService;

    public AccountReportServiceImpl(AccountService accountService, TransactionAccountService transactionAccountService) {
        this.accountService = accountService;
        this.transactionAccountService = transactionAccountService;
    }

    @Override
    public BalanceTransactionHistoryDto generateBalanceHistoryReport(Long accountId) {
        Account account = accountService.getAccount(accountId);
        List<TransactionAccount> accountTransactions = transactionAccountService.getTransactionsAccount(accountId);
        return new BalanceTransactionHistoryDto(account.getBalance(), accountTransactions);
    }
}
