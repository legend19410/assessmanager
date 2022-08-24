package com.specops.assetmanager.company;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.specops.assetmanager.platoon.Platoon;
import com.specops.assetmanager.roles.Role;

@Entity
@JsonIgnoreProperties({"roles"})
public class Company {

	@Id	
	@Column(length = 30)
	private String name;
	
	@OneToMany(mappedBy="company")
	private List<Role> roles;
	
	@OneToMany(mappedBy="company")
	private List<Platoon> platoons;
}

