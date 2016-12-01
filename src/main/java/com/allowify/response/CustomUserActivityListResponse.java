package com.allowify.response;

import java.util.ArrayList;


public class CustomUserActivityListResponse {

	private final ArrayList<CustomUserActivityResponse> userActivity ;
	
	public CustomUserActivityListResponse(ArrayList<CustomUserActivityResponse> userActivity) {
		this.userActivity = userActivity;
	}

	public ArrayList<CustomUserActivityResponse> getUserActivityList() {
		return userActivity;
	}
}
