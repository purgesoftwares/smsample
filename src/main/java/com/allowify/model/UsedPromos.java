package com.allowify.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UsedPromos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String userId;
	
	private String promoCode;
	
	private Date created;
	
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

	public Date getCreated() {
		return created;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	

}
