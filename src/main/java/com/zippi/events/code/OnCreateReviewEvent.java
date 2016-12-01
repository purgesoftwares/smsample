package com.zippi.events.code;

import org.springframework.context.ApplicationEvent;

import com.allowify.model.Review;
import com.allowify.model.User;

@SuppressWarnings("serial")
public class OnCreateReviewEvent extends ApplicationEvent {
	
	private final Review review;
	
	private final User user;

	public OnCreateReviewEvent(final Review review, final User user) {
		super(review);
		this.review =  review;
		this.user = user;
	}

	public Review getReview() {
		return review;
	}
	
	public User getUser() {
		return user;
	}
	
}
