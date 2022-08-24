package com.specops.assetmanager.firearms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specops.assetmanager.roles.Role;



@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class FirearmController {
	

	@Autowired
	private FirearmService firearmService;
	
//	@GetMapping("/radios")
//	public List<Radio> getRadios() {
//		return radioService.getAllRadios();
//		
//	}
//	
	
	
	@GetMapping("firearm/{serialNo}")
	public ResponseEntity<Optional<Firearm>> getFirearm(@PathVariable String serialNo) {
		Optional<Firearm> firearm = firearmService.getFirearm(serialNo);
		return ResponseEntity.ok(firearm);
	}
	
	@PostMapping("/firearm")
	public Firearm save(@Valid @RequestBody Firearm firearm){
		firearmService.saveFirearm(firearm);
		return firearm;
	}
	
	
	@DeleteMapping("/firearm/{serialNo}")
	public String delete(@PathVariable String serialNo){
		firearmService.deleteRadio(serialNo);
		return "firearm deleted"; 
	}
	

}
