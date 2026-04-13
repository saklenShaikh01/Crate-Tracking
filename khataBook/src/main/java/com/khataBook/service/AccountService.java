package com.khataBook.service;

import com.khataBook.DTO.AccountRequestDTO;
import com.khataBook.entity.AccountTransaction;
import com.khataBook.projection.AccountLedgerView;

import java.util.List;
import java.util.Map;

public interface AccountService {

  AccountTransaction save(AccountRequestDTO req);

  AccountTransaction update(Long id, AccountRequestDTO req);

  List<AccountLedgerView> getLedger(String type, Long id);

  Double getBalance(String type, Long id);

  List<Map<String, Object>> getCustomerSummary();
}
