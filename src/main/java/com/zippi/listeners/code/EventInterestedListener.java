package com.zippi.listeners.code;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.zippi.events.code.OnCreateEventInterstedEvent;

@Component
public class EventInterestedListener implements ApplicationListener<OnCreateEventInterstedEvent> {

	@Override
	public void onApplicationEvent(final OnCreateEventInterstedEvent arg0) {
		
	}

}
