package com.zippi.listeners.code;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.zippi.events.code.OnCreateReviewEvent;

@Component
public class ReviewListener implements ApplicationListener<OnCreateReviewEvent> {

	@Override
	public void onApplicationEvent(OnCreateReviewEvent arg0) {
		System.out.println("listening review");
		
	}

}
