package com.allowify.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class Preference {
	
	@Id
	private String id;
	
	@NotNull(message = "isCardActive should not be blank.")
	private Boolean isCardActive;
	
	@NotNull(message = "isLocatonActive should not be blank.")
	private Boolean isLocatonActive;
	
	@NotNull(message = "transactionLimit should not be blank.")
	private Double transactionLimit;
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}



	public Preference(Boolean isCardActive, Boolean isLocatonActive,
			Double transactionLimit) {
		super();
		this.isCardActive = isCardActive;
		this.isLocatonActive = isLocatonActive;
		this.transactionLimit = transactionLimit;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



	/**
	 * @return the isCardActive
	 */
	public Boolean getIsCardActive() {
		return isCardActive;
	}



	/**
	 * @param isCardActive the isCardActive to set
	 */
	public void setIsCardActive(Boolean isCardActive) {
		this.isCardActive = isCardActive;
	}



	/**
	 * @return the isLocatonActive
	 */
	public Boolean getIsLocatonActive() {
		return isLocatonActive;
	}



	/**
	 * @param isLocatonActive the isLocatonActive to set
	 */
	public void setIsLocatonActive(Boolean isLocatonActive) {
		this.isLocatonActive = isLocatonActive;
	}



	/**
	 * @return the transactionLimit
	 */
	public Double getTransactionLimit() {
		return transactionLimit;
	}



	/**
	 * @param transactionLimit the transactionLimit to set
	 */
	public void setTransactionLimit(Double transactionLimit) {
		this.transactionLimit = transactionLimit;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Preference [id=" + id + ", isCardActive=" + isCardActive
				+ ", isLocatonActive=" + isLocatonActive
				+ ", transactionLimit=" + transactionLimit + "]";
	}



	public Preference() {
		UUID idOne = UUID.randomUUID();
		this.id = ""+idOne;
	}

}
