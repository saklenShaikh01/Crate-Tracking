package com.example.inventory_service.client;

import com.example.inventory_service.Dto.CustomerPendingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
  name = "api-gateway",
  url = "https://api-gateway.onrender.com",
  contextId = "customerClient"
)
public interface CustomerClient {

  @GetMapping("/api/customers/byCid/{id}")
  CustomerPendingDTO getCustomer(@PathVariable("id") Long id);
}
