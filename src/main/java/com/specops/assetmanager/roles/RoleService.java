package com.specops.assetmanager.roles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RoleService {
	
	@Autowired
	private RoleDAO roleRepo;
	
	
	public Role getRoleByName(String roleName) {
		return roleRepo.getRoleByRoleName(roleName);
	}
	
	
	
}
