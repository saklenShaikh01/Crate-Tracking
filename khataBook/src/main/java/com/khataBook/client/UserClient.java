package com.khataBook.client;

import com.khataBook.DTO.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
  name = "crate-management",
  url = "https://crate-tracking.onrender.com",
  contextId = "customerClient"
)
public interface UserClient {


  @GetMapping("/customers/byCid/{id}")
  CustomerDTO getCustomer(@PathVariable("id") Long id);
  @GetMapping("/customers")
  List<CustomerDTO> getAllCustomers();

}
