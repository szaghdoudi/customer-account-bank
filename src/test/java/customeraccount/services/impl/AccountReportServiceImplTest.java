package customeraccount.services.impl;

import customeraccount.domain.Account;
import customeraccount.domain.TransactionAccount;
import customeraccount.dto.BalanceTransactionHistoryDto;
import customeraccount.enums.TransactionType;
import customeraccount.services.AccountService;
import customeraccount.services.TransactionAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willReturn;

@ExtendWith(MockitoExtension.class)
class AccountReportServiceImplTest {

    @InjectMocks
    AccountReportServiceImpl accountReportService;

    @Mock
    AccountService accountService;

    @Mock
    TransactionAccountService transactionAccountService;

    @Test
    public void givenAccountId_generateReport_willReturnReport() {

        Long accountId = 1L;
        BigDecimal balance = BigDecimal.valueOf(20);

        Account account = new Account();
        account.setAccountId(accountId);
        account.setBalance(balance);
        willReturn(account).given(accountService).getAccount(accountId);

        TransactionAccount transactionAccount = new TransactionAccount(accountId, BigDecimal.TEN, Instant.now(), TransactionType.DEPOSIT);
        final List<TransactionAccount> transactionAccountList = Collections.singletonList(transactionAccount);
        willReturn(transactionAccountList).given(transactionAccountService).getTransactionsAccount(accountId);

        BalanceTransactionHistoryDto balanceTransactionHistoryDto = accountReportService.generateBalanceHistoryReport(accountId);

        assertEquals(balance, balanceTransactionHistoryDto.getBalance());
        assertEquals(transactionAccountList, balanceTransactionHistoryDto.getTransactionAccountItems());
    }

}
