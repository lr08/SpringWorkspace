package com.example.demo.service.payload;

import lombok.Data;

@Data
public class ProdInvDto {

	int inventoryId;
	int productId;
	int quantity;
	int cartId;
	
	private String productName;
	private double productPrice;
}
