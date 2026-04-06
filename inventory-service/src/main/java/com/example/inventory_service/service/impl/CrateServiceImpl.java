package com.example.inventory_service.service.impl;
import com.example.inventory_service.Dto.*;
import com.example.inventory_service.client.CustomerClient;
import com.example.inventory_service.client.FarmerClient;
import com.example.inventory_service.entity.CrateTransaction;
import com.example.inventory_service.entity.LedgerView;
import com.example.inventory_service.enums.ActionType;
import com.example.inventory_service.enums.PersonType;
import com.example.inventory_service.messaging.MessageProducer;
import com.example.inventory_service.repository.CrateTransactionRepository;
import com.example.inventory_service.service.CrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.inventory_service.Dto.CrateEvent;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrateServiceImpl implements CrateService {

  @Autowired
  private CrateTransactionRepository repository;
  @Autowired
  private MessageProducer producer;
  @Autowired
  private CustomerClient customerClient;

  @Autowired
  private FarmerClient farmerClient;



  private void sendCrateMessage(CrateTransaction savedTxn) {

    int balance = getBalance(
      savedTxn.getPersonType(),
      savedTxn.getPersonId()
    );

    String action = "";

    switch (savedTxn.getTransactionType()) {
      case "FARMER_TAKE":
      case "CUSTOMER_TAKE":
        action = "TAKEN";
        break;

      case "FARMER_RETURN":
      case "CUSTOMER_RETURN":
        action = "RETURNED";
        break;
    }

    CrateEvent event = new CrateEvent(
      savedTxn.getPersonId(), // ⚠️ ensure field hai
      savedTxn.getPersonType(),
      balance,
      action
    );

    producer.sendEvent(event);
  }


  @Override
  public CrateTransaction farmerTake(CrateTransaction transaction) {

    transaction.setPersonType("FARMER");
    transaction.setTransactionType("FARMER_TAKE");
    CrateTransaction saved = repository.save(transaction);
//    sendCrateMessage(saved);
    int balance= this.getBalance(transaction.getPersonType(), transaction.getPersonId());
    transaction.setBalance(balance);
    return saved;
  }

  @Override
  public CrateTransaction farmerReturn(CrateTransaction transaction) {

    transaction.setPersonType("FARMER");
    transaction.setTransactionType("FARMER_RETURN");
    CrateTransaction saved = repository.save(transaction);
//    sendCrateMessage(saved);
    int balance= this.getBalance(transaction.getPersonType(), transaction.getPersonId());
    transaction.setBalance(balance);
    return saved;
  }

  @Override
  public CrateTransaction customerTake(CrateTransaction transaction) {

    transaction.setPersonType("CUSTOMER");
    transaction.setTransactionType("CUSTOMER_TAKE");
    CrateTransaction saved = repository.save(transaction);
//    sendCrateMessage(saved);
    int balance= this.getBalance(transaction.getPersonType(), transaction.getPersonId());
    transaction.setBalance(balance);
    return saved;
  }

  @Override
  public CrateTransaction customerReturn(CrateTransaction transaction) {

    transaction.setPersonType("CUSTOMER");
    transaction.setTransactionType("CUSTOMER_RETURN");
    CrateTransaction saved = repository.save(transaction);
//    sendCrateMessage(saved);
    int balance= this.getBalance(transaction.getPersonType(), transaction.getPersonId());
    transaction.setBalance(balance);
    return saved;
  }

  @Override
  public int getBalance(String personType, Long personId) {
    Integer balance = repository.getBalance(personId, personType);
    if (balance < 0) {
      throw new RuntimeException("Invalid data: Balance cannot be negative");
    }
    return balance != null ? balance : 0;
  }

  @Override
  public int getShopStock() {

    return repository.getShopStock();
  }
  @Override
  public Map<Long, Integer> getFarmerPendingReport() {

    List<CrateTransaction> list = repository.findByPersonType("FARMER");

    Map<Long, Integer> report = new HashMap<>();

    for (CrateTransaction t : list) {

      int balance = report.getOrDefault(t.getPersonId(), 0);

      if (t.getTransactionType().equals("FARMER_TAKE")) {
        balance += t.getCrateCount();
      }

      if (t.getTransactionType().equals("FARMER_RETURN")) {
        balance -= t.getCrateCount();
      }

      report.put(t.getPersonId(), balance);
    }

    return report;
  }
  @Override
  public Map<Long, Integer> getCustomerPendingReport() {

    List<CrateTransaction> list = repository.findByPersonType("CUSTOMER");

    Map<Long, Integer> report = new HashMap<>();

    for (CrateTransaction t : list) {

      int balance = report.getOrDefault(t.getPersonId(), 0);

      if (t.getTransactionType().equals("CUSTOMER_TAKE")) {
        balance += t.getCrateCount();
      }

      if (t.getTransactionType().equals("CUSTOMER_RETURN")) {
        balance -= t.getCrateCount();
      }

      report.put(t.getPersonId(), balance);
    }

    return report;
  }
  @Override
  public List<CrateTransaction> getDailyReport(LocalDate date) {
    return repository.findByTransactionDate(date);
  }
  @Override
  public DashboardResponse getDashboardData() {

    DashboardResponse response = new DashboardResponse();

    List<CrateTransaction> list = repository.findAll();

    int shopStock = 0;
    int farmerPending = 0;
    int customerPending = 0;

    for (CrateTransaction t : list) {

      switch (t.getTransactionType()) {

        case "FARMER_TAKE":
          shopStock -= t.getCrateCount();
          farmerPending += t.getCrateCount();
          break;

        case "FARMER_RETURN":
          shopStock += t.getCrateCount();
          farmerPending -= t.getCrateCount();
          break;

        case "CUSTOMER_TAKE":
          shopStock -= t.getCrateCount();
          customerPending += t.getCrateCount();
          break;

        case "CUSTOMER_RETURN":
          shopStock += t.getCrateCount();
          customerPending -= t.getCrateCount();
          break;
      }
    }

    response.setShopStock(this.getShopStock());
    response.setTotalFarmerPending(farmerPending);
    response.setTotalCustomerPending(customerPending);
    response.setTodayTransactions(list.size());

    return response;
  }
  @Override
  public List<ReportView> getFarmerReport() {
    return repository.getFarmerReport();
  }

  @Override
  public List<ReportView> getCustomerReport() {
    return repository.getCustomerReport();
  }

  @Override
  public List<ReportView> getCustomerPendingFivePlus(){
    return repository.getCustomerPendingFivePlus();
  }



  @Override
  public List<LedgerView> getCustomerLedger(Long id){
    return repository.getCustomerLedger(id);
  }

  @Override
  public List<LedgerView> getFarmerLedger(Long id){
    return repository.getFarmerLedger(id);
  }

  @Override
  public CrateTransaction shopPurchase(CrateTransaction transaction){

    transaction.setTransactionType("SHOP_PURCHASE");

    return repository.save(transaction);
  }

  @Override
  public List<RecentTransactionDTO> getRecentTransactions() {

    List<CrateTransaction> list = repository.findRecentTransactions();

    return list.stream().limit(10).map(t -> {

      String name = "Unknown";
      String type = "UNKNOWN";
      String action = "UNKNOWN";

      // 🔥 HANDLE PURCHASE CASE FIRST
      if ("SHOP_PURCHASE".equals(t.getTransactionType())) {

        name = "Shop";
        type = "SHOP";
        action = "IN"; // purchase means crates added

      } else {

        // NORMAL CASE (CUSTOMER / FARMER)
        type = t.getPersonType() != null ? t.getPersonType() : "UNKNOWN";

        if (t.getTransactionType() != null) {
          if (t.getTransactionType().endsWith("TAKE")) {
            action = "OUT";
          } else if (t.getTransactionType().endsWith("RETURN")) {
            action = "IN";
          }
        }

        // 🔥 Feign call only if valid
        if (t.getPersonId() != null && t.getPersonType() != null) {

          try {
            if ("CUSTOMER".equals(type)) {
              name = customerClient.getCustomer(t.getPersonId()).getName();
            } else if ("FARMER".equals(type)) {
              name = farmerClient.getFarmer(t.getPersonId()).getName();
            }
          } catch (Exception e) {
            name = "Not Found";
          }

        }
      }

      return new RecentTransactionDTO(
        name,
        type,
        action,
        t.getCrateCount(),
        t.getTransactionDate()
      );

    }).toList();
  }

  @Override
  @Transactional
  public Object processUnified(CrateRequest request) {

    this.validate(request);
    validatePerson(request.getPersonId(), request.getPersonType());
    String personName;

    if (request.getPersonType() == PersonType.FARMER) {
      personName = farmerClient.getFarmer(request.getPersonId()).getName();
    } else {
      personName = customerClient.getCustomer(request.getPersonId()).getName();
    }
    request.setName(personName);
    if (request.getPersonType() == PersonType.FARMER) {

      if (request.getAction() == ActionType.TAKE) {
        return handleFarmerTake(request);
      } else {
        return handleFarmerReturn(request);
      }

    } else if (request.getPersonType() == PersonType.CUSTOMER) {

      if (request.getAction() == ActionType.TAKE) {
        return handleCustomerTake(request);
      } else {
        return handleCustomerReturn(request);
      }
    }

    throw new RuntimeException("Invalid request");
  }

  private void validatePerson(Long personId, PersonType type) {

    if (type == PersonType.FARMER) {

      CustomerPendingDTO farmer = farmerClient.getFarmer(personId);

      if (farmer == null) {
        throw new RuntimeException("Farmer not found");
      }

    } else {

      CustomerPendingDTO customer = customerClient.getCustomer(personId);

      if (customer == null) {
        throw new RuntimeException("Customer not found");
      }
    }
  }

  private void validate(CrateRequest request) {

    if (request == null) {
      throw new RuntimeException("Request cannot be null");
    }

    if (request.getPersonId() == null) {
      throw new RuntimeException("PersonId is required");
    }

    if (request.getPersonType() == null) {
      throw new RuntimeException("PersonType is required");
    }

    if (request.getAction() == null) {
      throw new RuntimeException("Action is required");
    }

    if (request.getCrateCount() <= 0) {
      throw new RuntimeException("Crate count must be greater than 0");
    }
  }

  private Object handleFarmerTake(CrateRequest request) {

    // 👉 existing DTO ya entity bana
    CrateTransaction transaction = new CrateTransaction();
    transaction.setName((request.getName()));
    transaction.setPersonId(request.getPersonId());
    transaction.setPersonType("FARMER");
    transaction.setTransactionType("FARMER_TAKE"); // ya jo tera DB me use ho raha hai
    transaction.setCrateCount(request.getCrateCount());
    transaction.setTransactionDate(request.getTransactionDate());
    // 👉 existing method call kar
    return farmerTake(transaction);
  }

  private Object handleFarmerReturn(CrateRequest request) {

    CrateTransaction transaction = new CrateTransaction();
    transaction.setName((request.getName()));
    transaction.setPersonId(request.getPersonId());
    transaction.setPersonType("FARMER");
    transaction.setTransactionType("FARMER_RETURN");
    transaction.setCrateCount(request.getCrateCount());
    transaction.setTransactionDate(request.getTransactionDate());
    return farmerReturn(transaction);
  }

  private Object handleCustomerTake(CrateRequest request) {

    CrateTransaction transaction = new CrateTransaction();
    transaction.setName((request.getName()));
    transaction.setPersonId(request.getPersonId());
    transaction.setPersonType("CUSTOMER");
    transaction.setTransactionType("CUSTOMER_TAKE"); // usually customer leke jata hai
    transaction.setCrateCount(request.getCrateCount());
    transaction.setTransactionDate(request.getTransactionDate());
    return customerTake(transaction);
  }

  private Object handleCustomerReturn(CrateRequest request) {

    CrateTransaction transaction = new CrateTransaction();
    transaction.setName((request.getName()));
    transaction.setPersonId(request.getPersonId());
    transaction.setPersonType("CUSTOMER");
    transaction.setTransactionType("CUSTOMER_RETURN");
    transaction.setCrateCount(request.getCrateCount());
    transaction.setTransactionDate(request.getTransactionDate());
    return customerReturn(transaction);
  }
@Override
  public Map<Long, Integer> getBulkBalance(BulkBalanceRequest request) {

    List<Object[]> results = repository.getBulkBalance(
      request.getPersonType(),
      request.getIds()
    );

    Map<Long, Integer> balanceMap = new HashMap<>();

    // DB se jo aaya
    for (Object[] row : results) {
      Long personId = (Long) row[0];
      Integer balance = ((Number) row[1]).intValue();

      balanceMap.put(personId, balance);
    }

    // jinke transactions nahi hai unko 0 set karo
    for (Long id : request.getIds()) {
      balanceMap.putIfAbsent(id, 0);
    }

    return balanceMap;
  }
}
