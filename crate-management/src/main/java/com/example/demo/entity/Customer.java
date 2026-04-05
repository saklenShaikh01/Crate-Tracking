package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String mobile_number;

  private String city;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMobile() {
    return mobile_number;
  }

  public void setMobile(String mobile) {
    this.mobile_number = mobile;
  }

  public String getAddress() {
    return city;
  }

  public void setAddress(String address) {
    this.city = address;
  }

}
