package com.example.demo.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Inventory;
import com.example.demo.exceptions.InventoryNotFoundException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.kafka.AppConstants;
import com.example.demo.modal.Product;
import com.example.demo.proxy.ProductProxy;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.service.payload.InventoryDto;
import com.example.demo.service.payload.ProdInvDto;

@Service
public class InventoryService {

	@Autowired
	private ProductProxy pro;
	
	@Autowired
	private InventoryRepository repo;
	
	private ModelMapper mapper;
	
	public InventoryService(ProductProxy pro, InventoryRepository repo, ModelMapper mapper) {
		super();
		this.pro = pro;
		this.repo = repo;
		this.mapper = mapper;
	}

	private InventoryDto mapToDto(Inventory inv) {
		InventoryDto inventory = mapper.map(inv, InventoryDto.class);
		return inventory;
	}
	
	private Inventory mapToEntity(InventoryDto inv) {
		Inventory inventory = mapper.map(inv, Inventory.class);
		return inventory;
	}
	
	@KafkaListener(topics= AppConstants.TOPIC_NAME, groupId = AppConstants.GROUP_ID)
	public void consume(String input) {
		String[] inputs = input.split(",");
		int id = Integer.parseInt(inputs[0]);
		int qty = Integer.parseInt(inputs[1]);
		repo.save(new Inventory(id,qty));
	}
	
	public InventoryDto updateInventory(int id, InventoryDto inventoryDto) {
		Inventory inventory = repo.findById(id).orElseThrow(()->new InventoryNotFoundException("inventory not found"));
		inventory.setQuantity(inventoryDto.getQuantity());
		inventory.setProductId(inventoryDto.getProductId());
		Inventory updateInventory = repo.save(inventory);
		return mapToDto(updateInventory);
	}
	
	public List<Inventory> getAll(){
		return repo.findAll();
	}
	
	public InventoryDto searchInventory(int id) {
		Inventory inventory = repo.findById(id).orElseThrow(()->new InventoryNotFoundException("inventory not found"));
		return mapToDto(inventory);
	}

	public void decreaseInventory(int id, int qty) {
		InventoryDto inv = searchInventory(id);
		if(inv.getQuantity()<qty || qty==0) {
			throw new ResourceNotFoundException("Quantity not found");
		}
		inv.setQuantity(inv.getQuantity()-qty);
		updateInventory(inv.getInventoryId(),inv);	
	}
	
	public ProdInvDto getInvWithProd(int inventoryId,int qty) {
		ProdInvDto productInvDto = new ProdInvDto();
		InventoryDto inv = searchInventory(inventoryId);
		Product product = pro.searchProduct(inv.getProductId());
		productInvDto = mapper.map(product, ProdInvDto.class);
		productInvDto.setInventoryId(inv.getInventoryId());
		productInvDto.setQuantity(qty);
		productInvDto.getInventoryId();
		productInvDto.getCartId();
		return productInvDto;
	}
}
