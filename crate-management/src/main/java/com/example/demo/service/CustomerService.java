package com.example.demo.service;


import com.example.demo.DTO.BulkBalanceRequest;
import com.example.demo.DTO.CustomerResponseDTO;
import com.example.demo.client.InventoryClient;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  private InventoryClient inventoryClient;

  public Customer saveCustomer(Customer customer){
    return customerRepository.save(customer);
  }

  public List<CustomerResponseDTO> getAllCustomers() {

    List<Customer> customers = customerRepository.findAll();

    List<Long> ids = customers.stream()
      .map(Customer::getId)
      .toList();

    BulkBalanceRequest request = new BulkBalanceRequest();
    request.setPersonType("CUSTOMER");
    request.setIds(ids);

    Map<Long, Integer> balanceMap = inventoryClient.getBulkBalance(request);

    return customers.stream().map(c -> {

      CustomerResponseDTO dto = new CustomerResponseDTO();

      dto.setId(c.getId());
      dto.setName(c.getName());
      dto.setMobile(c.getMobile());
      dto.setAddress(c.getAddress());

      dto.setBalance(balanceMap.getOrDefault(c.getId(), 0));

      return dto;

    }).toList();
  }

  public Customer updateCustomer(Long id, Customer customer){

    Customer existing = customerRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Customer not found"));

    existing.setName(customer.getName());
    existing.setAddress(customer.getAddress());
    existing.setMobile(customer.getMobile());

    return customerRepository.save(existing);
  }


  public Customer getCustomerById(Long id){

    return customerRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Customer not found"));
  }

}
