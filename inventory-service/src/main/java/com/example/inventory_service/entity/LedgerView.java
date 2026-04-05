package com.example.inventory_service.entity;

import java.time.LocalDate;

public interface LedgerView {

  String getTransactionType();

  Integer getCrateCount();

  Integer getBalance();


  LocalDate getTransactionDate();

}
