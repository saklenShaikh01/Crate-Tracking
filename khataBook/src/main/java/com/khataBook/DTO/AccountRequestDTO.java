package com.khataBook.DTO;

import lombok.Data;

@Data
public class AccountRequestDTO {

  private String personType;
  private Long personId;

  private Double amount;
  private String type; // CREDIT / DEBIT

  private String note;

  private String transactionDate; // yyyy-MM-dd
}
