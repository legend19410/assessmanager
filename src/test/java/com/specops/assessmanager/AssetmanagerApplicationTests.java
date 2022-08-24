package com.specops.assessmanager;

import org.junit.jupiter.api.Test;
import  org.assertj.core.api.AssertionsForClassTypes;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.specops.assetmanager.AssetmanagerApplication;

@ContextConfiguration(classes = AssetmanagerApplication.class)
@SpringBootTest
class AssetmanagerApplicationTests {
	
	Calculator cal = new Calculator();

	@Test
	void itShouldAddNumbers() {
		
		//given
		int numberOne = 20;
		int numberTwo = 30;
		
		//when
		int result = cal.add(numberOne, numberTwo);
		
		
		//then 
		AssertionsForClassTypes.assertThat(result).isEqualTo(51);
	}
	
	class Calculator{
		int add(int a, int b) {
			return a + b;
		}
	}

}
