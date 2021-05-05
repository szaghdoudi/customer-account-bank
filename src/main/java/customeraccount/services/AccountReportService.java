package customeraccount.services;

import customeraccount.dto.BalanceTransactionHistoryDto;

public interface AccountReportService {

    BalanceTransactionHistoryDto generateBalanceHistoryReport(Long accountId);
}
