package com.allowify.response;

import java.util.Date;

public class PromoCodeResponse {
	
	private String message;
	
	private Date validFrom;
	
	private Date validTo;
	
	public PromoCodeResponse(String message, Date validFrom, Date validTo) {
		this.message = message;
		this.validFrom = validFrom;
		this.validTo = validTo;
	}
	
	public PromoCodeResponse (String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

}
