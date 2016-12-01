package com.zippi.events.code;

import org.springframework.context.ApplicationEvent;

import com.allowify.model.CodeEvent;
import com.allowify.model.EventInterested;
import com.allowify.model.User;

@SuppressWarnings("serial")
public class OnCreateEventInterstedEvent extends ApplicationEvent{

	private final EventInterested eventInterested;
	
	private final User user;
	
	private final CodeEvent codeEvent;
	
	public OnCreateEventInterstedEvent(final EventInterested eventInterested, final User user, final CodeEvent codeEvent) {
		super(eventInterested);
		this.eventInterested = eventInterested;
		this.user = user;
		this.codeEvent = codeEvent;
	}
	
	public EventInterested getEventInterested() {
		return eventInterested;
	}
	
	public User getUser() {
		return user;
	}
	
	public CodeEvent getCodeEvent() {
		return codeEvent;
	}

}
