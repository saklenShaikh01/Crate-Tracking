package com.example.inventory_service.controller;


import com.example.inventory_service.Dto.*;
import com.example.inventory_service.entity.CrateTransaction;
import com.example.inventory_service.entity.LedgerView;
import com.example.inventory_service.enums.ActionType;
import com.example.inventory_service.enums.PersonType;
import com.example.inventory_service.service.CrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crate")
public class CrateController {

  @Autowired
  private CrateService crateService;
//
//  @PostMapping("/farmer-take")
//    public ResponseEntity<?> farmerTake(@RequestBody CrateTransaction transaction) {
//
//      CrateRequest request = new CrateRequest();
//      request.setPersonId(transaction.getPersonId());
//      request.setPersonType(PersonType.FARMER);
//      request.setAction(ActionType.valueOf("FARMER_TAKE"));
//      request.setCrateCount(transaction.getCrateCount());
//
//      return ResponseEntity.ok(crateService.processUnified(request));
//  }
//
//  @PostMapping("/farmer-return")
//  public CrateTransaction farmerReturn(@RequestBody CrateTransaction transaction) {
//    return crateService.farmerReturn(transaction);
//  }
//
//  @PostMapping("/customer-take")
//  public CrateTransaction customerTake(@RequestBody CrateTransaction transaction) {
//    return crateService.customerTake(transaction);
//  }
//
//  @PostMapping("/customer-return")
//  public CrateTransaction customerReturn(@RequestBody CrateTransaction transaction) {
//    return crateService.customerReturn(transaction);
//  }

  @GetMapping("/balance/{personType}/{personId}")
  public int getBalance(
    @PathVariable("personType") String personType,
    @PathVariable("personId") Long personId) {

    return  crateService.getBalance(personType, personId);
  }

  @GetMapping("/shop-stock")
  public Integer getShopStock(){
    return crateService.getShopStock();
  }
  @GetMapping("/farmer-report")
  public List<ReportView> farmerReport(){
    return crateService.getFarmerReport();
  }

  @GetMapping("/customer-report")
  public List<ReportView> customerReport(){
    return crateService.getCustomerReport();
  }
  @GetMapping("/daily-report/{date}")
  public List<CrateTransaction> dailyReport(@PathVariable("date") String date) {

    LocalDate localDate = LocalDate.parse(date);

    return crateService.getDailyReport(localDate);
  }
  @GetMapping("/dashboard")
  public DashboardResponse dashboard() {
    return crateService.getDashboardData();
  }

  @GetMapping("/customer-pending-5plus")
  public List<ReportView> customerPendingFivePlus(){
    return crateService.getCustomerPendingFivePlus();
  }


  @GetMapping("/customer-ledger/{id}")
  public List<LedgerView> customerLedger(@PathVariable("id") Long id){
    return crateService.getCustomerLedger(id);
  }

  @GetMapping("/farmer-ledger/{id}")
  public List<LedgerView> farmerLedger(@PathVariable("id") Long id){
    return crateService.getFarmerLedger(id);
  }

  @PostMapping("/shop-purchase")
  public CrateTransaction shopPurchase(@RequestBody CrateTransaction transaction){

    return crateService.shopPurchase(transaction);
  }

  @GetMapping("/recent-transactions")
  public List<RecentTransactionDTO> getRecentTransactions() {
    return crateService.getRecentTransactions();
  }

  @PostMapping("/transaction")
  public ResponseEntity<?> handleCrate(@RequestBody CrateRequest request) {
    return ResponseEntity.ok(crateService.processUnified(request));
  }

  @PostMapping("/balance/bulk")
  public Map<Long, Integer> getBulkBalance(
    @RequestBody BulkBalanceRequest request) {

    return crateService.getBulkBalance(request);
  }

}
