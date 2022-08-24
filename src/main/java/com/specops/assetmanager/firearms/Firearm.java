package com.specops.assetmanager.firearms;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Table(name="firearm")
public class Firearm {

	
	@Id
	private String serialNo;
	
	@NotBlank(message="make cannot be blank")
	private String brand;

	@NotBlank(message="make cannot be blank")
	private String type;
		
	
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}	
	
}