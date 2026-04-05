package com.example.inventory_service.Dto;

public class DashboardResponse {

  private int shopStock;
  private int totalFarmerPending;
  private int totalCustomerPending;
  private int todayTransactions;

  public int getShopStock() {
    return shopStock;
  }

  public void setShopStock(int shopStock) {
    this.shopStock = shopStock;
  }

  public int getTotalFarmerPending() {
    return totalFarmerPending;
  }

  public void setTotalFarmerPending(int totalFarmerPending) {
    this.totalFarmerPending = totalFarmerPending;
  }

  public int getTotalCustomerPending() {
    return totalCustomerPending;
  }

  public void setTotalCustomerPending(int totalCustomerPending) {
    this.totalCustomerPending = totalCustomerPending;
  }

  public int getTodayTransactions() {
    return todayTransactions;
  }

  public void setTodayTransactions(int todayTransactions) {
    this.todayTransactions = todayTransactions;
  }
}
