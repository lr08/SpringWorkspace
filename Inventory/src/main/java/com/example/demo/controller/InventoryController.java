package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Inventory;
import com.example.demo.service.InventoryService;
import com.example.demo.service.payload.InventoryDto;
import com.example.demo.service.payload.ProdInvDto;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	private InventoryService service;
	
	@GetMapping
	public List<Inventory> getAll(){
		return service.getAll();
	}
	
	@PutMapping("/{id")
	public ResponseEntity<InventoryDto> updateInventory(@RequestBody InventoryDto inventoryDto, @PathVariable int id){
		return new ResponseEntity<>(service.updateInventory(id, inventoryDto),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<InventoryDto> searchInventory(@PathVariable int id){
		return new ResponseEntity<>(service.searchInventory(id),HttpStatus.OK);
	}
	
	@PutMapping("/{id}/quantity/{qty}")
	public String decreaseInventoryWithProduct(@PathVariable int id,@PathVariable int qty) {
		service.decreaseInventory(id, qty);
		return "quantity decreased";
	}
	
	@GetMapping("/invwithpro/{id}/quantity/{qty}")
	public ProdInvDto inventoryWithProduct(@PathVariable int id,@PathVariable int qty) {
		return service.getInvWithProd(id, qty);
	}
	
}
