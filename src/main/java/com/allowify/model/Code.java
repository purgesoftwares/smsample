package com.allowify.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@CompoundIndexes({
	@CompoundIndex(name = "searchCode", def = "{'Code.code' : 1, 'Code.title' : 1}")
})


@Document
public class Code implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@Indexed
	@NotEmpty(message = "Code should not be blank.")
	private String code;
	
	private String title;
	
	@NotEmpty(message = "Lat and Long should not be blank.")
	@GeoSpatialIndexed(name="GeoIndex_2d")
	private double[] latLong;
	
	private String address1;
	
	private String address2;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String zipCode;
	
	private int isPrivate;
	
	@Indexed
	private String userId;
	
	private int totalReach;
	
	private int status;
	
	private int likesCount;
	
	private Date createDate;
	
	private Date updateDate;
	
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

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	@DBRef
	private UploadedFile uploadFile;
	
	private int checkInCount = 0;
	
	public  int getCheckInCount() {
		return checkInCount;
	}

	public  void setCheckInCount(int checkInCount) {
		this.checkInCount = checkInCount;
	}

	public UploadedFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(UploadedFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getId() {
	  return id;
	}
	
	public void setId(String id) {
		  this.id = id;
	}
	
	 
	/**
	 * @return the latLong
	 */
	public double[] getLatLong() {
		return latLong;
	}

	/**
	 * @param latLong the latLong to set
	 */
	public void setLatLong(double[] latLong) {
		this.latLong = latLong;
	}


	/**
	 * @return the totalReach
	 */
	public int getTotalReach() {
		return totalReach;
	}

	/**
	 * @param totalReach the totalReach to set
	 */
	public void setTotalReach(int totalReach) {
		this.totalReach = totalReach;
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

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the isPrivate
	 */
	public int getIsPrivate() {
		return isPrivate;
	}

	/**
	 * @param isPrivate the isPrivate to set
	 */
	public void setIsPrivate(int isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	
}
