package com.zippi.events.code;

import org.springframework.context.ApplicationEvent;

import com.allowify.model.CodeEvent;
import com.allowify.model.User;

@SuppressWarnings("serial")
public class OnCreateEventOnCode  extends ApplicationEvent{

	private final CodeEvent codeEvent;
	
	private final User user;
	
	public OnCreateEventOnCode(final CodeEvent codeEvent, final User user) {
		super(codeEvent);
		this.codeEvent = codeEvent;
		this.user = user;
	}
	
	public CodeEvent getCodeEvent() {
		return codeEvent;
	}
	
	public User getUser() {
		return user;
	}

}
