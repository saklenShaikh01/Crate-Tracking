package com.example.inventory_service.client;


import com.example.inventory_service.Dto.CustomerPendingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
  name = "api-gateway",
  url = "https://api-gateway.onrender.com",
  contextId = "farmerClient"
)
public interface FarmerClient {

  @GetMapping("/api/farmers/byFid/{id}")
  CustomerPendingDTO getFarmer(@PathVariable("id") Long id);
}
