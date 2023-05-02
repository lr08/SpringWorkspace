package com.example.demo.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Inventory;
import com.example.demo.kafka.AppConstants;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.service.entity.InventoryDto;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository repo;
	
	private ModelMapper mapper;
	
	public InventoryService(InventoryRepository repo, ModelMapper mapper) {
		super();
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
	
	public List<Inventory> getAll(){
		return repo.findAll();
	}

}