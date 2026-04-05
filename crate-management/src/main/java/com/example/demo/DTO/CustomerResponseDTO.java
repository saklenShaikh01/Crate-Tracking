package com.example.demo.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {

  private Long id;
  private String name;
  private String mobile;
  private String address;
  private Integer balance;

  // getters setters
}
