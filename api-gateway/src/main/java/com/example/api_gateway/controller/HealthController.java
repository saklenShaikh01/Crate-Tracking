package com.example.api_gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

  @Autowired
  private WebClient webClient;

  private Mono<String> checkService(String name, String url) {
    return webClient.get()
      .uri(url)
      .retrieve()
      .bodyToMono(String.class)
      .timeout(Duration.ofSeconds(2))
      .map(res -> name + ": UP")
      .onErrorReturn(name + ": DOWN");
  }

  @GetMapping
  public Mono<Map<String, String>> health() {

    Mono<String> crate = checkService("crate-management", "https://crate-tracking.onrender.com/customers/health");
    Mono<String> inventory = checkService("inventory-service", "https://crate-inventory.onrender.com/crate/health");

    return Mono.zip(crate, inventory)
      .map(tuple -> {
        Map<String, String> result = new HashMap<>();
        result.put("crate", tuple.getT1());
        result.put("inventory", tuple.getT2());
        return result;
      });
  }
}
