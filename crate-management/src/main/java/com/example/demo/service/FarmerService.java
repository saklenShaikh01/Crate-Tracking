package com.example.demo.service;

import com.example.demo.DTO.BulkBalanceRequest;
import com.example.demo.DTO.CustomerResponseDTO;
import com.example.demo.DTO.FarmerResponseDTO;
import com.example.demo.client.InventoryClient;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Farmer;
import com.example.demo.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FarmerService {

  @Autowired
  private FarmerRepository farmerRepository;
  @Autowired
  private InventoryClient inventoryClient;
  public Farmer updateFarmer(Long id, Farmer farmer){

    Farmer existing = farmerRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Farmer not found"));

    existing.setName(farmer.getName());
    existing.setVillage(farmer.getVillage());
    existing.setMobile_number(farmer.getMobile_number());

    return farmerRepository.save(existing);
  }

  public Farmer saveFarmer(Farmer farmer){
    return farmerRepository.save(farmer);
  }

  public List<FarmerResponseDTO> getAllFarmers() {

    List<Farmer> farmers = farmerRepository.findAll();

    List<Long> ids = farmers.stream()
      .map(Farmer::getId)
      .toList();

    BulkBalanceRequest request = new BulkBalanceRequest();
    request.setPersonType("FARMER");
    request.setIds(ids);

    Map<Long, Integer> balanceMap = inventoryClient.getBulkBalance(request);

    return farmers.stream().map(c -> {

      FarmerResponseDTO dto = new FarmerResponseDTO();

      dto.setId(c.getId());
      dto.setName(c.getName());
      dto.setMobile(c.getMobile_number());
      dto.setVillage(c.getVillage());

      dto.setBalance(balanceMap.getOrDefault(c.getId(), 0));

      return dto;

    }).toList();
  }

  public Farmer getFarmerById(Long id){

    return farmerRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Farmer not found"));
  }
}
