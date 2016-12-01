package com.zippi.events.code;

import org.springframework.context.ApplicationEvent;

import com.allowify.model.Comment;

@SuppressWarnings("serial")
public class OnCreateCommentEvent extends ApplicationEvent {
	private final Comment comment;
	
	public OnCreateCommentEvent(Comment comment) {
		super(comment);
		this.comment = comment;
	}

	 public Comment getComment() {
	    	return comment;
	 }

}
