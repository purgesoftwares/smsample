package com.allowify.response;

import java.util.ArrayList;
import java.util.List;

import com.allowify.model.Alert;

/**
 * 
 * @author tonyacunar
 *
 */
public class AlertsResponse {

	private List<Alert> alertList = new ArrayList<Alert>();

	public AlertsResponse(List<Alert> alertList) {
		this.alertList = alertList;
	}
	
	public List<Alert> getAlertList() {
		return alertList;
	}

	public void setAlertList(List<Alert> alertList) {
		this.alertList = alertList;
	}
	
	
	
}
