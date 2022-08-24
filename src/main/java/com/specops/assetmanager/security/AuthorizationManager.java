package com.specops.assetmanager.security;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.specops.assetmanager.exceptions.NoOfficerFoundException;
import com.specops.assetmanager.exceptions.UnauthorizedException;
import com.specops.assetmanager.officers.Officer;
import com.specops.assetmanager.officers.OfficerDto;
import com.specops.assetmanager.officers.OfficerService;
import com.specops.assetmanager.roles.Role;

@Component
public class AuthorizationManager {
	
	@Autowired
	private OfficerService officerService;
	
	public List<String> getAuthorizedCoys(String encodedToken, String formations) throws NoOfficerFoundException, UnsupportedEncodingException {
		String username = this.decodeUsername(encodedToken);
		List<String> givenRoles = new ArrayList<String>(Arrays.asList(formations.split(" ")));
		List<String> officerRoleTypes = new ArrayList<String>();
		
		//get user by username
		Officer officer = officerService.findByUsername(username);
		
		//get user's roles
		
			List<Role> officerRoles = officer.getRoles();
			//check if roles matches what is being asked for
			officerRoles.forEach(role->{
				officerRoleTypes.add(role.getType());
			});
		
		
		//give only what roles allow by using the filter method
		List<String> verifiedRoles = givenRoles.stream().filter(role->{
			return officerRoleTypes.contains(role);
		}).collect(Collectors.toList());

		return verifiedRoles;
		
	}
	
	public boolean verifyAuthorization(String encodedToken, OfficerDto target) throws UnsupportedEncodingException, NoOfficerFoundException {
		
		String username = this.decodeUsername(encodedToken);
		
		//get officer 
		Officer officer = officerService.findByUsername(username);
		
		//get roles
		if(officer != null) {
			List<Role> roles = officer.getRoles();
			if(roles != null) {
				List<String> roleTypes = roles.stream().map(role->{
					return role.getType();
				}).collect(Collectors.toList()) ;
				if(roleTypes.contains(target.getCompany())){
					return true;
				}
			}
		}
		return false;
	}
	
	public String decodeUsername(String encodedToken) throws UnsupportedEncodingException {
		DecodedToken decodedToken = DecodedToken.getDecoded(encodedToken);
		String username = decodedToken.sub;
		return username;
	}

}
