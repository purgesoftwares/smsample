package com.allowify.response;

public class ByCodeDetailsResponse {

	private int likesCount;
		
	private int checkInCount;
	
	private int reviewsCount;
	
	private int eventsCount;
	
	private int shareCounts;

	public int getReviewsCount() {
		return reviewsCount;
	}

	public void setReviewsCount(int reviewsCount) {
		this.reviewsCount = reviewsCount;
	}

	public int getEventsCount() {
		return eventsCount;
	}

	public void setEventsCount(int eventsCount) {
		this.eventsCount = eventsCount;
	}

	public int getCheckInCount() {
		return checkInCount;
	}

	public void setCheckInCount(int checkInCount) {
		this.checkInCount = checkInCount;
	}

	/**
	 * @return the likesCount
	 */
	public int getLikesCount() {
		return likesCount;
	}

	/**
	 * @param likesCount the likesCount to set
	 */
	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
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
