package customeraccount.domain;

import customeraccount.enums.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionAccount {

    private Long Id;

    private Long accountId;

    private BigDecimal amount;

    private Instant dateOperation;

    private TransactionType transactionType;

    public TransactionAccount() {
        super();
    }

    public TransactionAccount(Long accountId,BigDecimal amount, Instant dateOperation, TransactionType transactionType) {
        this.accountId = accountId;
        this.amount = amount;
        this.dateOperation = dateOperation;
        this.transactionType = transactionType;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Instant dateOperation) {
        this.dateOperation = dateOperation;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
