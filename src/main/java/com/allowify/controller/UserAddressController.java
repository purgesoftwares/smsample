package com.allowify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.allowify.model.UserAddress;
import com.allowify.repository.UserAddressRepositoryCustom;

@Controller
@RequestMapping("/address")
public class UserAddressController {
	
	@Autowired
	private UserAddressRepositoryCustom userAddressRepositoryCustom;
	
	@RequestMapping(method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public UserAddress create(@RequestBody UserAddress address) {
		
		address.setAddress1(address.getAddress1());
		address.setAddress2(address.getAddress2());
		address.setCity(address.getCity());
		address.setCountry(address.getCountry());
		address.setPinCode(address.getPinCode());
		address.setState(address.getState());
		
		userAddressRepositoryCustom.save(address);
		
		return address;
		
	}
	
	@RequestMapping( value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody UserAddress updateUserAddress(@PathVariable("id") String id,
											   @RequestBody UserAddress address) {
		UserAddress userAddress = userAddressRepositoryCustom.findOne(id);
		if(userAddress != null) {
			userAddress.setAddress1(address.getAddress1());
			userAddress.setAddress2(address.getAddress2());
			userAddress.setCity(address.getCity());
			userAddress.setCountry(address.getCountry());
			userAddress.setPinCode(address.getPinCode());
			userAddress.setState(address.getState());
			userAddressRepositoryCustom.save(userAddress);
		}
		
		return userAddress;
	}

}
