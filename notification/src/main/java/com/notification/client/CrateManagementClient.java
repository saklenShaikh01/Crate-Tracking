package com.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.notification.dto.CustomerResponse;

@FeignClient(name = "api-gateway", url = "http://localhost:8082")
public interface CrateManagementClient {

  @GetMapping("/api/customers/byCid/{id}")
  CustomerResponse getCustomer(@PathVariable("id") Long id);

  @GetMapping("/api/farmers/byFid/{id}")
  CustomerResponse getFarmer(@PathVariable("id") Long id);
}
