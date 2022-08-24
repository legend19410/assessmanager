package com.specops.assetmanager.officers;

import java.io.UnsupportedEncodingException;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.specops.assetmanager.exceptions.DuplicateKeyException;
import com.specops.assetmanager.exceptions.InvalidJwtException;
import com.specops.assetmanager.exceptions.NoOfficerFoundException;
import com.specops.assetmanager.exceptions.UnauthorizedException;
import com.specops.assetmanager.roles.Role;
import com.specops.assetmanager.security.AuthenticationResponse;
import com.specops.assetmanager.security.AuthorizationManager;
import com.specops.assetmanager.security.DecodedToken;

@RestController
@RequestMapping("/api")
public class OfficerController {
	

	@Autowired
	private OfficerService officerService;
	
	@Autowired
	private AuthorizationManager authorizationManager;
	
	
	@GetMapping("lvl1/officers")
	public List<OfficerDto> getOfficers(@RequestHeader(name="Authorization") String encodedToken, @RequestHeader(name="Formations") String formations) throws UnsupportedEncodingException, NoOfficerFoundException{
		
		List<String> verifiedRoles = authorizationManager.getAuthorizedCoys(encodedToken, formations);
		List<OfficerDto> allOfficers = officerService.getOfficersByCompany(verifiedRoles);
		return allOfficers;
	}
	
	
	@GetMapping("lvl1/officer/{regNo}")
	public OfficerDto getOfficerByRegNo(@PathVariable Integer regNo, @RequestHeader(name="Authorization") String encodedToken) throws NoOfficerFoundException, UnsupportedEncodingException, UnauthorizedException {
		
		OfficerDto target =  officerService.getOfficerByRegNo(regNo);
		boolean authorized = authorizationManager.verifyAuthorization(encodedToken, target);
		
		if(authorized) return target;
		else throw new UnauthorizedException("You are not authorized to get officer requested");
		
	}
	
	@GetMapping("lvl1/officer/search")
	public List<OfficerDto> getOfficers(@RequestParam String query, @RequestHeader(name="Authorization") String encodedToken, @RequestHeader(name="Formations") String formations) throws UnsupportedEncodingException, NoOfficerFoundException {
		List<String> verifiedRoles = authorizationManager.getAuthorizedCoys(encodedToken, formations);
		return officerService.queryOfficers(query, verifiedRoles);
	}
	
	@PostMapping("lvl3/officer")
	public Officer save(@Valid @RequestBody Officer officer, @RequestHeader(name="Authorization") String encodedtoken) throws DuplicateKeyException, UnsupportedEncodingException, NoOfficerFoundException, UnauthorizedException {
		DecodedToken decodedToken = DecodedToken.getDecoded(encodedtoken);
		String username = decodedToken.sub;
		Officer user = officerService.findByUsername(username);
		List<Role> roles = user.getRoles();
		boolean authorized = false;
		if(roles!=null) {
			for(Role role: roles) {
				if(role.getLevel()>=3) {
					officerService.saveOfficer(officer);
					authorized = true;
					break;
				}
			}
			if(!authorized) {
				throw new UnauthorizedException("Your are not authorized to create and save officers");
			}else {
				return officer;
			}
		}else {
			throw new UnauthorizedException("Your are not authorized to create and save officers");
		}
	}
	
	
	@PatchMapping("lvl2/officer/{regNo}")
	public Officer updateOfficer(@PathVariable Integer regNo, @RequestBody Map<String, Object> updatedParams, @RequestHeader(name="Authorization") String encodedtoken) throws NoOfficerFoundException, UnauthorizedException, UnsupportedEncodingException{
		DecodedToken decodedToken = DecodedToken.getDecoded(encodedtoken);
		String username = decodedToken.sub;
		Officer user = officerService.findByUsername(username);
		List<Role> roles = user.getRoles();
		boolean authorized = false;
		if(roles!=null) {
			for(Role role: roles) {
				if(role.getLevel()>=4) {
					return officerService.updateOfficer(regNo, updatedParams);
				}
			}
			if(!authorized) {
				throw new UnauthorizedException("Your are not authorized to create and save officers");
			}
		}else {
			throw new UnauthorizedException("Your are not authorized to create and save officers");
		}
		return officerService.updateOfficer(regNo, updatedParams);
	}
	
	@DeleteMapping("lvl4/officer/{regNo}")
	public String delete(@PathVariable int regNo, @RequestHeader(name="Authorization") String encodedtoken) throws UnsupportedEncodingException, NoOfficerFoundException, UnauthorizedException{
		DecodedToken decodedToken = DecodedToken.getDecoded(encodedtoken);
		String username = decodedToken.sub;
		Officer user = officerService.findByUsername(username);
		boolean authorized = false;
		if(user!=null) {
			List<Role> roles = user.getRoles();
			
			for(Role role: roles) {
				if(role.getLevel()>=4) {
					officerService.deleteOfficer(regNo);
					authorized = true;
					break;
				}
			}
			if(!authorized) {
				throw new UnauthorizedException("You are not authorized to delete officer");
			}
		}else {
			throw new NoOfficerFoundException("Couldn't find you credentials to perform operation requested");
		}
		
		return "officer deleted"; 
	}

	
	@GetMapping("lvl1/officer/username/{username}")
	public OfficerDto getByUsername(@PathVariable String username) throws NoOfficerFoundException {
		return officerService.getOfficerByUsername(username);
	}
	
	@GetMapping("lvl1/user")
	public OfficerDto get(@RequestHeader (name="Authorization") String encodedtoken) throws UnsupportedEncodingException, NoOfficerFoundException {
		DecodedToken decodedToken = DecodedToken.getDecoded(encodedtoken);
		String username = decodedToken.sub;
		return officerService.getOfficerByUsername(username);
	}

	@GetMapping("lvl1/officer/refresh-token")
	public AuthenticationResponse refreshMyToken(HttpServletRequest request, HttpServletResponse response) throws InvalidJwtException{
		String bearerToken = request.getHeader("Authorization");
		String url = request.getRequestURL().toString();
		String newAccessToken = officerService.refreshMyToken(bearerToken, url);
		String refreshToken = bearerToken.substring("Bearer ".length());
		return new AuthenticationResponse(newAccessToken, refreshToken);
	}
		
//	@GetMapping("lvl4/officers")
//	public List<Officer> getOfficers() {
//		return officerService.getAllOfficers(); 
//	}
//	
	@GetMapping("lvl4/officers")
	public Page<Officer> getOfficers(Pageable page) {
		return officerService.getOfficerPage(page); 
	}
	
}
	
