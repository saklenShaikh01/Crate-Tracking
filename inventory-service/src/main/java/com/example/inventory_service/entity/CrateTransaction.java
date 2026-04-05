  package com.example.inventory_service.entity;


  import jakarta.persistence.*;

  import java.time.LocalDate;
  import java.time.LocalDateTime;
  import lombok.*;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Entity
  public class CrateTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String personType;

    private Long personId;
    private String name;

    private Integer crateCount;

    private String transactionType;

    private LocalDate transactionDate;
    private Integer balance;
  //  private LocalDate transactionDateL;

    @PrePersist
    public void prePersist() {
      if (this.transactionDate == null) {
        this.transactionDate = LocalDate .now();
      }
    }


  }
