package com.example.inventory_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentTransactionDTO {

  private String personName;
  private String personType;
  private String action; // IN / OUT
  private int crateCount;
  private LocalDate dateTime;

}
