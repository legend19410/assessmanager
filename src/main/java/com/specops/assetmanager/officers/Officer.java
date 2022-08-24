package com.specops.assetmanager.officers;


import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.specops.assetmanager.leave.Leave;
import com.specops.assetmanager.roles.Role;

import lombok.Data;

import java.util.List;


/**
 * The persistent class for the officer database table.
 * 
 */
@Entity
@Table(name="officer")
@JsonIgnoreProperties({ "leaves" })
@Data
public class Officer {

	@Pattern(regexp = "^[A-Za-z]+$", message="First Name must only contain letters of the alphabet")
	@NotBlank(message="First Name cannot be blank")
	@Column(name="first_name", length=20, nullable=false)
	private String firstName;
	
	@Pattern(regexp = "^[A-Za-z]+$", message="Last Name must only contain letters of the alphabet")
	@NotBlank(message="Last Name cannot be blank")
	@Column(name="last_name", length=20, nullable=false)
	private String lastName;
	
	@Id
	@Column(name="reg_no")
	private Integer regNo;

	@Column(length = 20, nullable=false)
	private String rank;
	
	
	@Column(name="date_of_birth", nullable=false)
	private LocalDate dateOfBirth;
	
	
	@Column(name="enlistment_date", nullable=false)
	private LocalDate enlistmentDate;
	
	@Column(nullable=true)
	private String telephone;
	
	@Column(name="jcf_email", length = 50, nullable=true, unique=true)
	private String JCFEmail;
	
	@Column(name="email_password", nullable=false)
	private String emailPassword;

	@Column(nullable=true)
	private String address;
	

	@ManyToMany
	@JoinTable(
		name="officer_role"
		, joinColumns={
			@JoinColumn(name="officer_reg_no")
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_id")
			}
		)
	private List<Role> roles;
	
	

	@OneToMany(mappedBy="officer", cascade = CascadeType.ALL)
	private List<Leave> leaves;
	
	public boolean equals(Officer o) {
		if(o.getRegNo() == this.regNo) {
			return true;
		}else {
			return false;
		}
	}
	
	public Officer() {}

	public Officer(
			@Pattern(regexp = "^[A-Za-z]+$", message = "First Name must only contain letters of the alphabet") @NotBlank(message = "First Name cannot be blank") String firstName,
			@Pattern(regexp = "^[A-Za-z]+$", message = "Last Name must only contain letters of the alphabet") @NotBlank(message = "Last Name cannot be blank") String lastName,
			Integer regNo, String rank, LocalDate dateOfBirth, LocalDate enlistmentDate, String telephone,
			String jCFEmail, String emailPassword, String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.regNo = regNo;
		this.rank = rank;
		this.dateOfBirth = dateOfBirth;
		this.enlistmentDate = enlistmentDate;
		this.telephone = telephone;
		JCFEmail = jCFEmail;
		this.emailPassword = emailPassword;
		this.address = address;
	}

}