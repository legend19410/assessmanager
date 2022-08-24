package com.specops.assetmanager.vehicles;

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
import org.springframework.data.domain.Page;
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
import com.specops.assetmanager.exceptions.NoOfficerFoundException;
import com.specops.assetmanager.officers.OfficerService;
import com.specops.assetmanager.roles.Role;
import com.specops.assetmanager.roles.RoleService;

import org.springframework.data.domain.Pageable;

@Service
@Transactional
public class VehicleService{
	
	
	@Autowired
	private VehicleDAO vehicleDAO;
	
	@Autowired
	private OfficerVehicleAssignDAO officerVehicleAssignDAO;
	
	@Autowired
	private OfficerService officerService;
	
	
	public List<Vehicle> getAllVehicles(){
		return (List<Vehicle>) vehicleDAO.findAll();
	}
	
	public Page<OfficerVehicleAssign> getOfficerVehicleAssignments(Pageable page){
		return (Page<OfficerVehicleAssign>) officerVehicleAssignDAO.findAll(page);
	}
	
	
	
	public Optional<Vehicle> getVehicle(String plate) {
		
		return vehicleDAO.findById(plate);
	}
	
	
	
	
	
	public void saveVehicle(Vehicle vehicle) {
		vehicleDAO.save(vehicle);
	}
	
	
	public Vehicle updateVehicle(String licensePlate, Map<String, Object> updatedParams) throws NoOfficerFoundException {
		Vehicle vehicle = vehicleDAO.findById(licensePlate).orElseThrow(() -> 
			 new NoOfficerFoundException("Officer selected to be updated not found")
		);

		updatedParams.forEach((key, value)->{
			
			Field field = ReflectionUtils.findRequiredField(Vehicle.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, vehicle, value);
		});
		vehicleDAO.save(vehicle);
		return vehicle;
	}
	
	
	public void deleteVehicle(String licensePlate) {
		vehicleDAO.deleteById(licensePlate);	
	}
	

}
