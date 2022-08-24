package com.specops.assetmanager.officers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.specops.assetmanager.roles.Role;


class OfficerAdditionalDetails {
	
//	 Integer id;	
//	 String firstName;
//	 String lastName;
//	 Integer regNo;
	 String rank;
//	 LocalDate dateOfBirth;
//	 LocalDate enlistmentDate;
//	 String telephone;
//	 String jcfemail;
//	 String emailPassword;
//	 String address;
//	 List<Role> roles;
	public OfficerAdditionalDetails(String rank) {
		this.rank = rank;
	}
	public String getRank() {
		return this.rank;
	}
	
	
}

//interface OfficerDto{
//	String getRank();
//}

