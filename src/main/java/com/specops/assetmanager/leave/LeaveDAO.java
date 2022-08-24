package com.specops.assetmanager.leave;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface LeaveDAO extends CrudRepository<Leave, LeaveId> {
	
	@Query(value="SELECT * FROM Leave", nativeQuery=true)
	List<Leave> getAll();
	
	@Query(value="SELECT * FROM Leave WHERE officer_reg_no=:officerRegNo AND start_date=:startDate AND type=:type", nativeQuery=true)
	Leave get(@Param("officerRegNo") int officerRegNo, @Param("startDate") String startDate, @Param("type") String type);
	
	
//	@Modifying
//	@Transactional
//	@Query(value="INSERT INTO leave VALUES (:officerRegNo, :date, :duty)", nativeQuery=true)
//	void saveSchedule(@Param("officerRegNo")int officerRegNo, @Param("date")String date, @Param("duty")String duty);

}
