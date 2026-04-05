package com.example.inventory_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor     // 🔥 MUST
@AllArgsConstructor
public class CustomerPendingDTO {

  private Long customerId;
  private String name;
  private int pendingCrates;
//
//  public CustomerPendingDTO(Long customerId, String customerName, int pendingCrates) {
//    this.customerId = customerId;
//    this.customerName = customerName;
//    this.pendingCrates = pendingCrates;
//  }
//
//  public Long getCustomerId() {
//    return customerId;
//  }
//
//  public String getCustomerName() {
//    return customerName;
//  }
//
//  public int getPendingCrates() {
//    return pendingCrates;
//  }
}
