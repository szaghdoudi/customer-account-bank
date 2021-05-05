package customeraccount.dto;

import customeraccount.domain.TransactionAccount;

import java.math.BigDecimal;
import java.util.List;

public class BalanceTransactionHistoryDto {

    private BigDecimal balance;
    private List<TransactionAccount> transactionAccountItems;

    public BalanceTransactionHistoryDto() {
    }

    public BalanceTransactionHistoryDto(BigDecimal balance, List<TransactionAccount> transactionAccountItems) {
        this.balance = balance;
        this.transactionAccountItems = transactionAccountItems;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<TransactionAccount> getTransactionAccountItems() {
        return transactionAccountItems;
    }

    public void setTransactionAccountItems(List<TransactionAccount> transactionAccountItems) {
        this.transactionAccountItems = transactionAccountItems;
    }
}
