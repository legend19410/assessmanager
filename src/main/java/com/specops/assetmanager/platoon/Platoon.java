package com.specops.assetmanager.platoon;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.specops.assetmanager.company.Company;

@Entity
@JsonIgnoreProperties({ "officers","roles","vehicles","radios","firearms"})
public class Platoon {

	@Id	
	private Integer id;
	
	@Column(length = 30)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="company")
	private Company company;
	
}