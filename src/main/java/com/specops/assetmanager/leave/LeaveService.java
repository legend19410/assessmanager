package com.specops.assetmanager.leave;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.specops.assetmanager.officers.OfficerService;
import com.specops.assetmanager.roles.Role;

@Service
public class LeaveService {

	@Autowired
	private LeaveDAO leaveDAO;
	
	@Autowired
	private OfficerService officerService;
	
	public List<Leave> getAllLeave() {
		return (List<Leave>) leaveDAO.findAll();
	
	}
	
	public Leave getLeaveForOfficer(String type) {
		return null;
	}
	
	public List<Leave> getAllLeaveForOfficer() {
		return null;
	}
}
