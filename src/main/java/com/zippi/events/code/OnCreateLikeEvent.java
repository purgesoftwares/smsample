package com.zippi.events.code;

import org.springframework.context.ApplicationEvent;

import com.allowify.model.Code;
import com.allowify.model.CodeEvent;
import com.allowify.model.Comment;
import com.allowify.model.Like;
import com.allowify.model.User;

@SuppressWarnings("serial")
public class OnCreateLikeEvent extends ApplicationEvent {
	
	private final Like like;
	
	private final User user;
	
	private final CodeEvent codeEvent;
	
	
	private final Code code;
	
	public OnCreateLikeEvent(final Like like, final User user, final CodeEvent codeEvent, final Code code) {
		super(like);
		this.like = like;
		this.user = user;
		this.codeEvent = codeEvent;
		this.code = code;
		
	}

	public Like getLike() {
		return like;
	}
	
	public User getUser() {
		return user;
	}
	
	public CodeEvent getCodeEvent() {
		return codeEvent;
	}
	
	
	public Code getCode() {
		return code;
	}

}
