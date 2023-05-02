package com.example.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductDto;
import com.example.demo.kafka.AppConstants;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repo;
	
	private ModelMapper mapper;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public ProductService(ProductRepository repo, ModelMapper mapper, KafkaTemplate<String, String> kafkaTemplate) {
		super();
		this.repo = repo;
		this.mapper = mapper;
		this.kafkaTemplate = kafkaTemplate;
	}

	private ProductDto mapToDto(Product product) {
		ProductDto Prod = mapper.map(product, ProductDto.class);
		return Prod;
	}
	
	private Product mapToEntity(ProductDto prod) {
		Product Prod = mapper.map(prod, Product.class);
		return Prod;
	}
	
	public ProductDto sendProduct(ProductDto prod) {
		Product product = mapToEntity(prod);
		Product addedProduct = repo.save(product);
		ProductDto response = mapToDto(addedProduct);
		String inventoryInput = addedProduct.getId()+","+prod.getQuantity();
		kafkaTemplate.send(AppConstants.TOPIC_NAME,inventoryInput);
		return response;
	}

}
