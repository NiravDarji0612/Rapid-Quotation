package com.rapidquotation.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class RepairingQuotationItems {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int items_id;
	
	private String materialDescription;
	
	private int quantity;
	
	private String unit;
	
	private int price;
	
	public int getId() {
		return items_id;
	}
	public void setId(int items_id) {
		this.items_id = items_id;
	}
	
	public String getMaterialDescription() {
		return materialDescription;
	}
	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
