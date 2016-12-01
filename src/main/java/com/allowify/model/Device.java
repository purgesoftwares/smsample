/**
 * 
 */
package com.allowify.model;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author shankar palsaniya
 *
 */
@Document
public class Device {
	
	@Id
	private String id;
	
	private String userName;
	
	@NotEmpty(message = "Device token should not be blank")
	@Pattern(regexp = "^[a-z0-9]{60,100}$")
	private String deviceToken;
	
	@NotEmpty(message = "Device OS should not be blank")
	@Pattern(regexp = "[a-zA-z]+")
	private String deviceOs;
	
	private Boolean deviceStatus;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public Device(String userName,String deviceToken, String deviceOs, Boolean deviceStatus) {
		super();
		this.userName = userName;
		this.deviceToken = deviceToken;
		this.deviceOs = deviceOs;
		this.deviceStatus = deviceStatus;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the deviceToken
	 */
	public String getDeviceToken() {
		return deviceToken;
	}

	/**
	 * @param deviceToken the deviceToken to set
	 */
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	/**
	 * @return the deviceOs
	 */
	public String getDeviceOs() {
		return deviceOs;
	}

	/**
	 * @param deviceOs the deviceOs to set
	 */
	public void setDeviceOs(String deviceOs) {
		this.deviceOs = deviceOs;
	}

	/**
	 * @return the deviceStatus
	 */
	public Boolean getDeviceStatus() {
		return deviceStatus;
	}

	/**
	 * @param deviceStatus the deviceStatus to set
	 */
	public void setDeviceStatus(Boolean deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	/**
	 * 
	 */
	public Device() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Devices [id=" + id + ", deviceToken=" + deviceToken
				+ ", deviceOs=" + deviceOs + ", deviceStatus=" + deviceStatus
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
	
	

}
