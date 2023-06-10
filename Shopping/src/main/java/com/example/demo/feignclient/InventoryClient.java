package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.modal.ProdInvDto;

@FeignClient(name="INVENTORY-SERVICE")
public interface InventoryClient {

	@GetMapping("/inventory/inwithpro/{id}/quantity/{qty}")
	public ProdInvDto inventoryWithProduct(@PathVariable int id, @PathVariable int qty);
	
	@PutMapping("/inventory/{id}/quantity/{qty}")
	public void decreaseInventory(@PathVariable int id, @PathVariable int qty);
}
