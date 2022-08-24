package com.specops.assetmanager.roles;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.specops.assetmanager.exceptions.NoOfficerFoundException;
import com.specops.assetmanager.officers.OfficerDto;
import com.specops.assetmanager.officers.OfficerService;
import com.specops.assetmanager.security.AuthorizationManager;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	
//	@GetMapping("lvl1/role/officer")
//	public Role List<Role> getRole(@PathVariable Integer regNo){
//		
//	}
//	

}
