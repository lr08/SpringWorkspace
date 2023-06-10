package com.example.demo.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	

	private int id;
	private String name;
	private String description;
	private double productPrice;
	private int productQuantity;

}
