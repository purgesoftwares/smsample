package com.zippi.events.code;

import org.springframework.context.ApplicationEvent;

import com.allowify.model.CheckIn;
import com.allowify.model.Code;
import com.allowify.model.CodeEvent;
import com.allowify.model.User;


@SuppressWarnings("serial")
public class OnCreateCheckInEvent extends ApplicationEvent {
	
	private final CheckIn checkIn;
	
	private final CodeEvent codeEvent;
	
	private final Code code;
	
	private final User user;

	public OnCreateCheckInEvent(final CheckIn checkIn, final CodeEvent codeEvent, final Code code, final User user) {
		super(checkIn);
		this.checkIn = checkIn;
		this.codeEvent = codeEvent;
		this.code = code;
		this.user = user;
	}
	
	public CheckIn getCheckIn() {
		return checkIn;
	}
	
	public CodeEvent getCodeEvent() {
		return codeEvent;
	}
	
	public Code getCode() {
		return code;
	}
	
	public User getUser() {
		return user;
	}

}
