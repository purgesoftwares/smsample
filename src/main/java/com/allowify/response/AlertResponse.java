package com.allowify.response;

import com.allowify.model.Alert;

/**
 * 
 * @author tonyacunar
 *
 */
public class AlertResponse {

	private Alert alert;
	private Error error;

	public Alert getAlert() {
		return alert;
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
	
}
