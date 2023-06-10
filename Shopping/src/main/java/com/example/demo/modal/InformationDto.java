package com.example.demo.modal;

import java.util.List;

import lombok.Data;

@Data
public class InformationDto {

	private String customerName;
	private String customerEmail;
	private Address customerBillingAddress;
	private List<ProdInvDto> prodInfo;
	private Double totalAmount;
}
