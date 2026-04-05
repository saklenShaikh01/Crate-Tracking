package com.example.inventory_service.Dto;

import com.example.inventory_service.enums.ActionType;
import com.example.inventory_service.enums.PersonType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrateRequest {

  private Long personId;
  private String name;
  private PersonType personType;
  private ActionType action;
  private int crateCount;
  private LocalDate transactionDate;
  private int balance;

  // getters setters
}
