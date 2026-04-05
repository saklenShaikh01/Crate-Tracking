package com.example.demo.client;

import com.example.demo.DTO.BulkBalanceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
  name = "api-gateway",
  url = "https://api-gateway.onrender.com",
  contextId = "UserClient"
)
public interface InventoryClient {

  @GetMapping("api/crate/balance/{type}/{id}")
  Integer getBalance(
    @PathVariable("type") String type,
    @PathVariable("id") Long id
  );
  @PostMapping("api/crate/balance/bulk")
  Map<Long, Integer> getBulkBalance(@RequestBody BulkBalanceRequest request);
}
