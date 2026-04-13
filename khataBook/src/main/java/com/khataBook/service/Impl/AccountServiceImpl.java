package com.khataBook.service.Impl;
import com.khataBook.DTO.AccountRequestDTO;
import com.khataBook.DTO.CustomerDTO;
import com.khataBook.client.UserClient;
import com.khataBook.entity.AccountTransaction;
import com.khataBook.projection.AccountLedgerView;
import com.khataBook.repository.AccountRepository;
import com.khataBook.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository repository;
  @Autowired
  private UserClient userClient;

  @Override
  public AccountTransaction save(AccountRequestDTO req) {

    if (req.getAmount() <= 0) {
      throw new RuntimeException("Amount must be greater than 0");
    }

    AccountTransaction t = new AccountTransaction();

    t.setPersonType(req.getPersonType());
    t.setPersonId(req.getPersonId());
    t.setAmount(req.getAmount());
    t.setTransactionType(req.getType());
    t.setNote(req.getNote());

    if (req.getTransactionDate() != null) {
      t.setTransactionDate(LocalDate.parse(req.getTransactionDate()));
    } else {
      t.setTransactionDate(LocalDate.now());
    }

    return repository.save(t);
  }

  @Override
  public AccountTransaction update(Long id, AccountRequestDTO req) {

    AccountTransaction existing = repository.findById(id)
      .orElseThrow(() -> new RuntimeException("Transaction not found"));

    existing.setAmount(req.getAmount());
    existing.setTransactionType(req.getType());
    existing.setNote(req.getNote());

    if (req.getTransactionDate() != null) {
      existing.setTransactionDate(LocalDate.parse(req.getTransactionDate()));
    }

    return repository.save(existing);
  }

  @Override
  public List<AccountLedgerView> getLedger(String type, Long id) {
    return repository.getLedger(type, id);
  }

  @Override
  public Double getBalance(String type, Long id) {
    return repository.getBalance(type, id);
  }

  @Override
  public List<Map<String, Object>> getCustomerSummary() {
    // 1. Database se sirf unka balance lao jinka transaction hua hai
    List<Object[]> rows = repository.getCustomerSummary();
    Map<Long, Double> balanceMap = new HashMap<>();
    for (Object[] row : rows) {
      balanceMap.put(((Number) row[0]).longValue(), ((Number) row[1]).doubleValue());
    }

    // 2. UserClient se SAARE customers ki list mangwao (chahe transaction ho ya na ho)
    List<CustomerDTO> allCustomers = userClient.getAllCustomers();

    List<Map<String, Object>> finalSummary = new ArrayList<>();

    for (CustomerDTO customer : allCustomers) {
      Map<String, Object> data = new HashMap<>();
      data.put("id", customer.getId());
      data.put("name", customer.getName());

      // Agar balanceMap mein record nahi hai toh balance 0.0 set karo
      Double currentBalance = balanceMap.getOrDefault(customer.getId(), 0.0);
      data.put("balance", currentBalance);

      finalSummary.add(data);
    }

    return finalSummary;
  }
}
