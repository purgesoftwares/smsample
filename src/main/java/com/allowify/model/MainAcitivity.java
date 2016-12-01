package com.allowify.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MainAcitivity {
	
	@Id
	private String id;
	
	@DBRef
	private Code code;
	
	@DBRef
	private CodeEvent codeEvent;
	
	private int isPrivate;
	
	private int status = 1;
	
	@DBRef
	private User user;
	
	@DBRef
	private Set<Comment> comments = new HashSet<Comment>();
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	public Comment getComment(String commentString) {
		
		Comment comment1 = null;
		Iterator<Comment> commentIterator = comments.iterator();
		
		while(commentIterator.hasNext()) {
			Comment tmpCode = commentIterator.next();
			if(commentString.equals(tmpCode.getComment())) {
				comment1 = tmpCode;
				break;
			}
		}		
		return comment1;
	}
	
	public void addComment(Comment comment) {
		
		if(comments == null) {
			comments = new HashSet<Comment>();
		}
		comments.add(comment);
	}


	private int likesCount = 1;
	
	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(int isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public CodeEvent getCodeEvent() {
		return codeEvent;
	}

	public void setCodeEvent(CodeEvent codeEvent) {
		this.codeEvent = codeEvent;
	}

	public double[] getLatLong() {
		return latLong;
	}

	public void setLatLong(double[] latLong) {
		this.latLong = latLong;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	@GeoSpatialIndexed(name="GeoIndex_2d")
	private double[] latLong;
	
	private Date createDate;
	
	

}
