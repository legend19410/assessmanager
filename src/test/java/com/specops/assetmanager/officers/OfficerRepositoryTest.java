package com.specops.assetmanager.officers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OfficerRepositoryTest {
	
	@Autowired
	private OfficerRepository officerRepo;

	@Test
	void itShouldCheckExist() {
		
		//given
		Officer officer = new Officer("Milton", "LastName", 23, "cpl", LocalDate.of(2020, 1, 8), LocalDate.of(2020, 1, 8), "12345", "legend@gmail.com", "vvv", "planet earth");
		officerRepo.save(officer);
		
		//when
		
		Officer o = officerRepo.getOfficerByUsername("legend@gmail.com");
		boolean exists = o.getJCFEmail().equals("legend@gmail.com");
		

		//then
		
		AssertionsForClassTypes.assertThat(exists).isTrue();
	}

}
