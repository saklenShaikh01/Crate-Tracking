package com.khataBook.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "account_transaction")
@Data
public class AccountTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String personType;   // CUSTOMER / FARMER
  private Long personId;

  private Double amount;

  private String transactionType; // CREDIT / DEBIT

  private String note;

  private LocalDate transactionDate;
}
