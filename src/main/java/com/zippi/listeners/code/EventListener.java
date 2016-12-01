package com.zippi.listeners.code;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.zippi.events.code.OnCreateEventOnCode;

@Component
public class EventListener implements ApplicationListener<OnCreateEventOnCode> {

	@Override
	public void onApplicationEvent(final OnCreateEventOnCode arg0) {
		System.out.println("in event Listener");
		
	}

}
