package com.notification.dto;

public class CustomerResponse {

  private Long id;
  private String name;
  private String mobile;  // 🔥 field

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getMobile() {   // 🔥 THIS METHOD REQUIRED
    return mobile;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
}
