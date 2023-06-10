package com.example.demo.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int customerId;
	
	private String customerName;
	
	private String customerEmail;
	
	@Embedded
	@Column(name="customer_BillingAddress")
	@AttributeOverrides({
		@AttributeOverride(name="doorNo",column=@Column(name="billing_doorNo")),
		@AttributeOverride(name="street",column=@Column(name="billing_street")),
		@AttributeOverride(name="layout",column=@Column(name="billing_layout")),
		@AttributeOverride(name="city",column=@Column(name="billing_city")),
		@AttributeOverride(name="pincode",column=@Column(name="billing_pincode")),
		
	})
	private Address customerBillingAddress;
	
	@Embedded
	@Column(name="Shipping_Address")
	@AttributeOverrides({
		@AttributeOverride(name="doorNo",column=@Column(name="shipping_doorNo")),
		@AttributeOverride(name="street",column=@Column(name="shipping_street")),
		@AttributeOverride(name="layout",column=@Column(name="shipping_layout")),
		@AttributeOverride(name="city",column=@Column(name="shipping_city")),
		@AttributeOverride(name="pincode",column=@Column(name="shipping_pincode")),
	})
	private Address shippingAddress;
	
}
