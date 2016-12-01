package com.zippi.test.code;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.allowify.model.User;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.response.UserResponse;
import com.zippi.places.GooglePlaces.UserAccessRequest;
import com.zippi.test.ZippiTestSuite;

public class UserControllerTest extends ZippiTestSuite {
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;
	
	@Test
	public void testUserCreate() throws Exception {
		
		User responseUser  =  createUser();
		User queriedUser = userRepositoryCustom.findByEmail(responseUser.getEmail());
		
		assertEquals(responseUser.getEmail(), queriedUser.getEmail());
	}
	
	
	@Test
	public void testFindOneUser() throws Exception{
		
		User responseUser  =  createUser();
		User queriedUser = userRepositoryCustom.findByEmail(responseUser.getEmail());
		
		assertEquals(responseUser.getEmail(), queriedUser.getEmail());
					
	}
	
	private User createUser() {
		User user = new User();
		user.setEmail("zzzyz@gmail.com");
		user.setNewPassword("123456");
		user.setFirstName("Test");
		user.setLastName("aa");
		user.setPhoneNumber("233333");
				
		User responseUser = 
				restTemplate.postForObject(userControllerURL.toString()+ "/create", user, User.class);
		return responseUser;
		
	}
	
	@Test
	public void testUpdateUser() throws Exception{

		User addUser = createUser();
		User queriedUser = userRepositoryCustom.findByEmail(addUser.getEmail());
					
		if(queriedUser != null) {
			addUser.setEmail("updatezzee889@gmail.com");
			addUser.setNewPassword("upddate123456");
			addUser.setFirstName("Test");
			addUser.setLastName("update");
			addUser.setPhoneNumber("23903333");
			addUser = restTemplate.postForObject(
					userControllerURL.toString()+ "/create", addUser, User.class);
		}
		
		assertNotEquals(queriedUser.getEmail(), addUser.getEmail());
		

		
	}
	
	@Test
	public void testGetUser() throws Exception {
		User addUser = createUser();
		User queriedUser = userRepositoryCustom.findByEmail(addUser.getEmail());
		
		assertEquals(addUser.getEmail(), queriedUser.getEmail());
	}
	
	
	@Test
	public void testUserAuthorize() throws Exception {
		UserAccessRequest userAccessRequest = new UserAccessRequest();
		userAccessRequest.setUserName("shankar@mailinator.com");
		userAccessRequest.setPassword("123456");
		
		UserResponse responseUser = restTemplate.postForObject
				(userControllerURL.toString()+ "/authorize", userAccessRequest, UserResponse.class);
		System.out.println("here..!!!");
		User requestingUser = userRepositoryCustom.findByUserName(userAccessRequest.getUserName());
		
		assertEquals(responseUser.getUser().getUserName(), requestingUser.getUserName());
	}

}
