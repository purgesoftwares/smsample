package com.allowify.model;

import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Gps {

	@Id private String id;
	
	private String userName;
	
	@NotEmpty(message = "Location should not be blank.")
	@Size(max = 2, min = 2)
	private double[] location;
	
	@NotNull(message = "Time Stamp should not be blank.")
	private Date timestamp;
	
	@PersistenceConstructor
	public Gps(String userName, double[] location , Date timestamp) {
		super();
		this.location = location;
		this.userName = userName;
		this.timestamp = timestamp;
	}

	public Gps(String userName, double latitude, double longitude) {
		super();
		this.userName = userName;
		double location[] 	= { latitude,longitude };
		this.location = location;
	}

	public Gps() {
		
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
	 * @return the location
	 */
	public double[] getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(double[] location) {
		this.location = location;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Gps [id=" + id + ", location=" + Arrays.toString(location)
				+ "]";
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
