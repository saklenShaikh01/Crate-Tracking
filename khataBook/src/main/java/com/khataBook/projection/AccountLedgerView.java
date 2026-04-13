package com.khataBook.projection;

public interface AccountLedgerView {
Long getId();
  String getTransactionType();
  Double getAmount();
  Double getBalance();
  String getTransactionDate();
}
