package com.allowify.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@CompoundIndexes({
	@CompoundIndex(name = "likes", def = "{'user_id' : 1, 'reference_id' : 1}")
})
@Document
public class Like implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@NotEmpty(message = "Entity should not be blank.")
	private String referenceId;
	
	// 1 = code, 2 = activity, 3 = comment  4=event
	@NotNull(message = "Entity Type should not be null.")
	private int type;
	
	@NotEmpty(message = "User Id should not be blank.")
	private String userId;
	
	private Date createDate;

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String reference_id) {
		this.referenceId = reference_id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
