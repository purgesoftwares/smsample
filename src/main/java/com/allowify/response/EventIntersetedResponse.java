package com.allowify.response;

public class EventIntersetedResponse {
	
	private boolean isLiked;
	
	private int likesCount;
	
	private boolean interestedInEvent;
	
	private int numOfInterestedInEvent;
	
	private int shareCounts;

	public boolean getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public boolean isInterestedInEvent() {
		return interestedInEvent;
	}

	public void setInterestedInEvent(boolean interestedInEvent) {
		this.interestedInEvent = interestedInEvent;
	}

	public int getNumOfInterestedInEvent() {
		return numOfInterestedInEvent;
	}

	public void setNumOfInterestedInEvent(int numOfInterestedInEvent) {
		this.numOfInterestedInEvent = numOfInterestedInEvent;
	}

	/**
	 * @return the shareCounts
	 */
	public int getShareCounts() {
		return shareCounts;
	}

	/**
	 * @param shareCounts the shareCounts to set
	 */
	public void setShareCounts(int shareCounts) {
		this.shareCounts = shareCounts;
	}

}
