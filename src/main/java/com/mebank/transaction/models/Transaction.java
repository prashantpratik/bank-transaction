package com.mebank.transaction.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Transaction implements Serializable {
    @CsvBindByName
    private String transactionId;
    @CsvBindByName
    private String fromAccountId;
    @CsvBindByName
    private String toAccountId;

    @CsvDate("dd/MM/yyyy HH:mm:ss")
    @CsvBindByName
    private Date createdAt;

    @CsvBindByName
    private double amount;
    @CsvBindByName
    private String transactionType;
    @CsvBindByName()
    private String relatedTransaction;


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 &&
                transactionId.equals(that.transactionId) &&
                fromAccountId.equals(that.fromAccountId) &&
                toAccountId.equals(that.toAccountId) &&
                createdAt.equals(that.createdAt) &&
                transactionType.equals(that.transactionType) &&
                Objects.equals(relatedTransaction, that.relatedTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, fromAccountId, toAccountId, createdAt, amount, transactionType, relatedTransaction);
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId.trim();
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId.trim();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", fromAccountId='" + fromAccountId + '\'' +
                ", toAccountId='" + toAccountId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                ", relatedTransaction=" + relatedTransaction +
                '}';
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return TransactionType.valueOf(transactionType);
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType.trim();
    }

    public String getRelatedTransaction() {
        return relatedTransaction;
    }

    public void setRelatedTransaction(String relatedTransaction) {
        this.relatedTransaction = relatedTransaction.trim();
    }


    public enum TransactionType {
        PAYMENT,
        REVERSAL
    }
}
