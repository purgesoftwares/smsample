package com.allowify.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Alert {

	@Id
	private String id;
	
	private Long cardNumber;
	
	private String title;
	
	private String userId;
	
	private Boolean isRead;
	
	private Date timestamp;
	
	public Alert() {
		
	}
	
	public Alert(String userId, Long cardNumber, String title, String message) {
		super();
		this.cardNumber = cardNumber;
		this.title = title;
		this.message = message;
		this.userId = userId;
		this.isRead = false;
		this.timestamp = new Date();
		
	}

	private String message;
	
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
	 * @return the cardNumber
	 */
	public Long getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the usrId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param usrId the usrId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the isRead
	 */
	public Boolean getIsRead() {
		return isRead;
	}

	/**
	 * @param isRead the isRead to set
	 */
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
