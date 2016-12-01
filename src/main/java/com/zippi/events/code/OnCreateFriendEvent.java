package com.zippi.events.code;

import org.springframework.context.ApplicationEvent;

import com.allowify.model.Friend;
import com.allowify.model.User;

@SuppressWarnings("serial")
public class OnCreateFriendEvent extends ApplicationEvent {

	private final Friend friend;
	
	private final User user;
	
	public OnCreateFriendEvent(final Friend friend, final User user) {
		super(friend);
		this.friend = friend;
		this.user = user;
	}
	
	public Friend getFriend() {
		return friend;
	}
	
	public User getUser() {
		return user;
	}

}
