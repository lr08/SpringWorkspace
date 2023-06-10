package com.example.demo.service.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryDto {

	int inventoryId;
	int productId;
	int quantity;
}
