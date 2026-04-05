package com.example.inventory_service.Dto;

import java.time.LocalDate;

public class LedgerDTO {

  private LocalDate date;
  private String transactionType;
  private int crateCount;

  public LedgerDTO(LocalDate date, String transactionType, int crateCount) {
    this.date = date;
    this.transactionType = transactionType;
    this.crateCount = crateCount;
  }

  public LocalDate getDate() {
    return date;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public int getCrateCount() {
    return crateCount;
  }
}
