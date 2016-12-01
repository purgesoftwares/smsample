package com.allowify.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Share implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@NotEmpty(message="Can not be null")
	private String type;
	
	@NotEmpty(message="Can not be null")
	private String referenceId;
	
	@NotEmpty(message="Can not be null")
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	

}
