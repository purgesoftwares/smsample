package com.allowify.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UploadedFile implements Serializable{
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String filename;
	
	private Date lastUpdate;
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


}
