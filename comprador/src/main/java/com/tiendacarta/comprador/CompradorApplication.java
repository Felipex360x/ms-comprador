package com.tiendacarta.comprador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.tiendacarta.comprador.Client")
public class CompradorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompradorApplication.class, args);
	}

}
