package com.allowify.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class EventInterested implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@Indexed
	@NotEmpty(message = "UserId should not be blank.")
	private String userId;
	
	@Indexed
	@NotEmpty(message = "EventId should not be blank.")
	private String eventId;

	
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

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	

}
