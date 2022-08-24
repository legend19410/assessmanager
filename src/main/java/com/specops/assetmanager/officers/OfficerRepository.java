package com.specops.assetmanager.officers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.specops.assetmanager.roles.Role;



public interface OfficerRepository extends JpaRepository<Officer, Integer>{
	
	@Query(value="SELECT * FROM officer WHERE jcf_email=:username", nativeQuery=true)
	Officer getOfficerByUsername( String username);
	
	@Query(value="SELECT o.reg_no AS regNo, o.rank, o.first_name AS firstName, o.last_name AS lastName, "
			+ "o.telephone, o.date_of_birth as dateOfBirth, o.enlistment_date AS enlistmentDate, o.jcf_email AS JCFEmail, o.address, p.name AS platoon, c.name AS company FROM officer o "
			+ "JOIN officer_platoon_assign opa ON opa.officer_reg_no=o.reg_no "
			+ "JOIN platoon p ON p.id=opa.id JOIN company c ON c.name=p.company"
			+ " WHERE o.jcf_email=:username", nativeQuery=true)
	OfficerDto findByUsername(@Param("username") String username);
	
	@Query(value="SELECT DISTINCT o.reg_no AS regNo, o.rank, o.first_name AS firstName, o.last_name AS lastName, o.telephone, p.name"
			+ " AS platoon, c.name AS company FROM role r JOIN company c ON r.company=c.name "
			+ "JOIN platoon p ON c.name=p.company JOIN officer_platoon_assign opa ON opa.id=p.id "
			+ "JOIN officer o ON o.reg_no=opa.officer_reg_no WHERE o.first_name LIKE %:query% OR o.last_name LIKE %:query% AND r.type=:role", nativeQuery=true)
	List<OfficerDto> search(@Param("query") String query, @Param("role") String role);

	@Query(value="SELECT o.reg_no AS regNo, o.rank, o.first_name AS firstName, o.last_name AS lastName, "
			+ "o.telephone, o.date_of_birth as dateOfBirth, o.enlistment_date AS enlistmentDate, o.jcf_email AS JCFEmail, o.address, p.name AS platoon, c.name AS company FROM officer o "
			+ "JOIN officer_platoon_assign opa ON opa.officer_reg_no=o.reg_no "
			+ "JOIN platoon p ON p.id=opa.id JOIN company c ON c.name=p.company"
			+ " WHERE o.reg_no=:regNo", nativeQuery=true)
	OfficerDto findByRegNo(@Param("regNo") int regNo);
	
	@Query(value="SELECT o.reg_no AS regNo, o.rank, o.first_name AS firstName, o.last_name AS lastName, o.telephone, p.name"
			+ " AS platoon, c.name AS company FROM role r JOIN company c ON r.company=c.name "
			+ "JOIN platoon p ON c.name=p.company JOIN officer_platoon_assign opa ON opa.id=p.id "
			+ "JOIN officer o ON o.reg_no=opa.officer_reg_no WHERE r.type=:role", nativeQuery=true)
	List<OfficerDto> getOfficers(@Param("role") String role);
}
