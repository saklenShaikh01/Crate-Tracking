package com.example.inventory_service.Dto;

public class ReportDTO {

  private Long id;
  private String name;
  private int pendingCrates;

  public ReportDTO(Long id, String name, int pendingCrates) {
    this.id = id;
    this.name = name;
    this.pendingCrates = pendingCrates;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getPendingCrates() {
    return pendingCrates;
  }
}
