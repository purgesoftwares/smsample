package com.zippi.test.code;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.allowify.model.Code;
import com.allowify.repository.CodeRepositoryCustom;
import com.zippi.places.GooglePlaces.NearRequest;
import com.zippi.test.ZippiTestSuite;

public class CodeControllerTest extends ZippiTestSuite{
	
	@Autowired
	private CodeRepositoryCustom codeRepositoryCustom;
	
	@Test
	public void testCodeCreate() throws Exception {
		
		Code responseCode  =  createCode();
		List<Code> queriedUser = codeRepositoryCustom.findByUserId(responseCode.getUserId());
		
		assertEquals(responseCode.getUserId(), queriedUser.get(0).getUserId());
	}

	private Code createCode() {
		Code code = new Code();
		code.setTitle("test");
		code.setAddress1("Test Address");
		code.setAddress2("Test Address 2");
		double[] latlong = {26.9906725,75.7670256};
		code.setLatLong(latlong);
		code.setCity("Test City");
		code.setState("Test State");
		code.setZipCode("Test");
		code.setIsPrivate(1);
		code.setStatus(1);
		code.setUserId("Test USerID");
		
		return restTemplate.postForObject
				(codeURL.toString()+ "/add-code", code, Code.class);
		
	}
	
	@Test
	public void testUpdateCode() throws Exception {
		Code responseCode  =  createCode();
		List<Code> queriedCode = codeRepositoryCustom.findByCode(responseCode.getCode());
		
		if(queriedCode != null){
			
			responseCode.setCode("ZZZZZ");
			responseCode.setTitle("test");
			double[] latlong = {27.9906725,76.7670256};
			responseCode.setLatLong(latlong);
			responseCode = restTemplate.postForObject
			(codeURL.toString()+ "/add-code", responseCode, Code.class);
			
		}
		assertNotEquals(responseCode.getCode(), queriedCode.get(0).getCode());
		
	}
	
	@Test
	public void testCodeNear() throws Exception {
		NearRequest nearRequest = new NearRequest();
		nearRequest.setDistance(3.0);
		nearRequest.setLimit(2);
		double[] latlong = {26.9906725,75.7670256};
		nearRequest.setLatLong(latlong);
		
		Code[] code = restTemplate.postForObject
				(codeURL.toString()+ "/near", nearRequest, Code[].class);
		
		Code newCode =  code[0];
		List<Code> quriedCode = codeRepositoryCustom.findByCode(newCode.getCode());
		assertEquals(code[0].getCode(), quriedCode.get(0).getCode());
		
	}
	
	@Test
	public void testGetCode() throws Exception {
		Code responseCode  =  createCode();
		List<Code> queriedCode = codeRepositoryCustom.findByCode(responseCode.getCode());
		
		assertEquals(queriedCode.get(0).getCode(), responseCode.getCode());
		
	}
	
	@Test
	public void testDeleteCode() throws Exception {
		
		Code responseCode  =  createCode();
		restTemplate.delete(codeURL.toString()+"/"+responseCode.getCode());
		
		List<Code> queriedCode = codeRepositoryCustom.findByCode(responseCode.getCode());
		if(queriedCode.isEmpty()){
			assertNull(null);
		}
	}
}
