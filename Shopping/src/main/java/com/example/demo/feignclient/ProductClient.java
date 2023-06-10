package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.modal.Product;

@FeignClient(name="PRODUCT-SERVICE")
public interface ProductClient {

	@PostMapping("/products/publish")
	public String publishProduct(@RequestBody Product product);
}
