package com.allowify.response;

public class UserDetailsResponse {
	
	private boolean isFollowing;
	
	private int numOfFollowers;
	
	private int numOfFollowing;

	public boolean isFollowing() {
		return isFollowing;
	}

	public void setIsFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}

	public int getNumOfFollowers() {
		return numOfFollowers;
	}

	public void setNumOfFollowers(int numOfFollowers) {
		this.numOfFollowers = numOfFollowers;
	}

	public int getNumOfFollowing() {
		return numOfFollowing;
	}

	public void setNumOfFollowing(int numOfFollowing) {
		this.numOfFollowing = numOfFollowing;
	}

	
}
