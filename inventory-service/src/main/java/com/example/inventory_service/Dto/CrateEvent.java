package com.example.inventory_service.Dto;

import java.io.Serializable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrateEvent implements Serializable {

  private Long personId;   // ✅ correct
  private String personType;
  private int balance;
  private String action;


  // getters setters
}
