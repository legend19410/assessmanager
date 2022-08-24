package com.specops.assetmanager.platoon;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class OfficerPlatoonAssignPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="officer_reg_no")
	Integer OfficerRegNo;
	
	@Column(name="id")
	Integer id;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOfficerRegNo() {
		return OfficerRegNo;
	}

	public void setOfficerRegNo(Integer officerRegNo) {
		OfficerRegNo = officerRegNo;
	}

}