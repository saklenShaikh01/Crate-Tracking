package com.notification.dto;

import java.io.Serializable;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrateEvent implements Serializable {

  private Long personId;      // ✅ same as inventory
  private String personType;  // ✅ same name
  private int balance;
  private String action;
}
