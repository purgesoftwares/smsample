package com.zippi.listeners.code;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.zippi.events.code.OnCreateFriendEvent;

@Component
public class FollowListener implements ApplicationListener<OnCreateFriendEvent> {

	@Override
	public void onApplicationEvent(final OnCreateFriendEvent arg0) {
				
	}

}
