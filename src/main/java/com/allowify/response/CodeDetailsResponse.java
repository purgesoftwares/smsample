package com.allowify.response;


/**
 * 
 * @author shankarp
 *
 * wrapper for code detail
 *
 */
public class CodeDetailsResponse {
	
	private boolean isLiked;
	
	private boolean isBookmarked;

	private int likesCount;
	
	private boolean isCheckedIn;
	
	private int checkInCount;
	
	private int reviewsCount;
	
	private int eventsCount;
	
	private boolean isReviewed;
	
	private double avgRating;
	
	private int shareCounts;

	public boolean getIsReviewed() {
		return isReviewed;
	}

	public void setIsReviewed(boolean isReviewed) {
		this.isReviewed = isReviewed;
	}

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

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

	public boolean isCheckedIn() {
		return isCheckedIn;
	}

	public void setCheckedIn(boolean isCheckedIn) {
		this.isCheckedIn = isCheckedIn;
	}

	public int getCheckInCount() {
		return checkInCount;
	}

	public void setCheckInCount(int checkInCount) {
		this.checkInCount = checkInCount;
	}

	/**
	 * @return the isLiked
	 */
	public boolean getIsLiked() {
		return isLiked;
	}

	/**
	 * @param isLiked the isLiked to set
	 */
	public void setIsLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	/**
	 * @return the isBookmarked
	 */
	public boolean isBookmarked() {
		return isBookmarked;
	}

	/**
	 * @param isBookmarked the isBookmarked to set
	 */
	public void setBookmarked(boolean isBookmarked) {
		this.isBookmarked = isBookmarked;
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
