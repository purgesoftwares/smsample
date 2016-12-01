package com.zippi.test.checkIn;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.allowify.model.CheckIn;
import com.allowify.repository.CheckInRepositoryCustom;
import com.zippi.test.ZippiTestSuite;

public class CheckInControllerTest extends ZippiTestSuite {
	
	@Autowired
	private CheckInRepositoryCustom checkInRepositoryCustom;
	
	@Test
	public void testAddCheckIn() throws Exception {
		
		CheckIn quriedCheckIn = createCheckIn();
		CheckIn responseCheckIn = checkInRepositoryCustom.findById(quriedCheckIn.getId());
		
		assertEquals(quriedCheckIn.getId(), responseCheckIn.getId());
		
	}
	
	private CheckIn createCheckIn() {
		
		CheckIn checkIn = new CheckIn();
		checkIn.setCode("00CXW");
		checkIn.setUserId("56cefebbe4b03a5be76f4222");
		checkIn.setDate(new Date());
		
		return restTemplate.postForObject(
				checkInURL.toString()+ "/create",checkIn, CheckIn.class);
		
	}
	
	@Test
	public void testUpdateCheckIn() throws Exception {
		CheckIn quriedCheckIn = createCheckIn();
		
		CheckIn responseCheckIn = checkInRepositoryCustom.findById(quriedCheckIn.getId());
		
		if(responseCheckIn != null) {
			responseCheckIn.setCode("00CXX");
			responseCheckIn.setDate(new Date());
			checkInRepositoryCustom.save(responseCheckIn);
			
		}
		
		assertNotEquals(quriedCheckIn.getCode(), responseCheckIn.getCode());
	}

}
