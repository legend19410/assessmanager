package com.specops.assetmanager.platoon;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.specops.assetmanager.officers.Officer;


@Entity
@Table(name="officer_platoon_assign")
@JsonIgnoreProperties({ "officer", "platoon" })
public class OfficerPlatoonAssign {
	
	@EmbeddedId
	private OfficerPlatoonAssignPK id;
	
	@ManyToOne
	@MapsId("id")
	@JoinColumn(name="id")
	private Platoon platoon;
	
	@ManyToOne
	@MapsId("officerRegNo")
	@JoinColumn(name="officer_reg_no")
	private Officer officer;
	
	
	public OfficerPlatoonAssignPK getId() {
		return id;
	}

	public void setId(OfficerPlatoonAssignPK id) {
		this.id = id;
	}

	public Platoon getPlatoon() {
		return platoon;
	}

	public void setPlatoon(Platoon platoon) {
		this.platoon = platoon;
	}

	public Officer getOfficer() {
		return officer;
	}

	public void setOfficer(Officer officer) {
		this.officer = officer;
	}

}
