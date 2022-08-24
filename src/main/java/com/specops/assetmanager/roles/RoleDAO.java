package com.specops.assetmanager.roles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface RoleDAO extends CrudRepository<Role, Integer>{

	@Query(value="SELECT * FROM role WHERE type=:name", nativeQuery=true)
	Role getRoleByRoleName( String name);
	
}
