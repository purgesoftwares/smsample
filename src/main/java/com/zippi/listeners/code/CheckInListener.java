package com.zippi.listeners.code;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.zippi.events.code.OnCreateCheckInEvent;

@Component
public class CheckInListener implements ApplicationListener<OnCreateCheckInEvent> {

	@Override
	public void onApplicationEvent(final OnCreateCheckInEvent arg0) {
				
	}

}
