package com.example.inventory_service.service;


import com.example.inventory_service.Dto.*;
import com.example.inventory_service.entity.CrateTransaction;
import com.example.inventory_service.entity.LedgerView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CrateService {

  CrateTransaction farmerTake(CrateTransaction transaction);

  CrateTransaction farmerReturn(CrateTransaction transaction);

  CrateTransaction customerTake(CrateTransaction transaction);

  CrateTransaction customerReturn(CrateTransaction transaction);

  int getBalance(String personType, Long personId);
  int getShopStock();
  Map<Long, Integer> getFarmerPendingReport();
  Map<Long, Integer> getCustomerPendingReport();
  List<CrateTransaction> getDailyReport(LocalDate date);
  DashboardResponse getDashboardData();
  List<ReportView> getFarmerReport();

  List<ReportView> getCustomerReport();
  List<ReportView> getCustomerPendingFivePlus();
  List<LedgerView> getCustomerLedger(Long id);

  List<LedgerView> getFarmerLedger(Long id);

  CrateTransaction shopPurchase(CrateTransaction transaction);

  List<RecentTransactionDTO> getRecentTransactions();
  Object processUnified(CrateRequest request);
  Map<Long, Integer> getBulkBalance(BulkBalanceRequest request);

}
