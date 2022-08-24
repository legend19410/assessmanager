package com.specops.assetmanager.roles;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import com.specops.assetmanager.company.Company;

import lombok.Data;


@Entity
@Data
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String type;
	
	private int level;
	
	@ManyToOne
	@JoinColumn(name="company")
	private Company company;
	
}
