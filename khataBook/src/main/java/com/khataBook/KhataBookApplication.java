package com.khataBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class KhataBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(KhataBookApplication.class, args);
	}

}
