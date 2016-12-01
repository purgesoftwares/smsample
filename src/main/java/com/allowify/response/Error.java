package com.allowify.response;

/**
 * 
 * @author tonyacunar
 *
 */
public enum Error {

			
	/**
	 * Data input validations
	 */
	INVALID_USERNAME(1),
	INVALID_CARD(2),
	INVALID_TRANSACTION(3),
	INVALID_LONGITUDE(4),
	INVALID_LATITUDE(5),
	GPS_COORDINATES_NOT_FOUND(6);
	
	private int errorValue;
	
	private Error(int errorValue) {
		this.errorValue = errorValue;
	}
	
	public int getErrorValue() {
		return errorValue;
	}
	
}
