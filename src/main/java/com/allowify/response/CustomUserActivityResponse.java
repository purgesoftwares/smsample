package com.allowify.response;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.allowify.model.Code;
import com.allowify.model.User;
import com.allowify.model.UserActivity;

public class CustomUserActivityResponse {
	
	private String id;
	
	private String description;
	
	private String title;
	
	private Date createDate;
	
	private Date updateDate;
	
	private String userId;
		
	private User user;

	private Code code;
	
	private final List<CustomCommentResponse> comments;
	
	private boolean isLiked; 
	
	public CustomUserActivityResponse(String id,
			String description,
			String title,
			Date createDate,
			Date updateDate,
			String userId,
			User user,
			Code code,
			final List<CustomCommentResponse> comments) {
		
		this.id 			= id;
		this.description 	= description;
		this.title 			= title;
		this.createDate 	= createDate;
		this.updateDate 	= updateDate;
		this.userId 		= userId;
		this.setUser(user);
		this.setCode(code);
		this.comments 		= comments;
		
	}
	
	public CustomUserActivityResponse(UserActivity userActivity,
			final List<CustomCommentResponse> comments) {
		
		this.id 			= userActivity.getId();
		this.description 	= userActivity.getDescription();
		this.title 			= userActivity.getTitle();
		this.createDate 	= userActivity.getCreateDate();
		this.updateDate 	= userActivity.getUpdateDate();
		this.userId 		= userActivity.getUserId();
		this.setUser(userActivity.getUser());
		this.setCode(userActivity.getCode());
		this.comments 		= comments;
		
	}

	/**
	 * @return the isLiked
	 */
	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	/**
	 * @return the comments
	 */
	public List<CustomCommentResponse> getComments() {
		return comments;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the code
	 */
	public Code getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Code code) {
		this.code = code;
	}

	

}
