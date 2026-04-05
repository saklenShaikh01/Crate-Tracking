package com.example.api_gateway.entrypoint;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntryController {
  @Bean
  public RouteLocator myRoutes(RouteLocatorBuilder builder) {

    return builder.routes()

      // CUSTOMER APIs
      .route("customer-service", r -> r
        .path("/api/customers/**")
        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
        .uri("https://crate-management.onrender.com"))

      // FARMER APIs
      .route("farmer-service", r -> r
        .path("/api/farmers/**")
        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
        .uri("https://crate-management.onrender.com"))

      // CRATE INVENTORY APIs
      .route("crate-service", r -> r
        .path("/api/crate/**")
        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
        .uri("https://inventory-service.onrender.com"))

      .build();
  }
}
