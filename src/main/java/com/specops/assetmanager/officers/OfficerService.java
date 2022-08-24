package com.specops.assetmanager.officers;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.specops.assetmanager.exceptions.DuplicateKeyException;
import com.specops.assetmanager.exceptions.InvalidJwtException;
import com.specops.assetmanager.exceptions.NoOfficerFoundException;
import com.specops.assetmanager.security.JwtUtil;

@Service
@Transactional
public class OfficerService implements UserDetailsService{
	
	@Autowired
	private OfficerRepository officerRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Officer officer = officerRepo.getOfficerByUsername(username);
		if(officer == null) {
			throw new UsernameNotFoundException("Officer not found in database");
		}
		Collection<SimpleGrantedAuthority> authorities  =  new ArrayList<SimpleGrantedAuthority>();
		officer.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getType()));
		});
		return new User(officer.getJCFEmail(), officer.getEmailPassword(), authorities);
	}
	
	
	public List<Officer> getAllOfficers(){
		return (List<Officer>) officerRepo.findAll();
	}
	
	public List<OfficerDto> getOfficersByCompany(List<String> roles){
		
		List<OfficerDto> officers = new ArrayList<OfficerDto>();
		for(String role : roles) {
			// should be a set
			List<OfficerDto> officersFound = officerRepo.getOfficers(role);
			if(officersFound != null) {
				officers.addAll(officersFound);
			}
		}
	
		return officers;
	}

	public List<OfficerDto> queryOfficers(String query, List<String> roles) {
		
		List<OfficerDto> officers = new ArrayList<OfficerDto>();
		for(String role : roles) {
			// should be a set
			List<OfficerDto> officersFound = officerRepo.search(query, role);
			if(officersFound != null) {
				officers.addAll(officersFound);
			}
		}
	
		return officers;
	}
	
	public OfficerDto getOfficerByRegNo(Integer regNo) throws NoOfficerFoundException{
		OfficerDto officer = officerRepo.findByRegNo(regNo);
		if(officer == null) {
			throw new NoOfficerFoundException("Officer Not Found in the Database");
		}
		return officer;
	}
	
	public OfficerDto getOfficerByUsername(String username) throws NoOfficerFoundException {
		OfficerDto officer = officerRepo.findByUsername(username);
		if(officer == null) {
			throw new NoOfficerFoundException("Officer Not Found in the Database");
		}
		return officer;
	}
	
	public Officer findByUsername(String username) throws NoOfficerFoundException {
		Officer officer = officerRepo.getOfficerByUsername(username);
		if(officer == null) {
			throw new NoOfficerFoundException("Officer Not Found in the Database");
		}
		return officer;
	}
	
	
	public String refreshMyToken(String bearerToken, String url) throws InvalidJwtException {
		
		String username = jwtUtil.extractVerifiedTokenOwner(bearerToken);
		UserDetails userDetails = this.loadUserByUsername(username);
		
		String newAccessToken = jwtUtil.createAccessToken(userDetails, url);
		return newAccessToken;
	}
	
	public void saveOfficer(Officer officer) throws DuplicateKeyException  {
		if(officerRepo.existsById(officer.getRegNo())) {
			throw new DuplicateKeyException("Reg No already exists!");
		}else {
			officerRepo.save(officer);
		}
	}
	
	
	public Officer updateOfficer(Integer regNo, Map<String, Object> updatedParams) throws NoOfficerFoundException {
		Officer officer = officerRepo.findById(regNo).orElseThrow(() -> 
			 new NoOfficerFoundException("Officer selected to be updated not found")
		);

		updatedParams.forEach((key, value)->{
			
			Field field = ReflectionUtils.findRequiredField(Officer.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, officer, value);
		});
		officerRepo.save(officer);
		return officer;
	}
	
	
	public void deleteOfficer(int regNo) {
		officerRepo.deleteById(regNo);	
	}
	

//	public Page<Officer> getOfficerPage(Pageable page) {
//		return (Page<Officer>) officerRepo.findAll(page);
//	}
	
	public Page<Officer> getOfficerPage(Pageable page){
		
//		Pageable page = PageRequest.of(pageNumber, pageSize);
		return officerRepo.findAll(page);
	}


	
}
