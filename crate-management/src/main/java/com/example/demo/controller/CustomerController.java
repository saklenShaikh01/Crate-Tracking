package com.example.demo.controller;

import com.example.demo.DTO.CustomerResponseDTO;
import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  CustomerService customerService;

  @PostMapping
  public Customer createCustomer(@RequestBody Customer customer){
    return customerService.saveCustomer(customer);
  }

  @GetMapping
  public List<CustomerResponseDTO> getCustomers(){
    return customerService.getAllCustomers();
  }

  @PutMapping("/{id}")
  public Customer updateCustomer(@PathVariable("id") Long id,
                                 @RequestBody Customer customer){

    return customerService.updateCustomer(id, customer);
  }

  @GetMapping("/byCid/{id}")
  public Customer getCustomerById(@PathVariable("id") Long id){
    return customerService.getCustomerById(id);
  }


}
