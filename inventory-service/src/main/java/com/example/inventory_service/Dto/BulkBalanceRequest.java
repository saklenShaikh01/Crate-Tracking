package com.example.inventory_service.Dto;

import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkBalanceRequest {

  private String personType;
  private List<Long> ids;

  // getters setters
}
