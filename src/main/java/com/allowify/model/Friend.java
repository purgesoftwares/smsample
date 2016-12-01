package com.allowify.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Friend implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String userId;
	
	private String anotherUserId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAnotherUserId() {
		return anotherUserId;
	}

	public void setAnotherUserId(String anotherUserId) {
		this.anotherUserId = anotherUserId;
	}
	

}
