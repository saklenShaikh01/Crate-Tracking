package com.example.demo.DTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerResponseDTO {

  private Long id;
  private String name;
  private String mobile;
  private String village;
  private Integer balance;

  // getters setters
}
