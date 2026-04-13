package com.khataBook.controller;
import com.khataBook.DTO.AccountRequestDTO;
import com.khataBook.entity.AccountTransaction;
import com.khataBook.projection.AccountLedgerView;
import com.khataBook.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private AccountService service;

  @PostMapping("/transaction")
  public AccountTransaction save(@RequestBody AccountRequestDTO req) {
    return service.save(req);
  }

  @PutMapping("/transaction/{id}")
  public AccountTransaction update(@PathVariable Long id,
                                   @RequestBody AccountRequestDTO req) {
    return service.update(id, req);
  }

  @GetMapping("/ledger/{type}/{id}")
  public List<AccountLedgerView> ledger(@PathVariable String type,
                                        @PathVariable Long id) {
    return service.getLedger(type, id);
  }

  @GetMapping("/balance/{type}/{id}")
  public Double balance(@PathVariable String type,
                        @PathVariable Long id) {
    return service.getBalance(type, id);
  }

  @GetMapping("/customers-summary")
  public List<Map<String, Object>> summary() {
    return service.getCustomerSummary();
  }
}
