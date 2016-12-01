/** Copyright © 2015 by 1986 Tech, LLC. All Rights Reserved. **/

package com.allowify.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class UserRole{
 
	@Id
	private String id;
	
	@JsonIgnore 
	private User user;
	
	private String role;

	public UserRole() {
	}
 
	public UserRole(User user, String role) {
		this.user = user;
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}
 
	public void setUser(User user) {
		this.user = user;
	}
 
	public String getRole() {
		return this.role;
	}
 
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserRole [id=" + id + ", role=" + role + "]";
	}	
 
}
