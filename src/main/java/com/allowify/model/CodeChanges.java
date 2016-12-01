package com.allowify.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CodeChanges implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String previousCode;
	
	private String nextCode;
	
	private Date createDate;
	
	public String getPreviousCode() {
		return previousCode;
	}

	public void setPreviousCode(String previousCode) {
		this.previousCode = previousCode;
	}

	public String getNextCode() {
		return nextCode;
	}

	public void setNextCode(String nextCode) {
		this.nextCode = nextCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


}
