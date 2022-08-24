package com.specops.assetmanager.firearms;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specops.assetmanager.officers.OfficerService;
import com.specops.assetmanager.roles.Role;
import com.specops.assetmanager.roles.RoleService;

@Service
@Transactional
public class FirearmService{
	
	
	@Autowired
	private FirearmDAO firearmDAO;
	
	@Autowired
	private OfficerService officerService;

	
	public List<Firearm> getAllFirearms(){
		return (List<Firearm>) firearmDAO.findAll();
	}
	
	
	
	public Optional<Firearm> getFirearm(String serialNo) {
		
		return firearmDAO.findById(serialNo);
	}
	
	

	
	public void saveFirearm(Firearm firearm) {
		firearmDAO.save(firearm);
	}
	
	
	
	public void deleteRadio(String serialNo) {
		firearmDAO.deleteById(serialNo);	
	}
	

}
