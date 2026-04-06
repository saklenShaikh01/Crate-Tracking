package com.example.inventory_service.client;


import com.example.inventory_service.Dto.CustomerPendingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
  name = "crate-management",
  url = "https://crate-tracking.onrender.com",
  contextId = "customerClient"
)
public interface FarmerClient {

  @GetMapping("/farmers/byFid/{id}")
  CustomerPendingDTO getFarmer(@PathVariable("id") Long id);
}
