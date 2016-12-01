package com.allowify.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document
public class CodeEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;

	@Indexed
	private String  code;
	
	@NotEmpty(message="Description should not be blank")
	private String description;
	
	//@NotEmpty(message="Date/time should not be blank")
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date startDate;
	
	//@NotEmpty(message="Date/time should not be blank")
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date endDate;
	
	private Date eventStartTime;
	
	private Date eventEndTime;
	
	@Range(min = 1, max = 31)
	private int[] repeatValuesOfMonth;
	
	@Range(min=1, max=7)
	private int[] repeatValuesOfWeek; 
	
	private  int likeCount;
	
	@Indexed
	private String userId;
	
	private Date createDate;
	
	private Date updateDate;
	
	@DBRef
	private UploadedFile uploadFile;

	private Long phoneNumber;
	
	private int checkInCount;
	
	public int getCheckInCount() {
		return checkInCount;
	}

	public void setCheckInCount(int checkInCount) {
		this.checkInCount = checkInCount;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public UploadedFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(UploadedFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int[] getRepeatValuesOfMonth() {
		return repeatValuesOfMonth;
	}

	public void setRepeatValuesOfMonth(int[] repeatValuesOfMonth) {
		this.repeatValuesOfMonth = repeatValuesOfMonth;
	}

	public int[] getRepeatValuesOfWeek() {
		return repeatValuesOfWeek;
	}

	public void setRepeatValuesOfWeek(int[] repeatValuesOfWeek) {
		this.repeatValuesOfWeek = repeatValuesOfWeek;
	}

	public Date getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(Date eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public Date getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(Date eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	private String repeatType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(String repeatType) {
		this.repeatType = repeatType;
	}

}
