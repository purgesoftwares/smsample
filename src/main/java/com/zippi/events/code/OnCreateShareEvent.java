package com.zippi.events.code;

import org.springframework.context.ApplicationEvent;

import com.allowify.model.Code;
import com.allowify.model.CodeEvent;
import com.allowify.model.User;
import com.allowify.model.UserActivity;

@SuppressWarnings("serial")
public class OnCreateShareEvent extends ApplicationEvent {
	
	private final User user;
	
	private final Code code;
	
	private final CodeEvent codeEvent;
	
	private final UserActivity userActivity;
	
	public OnCreateShareEvent(final User user, final Code code, final CodeEvent codeEvent,
			final UserActivity userActivity) {
		super(user);
		this.user = user;
		this.code = code;
		this.codeEvent = codeEvent;
		this.userActivity = userActivity;
	}
	
	public User getUser(){
		return user;
	}
	
	public Code getCode() {
		return code;
	}
	
	public CodeEvent getCodeEvent() {
		return codeEvent;
	}
	
	public UserActivity getUserActivity() {
		return userActivity;
	}
	

}
