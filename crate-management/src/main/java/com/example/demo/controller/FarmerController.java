package com.example.demo.controller;

import com.example.demo.DTO.FarmerResponseDTO;
import com.example.demo.entity.Farmer;
import com.example.demo.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/farmers")
public class FarmerController {

  @Autowired
  FarmerService farmerService;

  @PostMapping
  public Farmer createFarmer(@RequestBody Farmer farmer){
    return farmerService.saveFarmer(farmer);
  }

  @GetMapping
  public List<FarmerResponseDTO> getFarmers(){
    return farmerService.getAllFarmers();
  }
  @PutMapping("/{id}")
  public Farmer updateFarmer(@PathVariable("id") Long id,
                             @RequestBody Farmer farmer){

    return farmerService.updateFarmer(id, farmer);
  }

  @GetMapping("/byFid/{id}")
  public Farmer getFarmerById(@PathVariable("id") Long id){
    return farmerService.getFarmerById(id);
  }

}
