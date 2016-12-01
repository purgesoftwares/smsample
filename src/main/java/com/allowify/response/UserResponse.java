package com.allowify.response;

import com.allowify.model.User;

/**
 * 
 * @author tonyacunar
 *
 * wrapper for user
 *
 */
public class UserResponse {

	private final User user;

	public UserResponse(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	
	
}
